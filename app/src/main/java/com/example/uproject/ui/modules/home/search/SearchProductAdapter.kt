package com.example.uproject.ui.modules.home.search

import android.graphics.Bitmap
import android.graphics.Color
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
import com.example.uproject.databinding.ItemProductSearchListBinding
import com.example.uproject.domain.model.Product
import com.example.uproject.ui.modules.home.products.ProductListAdapter
import kotlin.math.roundToInt

class SearchProductAdapter(
    private val productList: List<Product>,
    private val productClickListener: ProductListAdapter.OnProductClickListener
): RecyclerView.Adapter<SearchProductAdapter.SearchViewProductViewHolder>() {

//    private var _productSearchList = emptyList<ProductEntity>()
//
//    fun setData(data: List<ProductEntity>){
//        this._productSearchList = data
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewProductViewHolder {
        return SearchViewProductViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_product_search_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int = productList.size

    inner class SearchViewProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemProductSearchListBinding.bind(itemView)
        fun bindView(product: Product){
            binding.apply {
                var colorRGB = 0

                val imageLoader = ImageLoader.Builder(itemView.context).build()
                val request = ImageRequest.Builder(itemView.context)
                    .data(product.image)
                    .target(itemImageProductSearch)
                    .transformations(object: Transformation {
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

                itemNameProductSearch.text      = product.name
                itemMarkProductSearch.text      = product.mark
                itemWeightProductSearch.text    = product.weight

                if(product.offer != 0){
                    val discountPrice   = product.price - product.price*product.offer/100
                    val finalPrice      = (discountPrice * 100.0).roundToInt() / 100.0
                    itemRealPriceProductSearch.text = "S/ $finalPrice"
                }else{
                    itemRealPriceProductSearch.text = "S/ ${product.price}"
                }

                itemCardContainerSearch.setOnClickListener {
                    productClickListener.onProductClicked(product, colorRGB)
                }
            }
        }
    }

}