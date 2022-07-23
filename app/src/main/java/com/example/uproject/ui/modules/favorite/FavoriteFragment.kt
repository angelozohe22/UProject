package com.example.uproject.ui.modules.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uproject.core.Resource
import com.example.uproject.data.source.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.source.helpers.DulcekatDataBase
import com.example.uproject.data.source.local.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentFavoriteBinding
import com.example.uproject.domain.model.Product
import com.example.uproject.data.repository.DulcekatRepositoryImpl
import com.example.uproject.domain.model.FavoriteProduct
import com.example.uproject.ui.modules.home.details.DetailsProductActivity
import com.example.uproject.ui.modules.home.products.ProductListAdapter
import com.example.uproject.ui.modules.home.HomeViewModelFactory

class FavoriteFragment : Fragment(), FavoriteAdapter.OnFavoriteProductClickListener {

    private var _binding: FragmentFavoriteBinding? =null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<FavoriteViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(requireContext())),
                RemoteFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        setupRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()

        //when is a function, the value of this liveData is dynamic
        viewModel.fetchFavoriteProducts().observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        binding.rvListFavorite.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.rvListFavorite.visibility = View.VISIBLE
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
                    is Resource.Failure -> {
                        print("EN FAVORITO? ${result.errorMessage}")
                    }
                }
            }
        })


    }

    private fun setupRecycler() {
        binding.rvListFavorite.apply{
            layoutManager   = GridLayoutManager(requireContext(),2)
            setHasFixedSize(false)
        }



    }

//    override fun onProductClicked(product: FavoriteProduct, colorRGB: Int) {
//        val productBundle = Bundle()
//        productBundle.putParcelable("product", product)
//        val intent = Intent(requireActivity(), DetailsProductActivity::class.java)
//        intent.putExtras(productBundle)
//        intent.putExtra("colorRGB", colorRGB)
//        startActivity(intent)
//    }

    override fun onFavoriteProductClicked(product: FavoriteProduct, colorRGB: Int) {
        viewModel.fetchProductById(product.productId).observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val product = result.data
                        val productBundle = Bundle()
                        productBundle.putParcelable("product", product)
                        val intent = Intent(requireActivity(), DetailsProductActivity::class.java)
                        intent.putExtras(productBundle)
                        intent.putExtra("colorRGB", colorRGB)
                        startActivity(intent)
                    }
                    is Resource.Failure -> {

                    }
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}