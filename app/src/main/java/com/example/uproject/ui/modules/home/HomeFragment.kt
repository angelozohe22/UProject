package com.example.uproject.ui.modules.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.uproject.core.Resource
import com.example.uproject.data.source.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.source.helpers.DulcekatDataBase
import com.example.uproject.data.source.local.LocalDataSourceImpl
import com.example.uproject.databinding.FragmentHomeBinding
import com.example.uproject.domain.model.Category
import com.example.uproject.domain.model.Product
import com.example.uproject.domain.model.SlideItem
import com.example.uproject.data.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.modules.home.category.CategoryActivity
import com.example.uproject.ui.modules.home.details.DetailsProductActivity
import com.example.uproject.ui.modules.home.products.ProductsActivity
import com.example.uproject.ui.modules.home.category.CategoryListAdapter
import com.example.uproject.ui.modules.home.products.ProductListAdapter
import com.example.uproject.ui.modules.home.offer.SlideAdapter
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

        //Ofertas
//        slideAdapter = SlideAdapter(binding.bannerOffers)

        setupCategoryRecyclerView()
        setupProductRecyclerView()
//        setupViewPager2()

        //Buscador
//        binding.searchViewContainer.setOnClickListener {
//            val intent = Intent(requireActivity(), SearchActivity::class.java)
//            startActivity(intent)
//        }

        binding.titleSeeAll.setOnClickListener {
            val intent = Intent(requireActivity(), ProductsActivity::class.java)
            startActivity(intent)
        }

        getCategoryList()
        getProductList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupCategoryRecyclerView(){
        binding.rvListCategory.apply{
            layoutManager = GridLayoutManager(requireContext(),4)
            adapter = categoryAdapter
        }
    }

    private fun getCategoryList(){
        viewModel.getCategoryList().observe(
            viewLifecycleOwner
        ){
            it?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        showProgressCategory()
                    }
                    is Resource.Success -> {
                        hideProgressCategory()
                        val data = result.data
                        categoryAdapter.setData(data)
                    }
                    is Resource.Failure -> {
                    }
                }
            }
        }
    }

    private fun setupProductRecyclerView(){
        binding.rvListProduct.apply{
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = productAdapter
        }
    }

    private fun getProductList(){
        viewModel.getProductList().observe(
            viewLifecycleOwner
        ){
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        showProgressProduct()
                    }
                    is Resource.Success -> {
                        hideProgressProduct()
                        val data = result.data
                        productAdapter.setData(data)
                    }
                    is Resource.Failure -> {
                    }
                }
            }
        }
    }

    //ofertas
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

        //Ofertas
//        binding.bannerOffers.apply {
//            adapter = slideAdapter
//            clipToPadding = false
//            clipChildren = false
//            offscreenPageLimit = 3 //view preview images as a book
//            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//            setPageTransformer(compositePageTransformer)
////            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
////                override fun onPageSelected(position: Int) {
////                    super.onPageSelected(position)
////                    handler.apply {
////                        removeCallbacks(sliderRunnable)
////                        postDelayed(sliderRunnable, 3000)
////                    }
////                }
////            })
//        }
        slideAdapter.setData(listSlideImage)
    }

//    private val sliderRunnable: Runnable = Runnable{
//        run {
//            binding.bannerOffers.apply {
//                currentItem += 1
//            }
//        }
//    }

    private fun showProgressCategory() {
        binding.progressCategory.startShimmer()
        binding.progressCategory.visibility = View.VISIBLE
        binding.rvListCategory.visibility = View.INVISIBLE
    }

    private fun hideProgressCategory(){
        binding.progressCategory.stopShimmer()
        binding.progressCategory.visibility = View.INVISIBLE
        binding.rvListCategory.visibility = View.VISIBLE
    }

    private fun showProgressProduct() {
        binding.progressProduct.startShimmer()
        binding.progressProduct.visibility = View.VISIBLE
        binding.rvListProduct.visibility = View.INVISIBLE
    }

    private fun hideProgressProduct(){
        binding.progressProduct.stopShimmer()
        binding.progressProduct.visibility = View.INVISIBLE
        binding.rvListProduct.visibility = View.VISIBLE
    }

    override fun onCategoryClicked(category: Category, colorRGB: Int) {
        val categoryBundle = Bundle()
        categoryBundle.putParcelable("category", category)
        val intent = Intent(requireActivity(), CategoryActivity::class.java)
        intent.putExtras(categoryBundle)
        intent.putExtra("colorRGB", colorRGB)
        startActivity(intent)
    }

    override fun onProductClicked(product: Product, colorRGB: Int) {
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