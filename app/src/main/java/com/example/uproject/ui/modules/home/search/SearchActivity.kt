package com.example.uproject.ui.modules.home.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uproject.common.utils.makeClearableEditText
import com.example.uproject.core.Resource
import com.example.uproject.core.ctx
import com.example.uproject.data.source.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.source.helpers.DulcekatDataBase
import com.example.uproject.data.source.local.LocalDataSourceImpl
import com.example.uproject.databinding.ActivitySearchBinding
import com.example.uproject.domain.model.Product
import com.example.uproject.data.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.modules.home.details.DetailsProductActivity
import com.example.uproject.ui.modules.home.products.ProductListAdapter
import com.example.uproject.ui.modules.home.category.ProductViewModel
import com.example.uproject.ui.modules.home.HomeViewModelFactory

class SearchActivity : AppCompatActivity(), ProductListAdapter.OnProductClickListener {

    private lateinit var binding: ActivitySearchBinding
    //private val searchProductAdapter by lazy { SearchProductAdapter() }

    private val viewModel by viewModels<ProductViewModel> {
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                RemoteFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupSearch()
        setupRecycler()

        val btnBackSearch = binding.btnBackSearch
        btnBackSearch.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupSearch(){
        val etSearchView = binding.etSearchView
        etSearchView.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        etSearchView.makeClearableEditText()
        etSearchView.setOnEditorActionListener { view, actionId, event ->
            var action = false
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val nameProduct = view.text.toString().trim()
                val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

                if(!TextUtils.isEmpty(nameProduct)){
                    viewModel.setProductName(nameProduct)
                    action = true
                }else{
                    Toast.makeText(this, "Ingrese el nombre del producto a buscar", Toast.LENGTH_SHORT).show()
                }
            }
            action
        }
    }

    private fun setupRecycler(){
        binding.rvListSearchView.apply {
            layoutManager = LinearLayoutManager( ctx,
                LinearLayoutManager.VERTICAL,false)
        }

        viewModel.fetchListProductByName.observe(this, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        binding.rvListSearchView.adapter = SearchProductAdapter(result.data, this)
                    }
                    is Resource.Failure -> {}
                }
            }
        })

    }

    override fun onProductClicked(product: Product, colorRGB: Int) {
        val productBundle = Bundle()
        productBundle.putParcelable("product", product)
        val intent = Intent(this, DetailsProductActivity::class.java)
        intent.putExtras(productBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
        finish()
    }

}