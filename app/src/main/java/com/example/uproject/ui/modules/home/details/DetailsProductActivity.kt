package com.example.uproject.ui.modules.home.details

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import coil.load
import com.example.uproject.R
import com.example.uproject.common.utils.setStatusBarColor
import com.example.uproject.core.Resource
import com.example.uproject.core.ctx
import com.example.uproject.core.preferences
import com.example.uproject.data.source.Remote.home.RemoteFirestoreDataSourceImpl
import com.example.uproject.data.source.helpers.DulcekatDataBase
import com.example.uproject.data.source.local.entity.OrderByProductEntity
import com.example.uproject.data.source.local.entity.OrderEntity
import com.example.uproject.data.source.local.LocalDataSourceImpl
import com.example.uproject.databinding.ActivityDetailsProductBinding
import com.example.uproject.domain.model.Product
import com.example.uproject.data.repository.DulcekatRepositoryImpl
import com.example.uproject.ui.modules.home.products.ProductListAdapter
import com.example.uproject.ui.modules.home.HomeViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DetailsProductActivity : AppCompatActivity(), ProductListAdapter.OnProductClickListener {

    private lateinit var binding: ActivityDetailsProductBinding
    private lateinit var productEntity: Product
    private val detailsProductAdapter by lazy { ProductListAdapter(this, this) }

    private var colorRGB = 0
    private var currentQuantity = 1

    private val viewModel by viewModels<ProductDetailsViewModel>{
        HomeViewModelFactory(
            DulcekatRepositoryImpl(
                LocalDataSourceImpl(DulcekatDataBase.getInstance(this)),
                RemoteFirestoreDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null){
            productEntity  = intent.getParcelableExtra<Product>("product") as Product
            colorRGB       = intent.getIntExtra("colorRGB", 0)
        }

        val nameKey = productEntity.name.substring(0,4)
        val idProduct = productEntity.idproduct
        viewModel.getListSimilarProducts(idProduct, nameKey)

        binding.btnBackDetailsProduct.setOnClickListener {
            onBackPressed()
        }

        setupProduct()
        setupFavoriteProduct()
        setupQuantity()
        setupRecycler()
        setupAddToBag()
    }

    private fun setupProduct(){
        val imgDetailsProduct               = binding.imgDetailsProduct
        val cardDetailsProductSupport       = binding.cardDetailsProductSupport
        val viewSupportDiscountDetails      = binding.viewSupportDiscountDetails
        val tvDiscountDetailsProduct        = binding.tvDiscountDetailsProduct
        val tvNameDetailsProduct            = binding.tvNameDetailsProduct
        val tvMarkDetailsProduct            = binding.tvMarkDetailsProduct
        val tvWeightDetailsProduct          = binding.tvWeightDetailsProduct
        val tvDescriptionDetailsProduct     = binding.tvDescriptionDetailsProduct
        val tvRealPriceDetailsProduct       = binding.tvRealPriceDetailsProduct
        val tvDiscountPriceDetailsProduct   = binding.tvDiscountPriceDetailsProduct

        cardDetailsProductSupport.setCardBackgroundColor(colorRGB)
        imgDetailsProduct.load(productEntity.image)
        tvNameDetailsProduct.text           = productEntity.name
        tvMarkDetailsProduct.text           = productEntity.mark
        tvWeightDetailsProduct.text         = productEntity.weight
        tvDescriptionDetailsProduct.text    = productEntity.description

        if(productEntity.offer != 0){
            val offer = productEntity.offer
            val price = productEntity.price
            val discountPrice   = price - price*offer/100
            val finalPrice      = (discountPrice * 100.0).roundToInt() / 100.0

            tvDiscountDetailsProduct.text           = productEntity.offer.toString().plus("% DESC")
            tvRealPriceDetailsProduct.text          = "S/ $price"
            tvRealPriceDetailsProduct.paintFlags    = tvRealPriceDetailsProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvDiscountPriceDetailsProduct.text      = "S/ $finalPrice"
        }else{
            viewSupportDiscountDetails.visibility   = View.GONE
            tvDiscountDetailsProduct.visibility     = View.GONE
            tvRealPriceDetailsProduct.visibility    = View.GONE
            tvDiscountPriceDetailsProduct.text      = "S/ ${productEntity.price}"
        }

        setStatusBarColor(this, colorRGB)
    }

    private fun setupFavoriteProduct(){
        val btnFavoriteDetailsProduct = binding.btnFavoriteDetailsProduct
        var isProductFavorite = productEntity.isfavorite == 1

        if(isProductFavorite){
            btnFavoriteDetailsProduct.setImageDrawable(
                ContextCompat.getDrawable(
                    btnFavoriteDetailsProduct.context,
                    R.drawable.avd_animation_heart_2
                )
            )
        }else{
            btnFavoriteDetailsProduct.setImageDrawable(
                ContextCompat.getDrawable(
                    btnFavoriteDetailsProduct.context,
                    R.drawable.avd_animation_heart_1
                )
            )
        }

        btnFavoriteDetailsProduct.apply {
            setOnClickListener {
                if (isProductFavorite) {
                    playAnimations(this, R.drawable.avd_animation_heart_2)
                    viewModel.updateProductToFavorite(productEntity.idproduct, 0)
                    Toast.makeText(applicationContext, "Eliminado de favoritos", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    playAnimations(this, R.drawable.avd_animation_heart_1)
                    viewModel.updateProductToFavorite(productEntity.idproduct, 1)
                    Toast.makeText(applicationContext, "Agregado a favoritos", Toast.LENGTH_SHORT)
                        .show()
                }
                isProductFavorite = !isProductFavorite
            }

        }
    }

    private fun playAnimations(view: AppCompatImageView, animatedVectorDrawableID: Int){
        view.setImageDrawable(ContextCompat.getDrawable(view.context, animatedVectorDrawableID))
        val drawable = view.drawable
        when(drawable){
            is AnimatedVectorDrawable -> {
                drawable.start()
            }
            is AnimatedVectorDrawableCompat -> {
                drawable.start()
            }
        }
    }

    private fun setupQuantity(){
        val tvQuantityProduct       = binding.tvQuantityProduct
        val btnIncreaseQuantity  = binding.btnIncreaseQuantity
        val btnDecreaseQuantity  = binding.btnDecreaseQuantity
        val btnAddBagProduct    = binding.btnAddBagProduct

        btnDecreaseQuantity.apply {
            isEnabled   = false
            setBackgroundResource(R.drawable.btn_corner_quantity_disable)
        }

        if(productEntity.quantity > 0){

            btnIncreaseQuantity.setOnClickListener {
                viewModel.increaseQuantity()
            }

            btnDecreaseQuantity.setOnClickListener {
                viewModel.decreaseQuantity()
            }

            viewModel.count.observe(this, Observer { count->
                tvQuantityProduct.text = (if(count<10) "0" else "").plus(count)
                btnIncreaseQuantity.apply {
                    isEnabled = count != productEntity.quantity
                    if(isEnabled) setBackgroundResource(R.drawable.btn_corner_quantity)
                    else setBackgroundResource(R.drawable.btn_corner_quantity_disable)
                }
                btnDecreaseQuantity.apply {
                    isEnabled = count>1
                    if(isEnabled) setBackgroundResource(R.drawable.btn_corner_quantity)
                    else setBackgroundResource(R.drawable.btn_corner_quantity_disable)
                }
                currentQuantity = count
            })

        }else{
            tvQuantityProduct.text = "00"
            btnIncreaseQuantity.apply {
                isEnabled   = false
                setBackgroundResource(R.drawable.btn_corner_quantity_disable)
            }
            btnAddBagProduct.apply {
                isEnabled      = false
                setBackgroundResource(R.drawable.btn_details_bag_disable)
            }
            Toast.makeText(this, "No tenemos productos en stock", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecycler(){
        val nameKey = productEntity.name.substring(0,4)
        val idProduct = productEntity.idproduct

        binding.rvOtherProducts.apply {
            layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL,false)
            adapter = detailsProductAdapter
        }

        viewModel.getListSimilarProducts(idProduct, nameKey).observe(this, Observer {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val listSimilarProducts = result.data
                        detailsProductAdapter.setData(listSimilarProducts)
                    }
                    is Resource.Failure -> {}
                }
            }
        })


    }

    private fun setupAddToBag(){
        val btnAddBagProduct  = binding.btnAddBagProduct

        val idProduct = productEntity.idproduct
        val price = if(productEntity.offer != 0){
            val discountPrice   = productEntity.price - productEntity.price*productEntity.offer/100
            (discountPrice * 100.0).roundToInt() / 100.0
        }else productEntity.price

        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val currentDate = sdf.format(Date())

        val flagOrder = preferences.flagOrder
        if(flagOrder == 0){
            viewModel.insertNewOrder(OrderEntity(0, preferences.deviceToken.toString(), currentDate, false))
            preferences.apply {
                this.flagOrder = 1
            }
        }

        btnAddBagProduct.setOnClickListener {
            viewModel.fetchListOrders().observe(this, Observer {
                it?.let { result ->
                    if (result.isEmpty()) {
                        viewModel.insertNewOrder(
                            OrderEntity(
                                0,
                                preferences.deviceToken.toString(),
                                currentDate,
                                false
                            )
                        )
                    } else {
                        Log.e("orders", result.size.toString())
                        Log.e("orders", result.toString())
                        val order = result.lastOrNull()
                        Log.e("orders", order.toString())
                        order?.let {
                            viewModel.insertOrderByProduct(
                                OrderByProductEntity(
                                    0,
                                    idProduct,
                                    it.idorder,
                                    currentQuantity,
                                    price
                                )
                            )
                            Toast.makeText(this, "Añadido a la bolsa", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

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