package com.example.uproject.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.uproject.common.FirebaseFirestore
import com.example.uproject.databinding.FragmentHomeBinding
import com.example.uproject.domain.model.CategoryDocument
import com.example.uproject.domain.model.Product
import com.example.uproject.domain.model.SlideItem
import com.example.uproject.ui.activities.home.contactus.ContactUsActivity
import com.example.uproject.ui.activities.home.products.ProductsActivity
import com.example.uproject.ui.activities.home.search.SearchActivity
import com.example.uproject.ui.fragments.home.adapter.CategoryListAdapter
import com.example.uproject.ui.fragments.home.adapter.ProductListAdapter
import com.example.uproject.ui.fragments.home.adapter.SlideAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var slideAdapter: SlideAdapter
    private val categoryAdapter by lazy { CategoryListAdapter() }
    private val productAdapter   by lazy { ProductListAdapter() }
    private val dbReference       by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }

    private fun setupCategoryRecyclerView(){
        binding.rvListCategory.apply{
            layoutManager = GridLayoutManager(requireContext(),4)
            adapter = categoryAdapter
        }

        /*//Esto es de prueba, debemos manejar MVVM con esto
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO){
                dbReference.collection("Category")
                    .document("categories").get().await()
            }
            if(result != null){
                val snapshot = result.toObject(CategoryDocument::class.java)?.categoryList ?: emptyList()
                categoryAdapter.setData(snapshot)
            }
        }*/
    }

    private fun setupProductRecyclerView(){
        binding.rvListProduct.apply{
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = productAdapter
        }
        val productList = listOf(
            Product("Mantequilla sin sal", "https://i.imgur.com/VUGHecK.png", "Sello de Oro", "2 Kgram", 8.89, 20, "lala", 15),
            Product("Mantequilla con sal", "https://i.imgur.com/VUGHecK.png", "Sello de Oro", "2 Kgram", 8.89, 20, "lala", 15),
            Product("Harina preparada", "https://i.imgur.com/VUGHecK.png", "Blanca Flor", "1 Kgram", 8.89, 20, "lala", 15),
            Product("Cobertura bitter", "https://i.imgur.com/VUGHecK.png", "Negusa", "2 Kgram", 8.89, 20, "lala", 15)
        )
        productAdapter.setData(productList)
    }

    private fun setupViewPager2(){
//        val handler = Handler(Looper.getMainLooper())
        val listSlideImage = listOf(
            SlideItem("https://i.imgur.com/z0oxC37.png"),
            SlideItem("https://i.imgur.com/mUi99XN.png"),
            SlideItem("https://i.imgur.com/ZXCJahq.png"),
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}