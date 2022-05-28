package com.example.uproject.ui.modules.home.products.fragments.decorativos

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
import com.example.uproject.databinding.FragmentDecorativosBinding
import com.example.uproject.domain.model.Product
import com.example.uproject.data.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.modules.home.details.DetailsProductActivity
import com.example.uproject.ui.modules.home.products.ProductListAdapter
import com.example.uproject.ui.modules.home.category.ProductViewModel
import com.example.uproject.ui.modules.home.HomeViewModelFactory

class DecorativosFragment : Fragment(), ProductListAdapter.OnProductClickListener {

    private var _binding: FragmentDecorativosBinding? = null
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
    ): View? {
        _binding = FragmentDecorativosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvListDecorativosProduct.apply {
            layoutManager   = GridLayoutManager(requireContext(),2)
            adapter         = productAdapter
        }

        viewModel.fetchListProductByCategory(3).observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val listDecorativosProduct = result.data
                        productAdapter.setData(listDecorativosProduct)
                    }
                    is Resource.Failure -> {}
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClicked(product: Product, colorRGB: Int) {
        val productBundle = Bundle()
        productBundle.putParcelable("product", product)
        val intent = Intent(requireActivity(), DetailsProductActivity::class.java)
        intent.putExtras(productBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
    }
}