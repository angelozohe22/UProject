package com.example.uproject.ui.activities.home.category

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.uproject.common.utils.setStatusBarColor
import com.example.uproject.data.local.db.category.CategoryEntity
import com.example.uproject.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryEntity: CategoryEntity
    private var colorRGB = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null){
            categoryEntity = intent.getParcelableExtra<CategoryEntity>("category") as CategoryEntity
            colorRGB       = intent.getIntExtra("colorRGB", 0)
        }

        setupCategory()

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


}