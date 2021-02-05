package com.example.uproject.ui.activities.home.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uproject.R
import com.example.uproject.common.utils.makeClearableEditText
import com.example.uproject.core.Resource
import com.example.uproject.core.aplication.ctx
import com.example.uproject.data.firebase.home.FirebaseFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.ActivityHomeBinding
import com.example.uproject.databinding.ActivitySearchBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.details.DetailsProductActivity
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.viewmodels.home.ProductViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory

class SearchActivity : AppCompatActivity(), ProductListAdapter.OnProductClickListener {

    private lateinit var binding: ActivitySearchBinding
    //private val searchProductAdapter by lazy { SearchProductAdapter() }

    private val viewModel by viewModels<ProductViewModel> {
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                FirebaseFirestoreDataSourceImpl()
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

    override fun onProductClicked(product: ProductEntity, colorRGB: Int) {
        val productBundle = Bundle()
        productBundle.putParcelable("product", product)
        val intent = Intent(this, DetailsProductActivity::class.java)
        intent.putExtras(productBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
        finish()
    }

}