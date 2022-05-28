package com.example.uproject.ui.modules.home.products

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.bitmap.BitmapPool
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.example.uproject.R
import com.example.uproject.databinding.ItemProductBinding
import com.example.uproject.domain.model.Product
import kotlin.math.roundToInt

class ProductListAdapter(
    private val productClickListener: OnProductClickListener,
    private val ctx: Context
): RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(){

    private var _productList = emptyList<Product>()

    interface OnProductClickListener{
        fun onProductClicked(product: Product, colorRGB: Int)
    }

    fun setData(data: List<Product>){
        this._productList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = _productList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int = _productList.size

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemProductBinding.bind(itemView)
        fun bindView(product: Product){
            binding.apply {
                var colorRGB = 0

                val imageLoader = ImageLoader.Builder(itemView.context).build()
                val request = ImageRequest.Builder(itemView.context)
                    .data(product.image)
                    .target(itemImageProduct)
                    .transformations(object: Transformation{
                        override fun key(): String = "paletteTransformer"
                        override suspend fun transform(
                            pool: BitmapPool,
                            input: Bitmap,
                            size: Size
                        ): Bitmap {
                            val palette = Palette.from(input).generate()
                            colorRGB = palette.getLightVibrantColor(0)
                            val swatch = palette.vibrantSwatch
                            if (swatch != null) {
                                //Una funcion para variar el color rgb
                                val hsv = swatch.hsl
                                colorRGB = swatch.rgb
                                Color.colorToHSV(colorRGB, hsv)
                                hsv[1] *= 0.7f
                                colorRGB = Color.HSVToColor(hsv)
                            }
                            return input
                        }
                    }).build()
                imageLoader.enqueue(request)

                itemNameProduct.text            = product.name
                itemMarkProduct.text            = product.mark
                itemWeightProduct.text          = product.weight

                if(product.offer != 0){
                    itemOfferProduct.text           = product.offer.toString().plus("% DESC")
                    itemRealPriceProduct.apply {
                        text = "S/ ${product.price}"
                        paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    val discountPrice   = product.price - product.price*product.offer/100
                    val finalPrice      = (discountPrice * 100.0).roundToInt() / 100.0
                    itemDiscountPriceProduct.text   = "S/ $finalPrice"
                }else{
                    viewDiscountSupport.visibility  = View.GONE
                    itemOfferProduct.visibility     = View.GONE
                    itemRealPriceProduct.visibility = View.GONE
                    itemDiscountPriceProduct.text   = "S/ ${product.price}"
                }

//                var isProductFavorite = false
//
//                icFavoriteProduct.apply{
//                    setOnClickListener {
//                        if(isProductFavorite) playAnimations(this, R.drawable.avd_animation_heart_2)
//                        else playAnimations(this, R.drawable.avd_animation_heart_1)
//                        isProductFavorite = !isProductFavorite
//                    }
//                }

                itemCardContainerProduct.setOnClickListener {
                    productClickListener.onProductClicked(product, colorRGB)
                }
            }
        }

//        private fun playAnimations(view: AppCompatImageView, animatedVectorDrawableID: Int){
//            view.setImageDrawable(ContextCompat.getDrawable(view.context,animatedVectorDrawableID))
//            val drawable = view.drawable
//            when(drawable){
//                is AnimatedVectorDrawable ->{
//                    drawable.start()
//                }
//                is AnimatedVectorDrawableCompat ->{
//                    drawable.start()
//                }
//            }
//        }
    }
}