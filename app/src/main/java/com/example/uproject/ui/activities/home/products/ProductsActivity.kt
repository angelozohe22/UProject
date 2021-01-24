package com.example.uproject.ui.activities.home.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uproject.R
import com.example.uproject.databinding.ActivityProductsBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding
    private val mAdapter by lazy { ProductsViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupProductsViewPager()
    }

    private fun setupProductsViewPager() {
        val viewPager = binding.viewPagerProducts
        val tabLayout  = binding.tabLayoutCategory

        viewPager.adapter = mAdapter
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager){
            tab, position ->
            when(position){
                0 ->{
                    tab.text = getString(R.string.tab_title_insumos)
                }
                1 ->{
                    tab.text = getString(R.string.tab_title_accesorios)
                }
                2 ->{
                    tab.text = getString(R.string.tab_title_confiteria)
                }
                3 ->{
                    tab.text = getString(R.string.tab_title_decorativos)
                }

            }
        }
        tabLayoutMediator.attach()

    }


}