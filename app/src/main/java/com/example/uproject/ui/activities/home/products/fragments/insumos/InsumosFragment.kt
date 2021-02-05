package com.example.uproject.ui.activities.home.products.fragments.insumos

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
import com.example.uproject.data.firebase.home.FirebaseFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentInsumosBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.details.DetailsProductActivity
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.viewmodels.home.ProductViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory

class InsumosFragment : Fragment(), ProductListAdapter.OnProductClickListener {

    private var _binding: FragmentInsumosBinding? = null
    private val binding get() = _binding!!

    private val productAdapter   by lazy { ProductListAdapter(this, requireContext()) }

    private val viewModel by activityViewModels<ProductViewModel>{
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
    ): View {
        _binding = FragmentInsumosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setupRecyclerView()
    }

    private fun setupRecyclerView(){
        binding.rvListInsumosProduct.apply {
            layoutManager   = GridLayoutManager(requireContext(),2)
            adapter         = productAdapter
        }

        viewModel.fetchListProductByCategory(0).observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val listInsumosProduct = result.data
                        productAdapter.setData(listInsumosProduct)
                    }
                    is Resource.Failure -> TODO()
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClicked(product: ProductEntity, colorRGB: Int) {
        val productBundle = Bundle()
        productBundle.putParcelable("product", product)
        val intent = Intent(requireActivity(), DetailsProductActivity::class.java)
        intent.putExtras(productBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
    }

}