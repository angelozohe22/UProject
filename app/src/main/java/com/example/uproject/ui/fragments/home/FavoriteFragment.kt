package com.example.uproject.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uproject.core.Resource
import com.example.uproject.data.firebase.home.FirebaseFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentFavoriteBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.details.DetailsProductActivity
import com.example.uproject.ui.fragments.home.adapter.FavoriteAdapter
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.viewmodels.home.FavoriteViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory

class FavoriteFragment : Fragment(), ProductListAdapter.OnProductClickListener {

    private var _binding: FragmentFavoriteBinding? =null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<FavoriteViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(requireContext())),
                FirebaseFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
    }

    private fun setupRecycler() {
        binding.rvListFavorite.apply{
            layoutManager   = GridLayoutManager(requireContext(),2)
        }

        //when is a function, the value of this liveData is dynamic
        viewModel.fetchFavoriteProducts().observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val favoriteList = result.data

                        if(favoriteList.isNotEmpty()){
                            val text = (if(favoriteList.size<10) "0" else "")
                                .plus(favoriteList.size)
                                .plus(if (favoriteList.size == 1) " producto" else " productos")

                            binding.tvQuantityFavoriteProducts.text = text
                            binding.rvListFavorite.adapter = FavoriteAdapter(favoriteList, this)
                        }else{
                            binding.rvListFavorite.visibility = View.GONE
                            binding.imgDefaultEmptyList.visibility = View.VISIBLE
                            binding.titleDefaultEmptyList.visibility = View.VISIBLE
                            binding.rvListFavorite.adapter = FavoriteAdapter(emptyList(), this)
                        }

                    }
                    is Resource.Failure -> {}
                }
            }
        })

    }

    override fun onProductClicked(product: ProductEntity, colorRGB: Int) {
        val productBundle = Bundle()
        productBundle.putParcelable("product", product)
        val intent = Intent(requireActivity(), DetailsProductActivity::class.java)
        intent.putExtras(productBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}