package com.example.uproject.ui.activities.home.category

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.uproject.common.utils.setStatusBarColor
import com.example.uproject.core.Resource
import com.example.uproject.data.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.CategoryEntity
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.ActivityCategoryBinding
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.details.DetailsProductActivity
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.viewmodels.home.ProductViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory

class CategoryActivity : AppCompatActivity(),ProductListAdapter.OnProductClickListener {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryEntity: CategoryEntity
    private var colorRGB = 0

    private val productAdapter   by lazy { ProductListAdapter(this, this) }

    private val viewModel by viewModels<ProductViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                RemoteFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null){
            categoryEntity = intent.getParcelableExtra<CategoryEntity>("category") as CategoryEntity
            colorRGB       = intent.getIntExtra("colorRGB", 0)
        }

        binding.btnBackCategory.setOnClickListener {
            onBackPressed()
        }

        setupCategory()
        setupRecyclerView()
    }

    private fun setupCategory(){
        val tvNameCategory      = binding.tvNameCategory
        val imgCategory         = binding.imgCategory
        val cardCategorySupport = binding.cardCategorySupport

        tvNameCategory.text = categoryEntity.name
        val imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                add(SvgDecoder(applicationContext))
            }.build()
        val request = ImageRequest.Builder(this)
            .data(categoryEntity.image)
            .target(imgCategory)
            .build()
        imageLoader.enqueue(request)
        cardCategorySupport.setCardBackgroundColor(colorRGB)
        setStatusBarColor(this, colorRGB)
    }

    private fun setupRecyclerView(){
        binding.rvListProductCategory.apply {
            layoutManager   = GridLayoutManager(applicationContext,2)
            adapter         = productAdapter
        }

        viewModel.fetchListProductByCategory(categoryEntity.idCategory).observe(this, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val listInsumosProduct = result.data
                        productAdapter.setData(listInsumosProduct)
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
    }


}