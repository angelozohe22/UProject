package com.example.uproject.ui.activities.home.products.fragments.confiteria

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
import com.example.uproject.data.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentConfiteriaBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.details.DetailsProductActivity
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.viewmodels.home.ProductViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory

class ConfiteriaFragment : Fragment(),ProductListAdapter.OnProductClickListener {

    private var _binding: FragmentConfiteriaBinding? = null
    private val binding get() = _binding!!

    private val productAdapter   by lazy { ProductListAdapter(this, requireContext()) }

    private val viewModel by activityViewModels<ProductViewModel>{
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
    ): View {
        _binding = FragmentConfiteriaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvListConfiteriaProduct.apply {
            layoutManager   = GridLayoutManager(requireContext(),2)
            adapter         = productAdapter
        }

        viewModel.fetchListProductByCategory(2).observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val listConfiteriaProduct = result.data
                        productAdapter.setData(listConfiteriaProduct)
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