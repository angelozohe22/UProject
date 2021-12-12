package com.example.uproject.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.uproject.data.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.local.db.DulcekatDataBase
import com.example.uproject.data.local.db.entity.CategoryEntity
import com.example.uproject.data.local.db.entity.ProductEntity
import com.example.uproject.data.local.source.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentHomeBinding
import com.example.uproject.domain.model.SlideItem
import com.example.uproject.domain.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.activities.home.category.CategoryActivity
import com.example.uproject.ui.activities.home.details.DetailsProductActivity
import com.example.uproject.ui.activities.home.products.ProductsActivity
import com.example.uproject.ui.activities.home.search.SearchActivity
import com.example.uproject.ui.fragments.home.adapter.CategoryListAdapter
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.fragments.home.adapter.SlideAdapter
import com.example.uproject.ui.viewmodels.home.HomeViewModel
import com.example.uproject.ui.viewmodels.home.factoryhome.HomeViewModelFactory
import kotlin.math.abs

class HomeFragment : Fragment(),
    CategoryListAdapter.OnCategoryClickListener,
    ProductListAdapter.OnProductClickListener{

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var slideAdapter: SlideAdapter
    private val categoryAdapter  by lazy { CategoryListAdapter(this) }
    private val productAdapter   by lazy { ProductListAdapter(this, requireContext()) }

    private val viewModel by activityViewModels<HomeViewModel>{
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
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        slideAdapter = SlideAdapter(binding.bannerOffers)

        setupCategoryRecyclerView()
        setupProductRecyclerView()
        setupViewPager2()

        binding.searchViewContainer.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        binding.titleSeeAll.setOnClickListener {
            val intent = Intent(requireActivity(), ProductsActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    private fun setupCategoryRecyclerView(){
        binding.rvListCategory.apply{
            layoutManager = GridLayoutManager(requireContext(),4)
            adapter = categoryAdapter
        }
        viewModel.getListCategory()
        viewModel.listCategoryEntity.observe(viewLifecycleOwner , Observer {
            it?.let { result ->
                categoryAdapter.setData(result)
            }
        })
    }

    private fun setupProductRecyclerView(){
        binding.rvListProduct.apply{
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = productAdapter
        }
        viewModel.getListProduct()

        viewModel.listProduct.observe(viewLifecycleOwner , Observer {
            it?.let { result ->
                productAdapter.setData(result)
            }
        })
    }

    private fun setupViewPager2(){
//        val handler = Handler(Looper.getMainLooper())
        val listSlideImage = listOf(
            SlideItem("https://i.imgur.com/th1eMcY.png"),
            SlideItem("https://i.imgur.com/qV7iTVK.png"),
            SlideItem("https://i.imgur.com/ulfPsmx.png"),
        )

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.apply {
            addTransformer(MarginPageTransformer(40)) //Space between images
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r*0.15f
            }) //scale image on y-axis
        }

        binding.bannerOffers.apply {
            adapter = slideAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3 //view preview images as a book
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
//            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    handler.apply {
//                        removeCallbacks(sliderRunnable)
//                        postDelayed(sliderRunnable, 3000)
//                    }
//                }
//            })
        }
        slideAdapter.setData(listSlideImage)
    }

//    private val sliderRunnable: Runnable = Runnable{
//        run {
//            binding.bannerOffers.apply {
//                currentItem += 1
//            }
//        }
//    }

    override fun onCategoryClicked(category: CategoryEntity, colorRGB: Int) {
        val categoryBundle = Bundle()
        categoryBundle.putParcelable("category", category)
        val intent = Intent(requireActivity(), CategoryActivity::class.java)
        intent.putExtras(categoryBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
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