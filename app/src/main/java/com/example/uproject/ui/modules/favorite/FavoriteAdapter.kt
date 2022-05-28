package com.example.uproject.ui.modules.favorite

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
import com.example.uproject.databinding.ItemProductFavoriteBinding
import com.example.uproject.domain.model.Product
import com.example.uproject.ui.modules.home.products.ProductListAdapter

class FavoriteAdapter(
    private val favoriteList: List<Product>,
    private val favoriteClickListener: ProductListAdapter.OnProductClickListener
):RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_favorite, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favoriteList[position]
        holder.bindView(favorite)
    }

    override fun getItemCount(): Int = favoriteList.size

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemProductFavoriteBinding.bind(itemView)
        fun bindView(product: Product){
            binding.apply {
                var colorRGB = 0

                val imageLoader = ImageLoader.Builder(itemView.context).build()
                val request = ImageRequest.Builder(itemView.context)
                    .data(product.image)
                    .target(itemImageFavoriteProduct)
                    .transformations(object: Transformation {
                        override fun key(): String = "paletteTransformer"
                        override suspend fun transform(
                            pool: BitmapPool,
                            input: Bitmap,
                            size: Size
                        ): Bitmap {
                            val palette = Palette.from(input).generate()
                            colorRGB = palette.getLightVibrantColor(0)
                            favoriteContainer.setBackgroundColor(colorRGB)
                            val swatch = palette.vibrantSwatch
                            if (swatch != null) {
                                //Una funcion para variar el color rgb
                                val hsv = swatch.hsl
                                colorRGB = swatch.rgb
                                Color.colorToHSV(colorRGB, hsv)
                                hsv[1] *= 0.7f
                                colorRGB = Color.HSVToColor(hsv)
                                favoriteContainer.setBackgroundColor(colorRGB)
                            }
                            return input
                        }
                    }).build()
                imageLoader.enqueue(request)


                itemNameProductFavorite.text = product.name
                itemMarkProductFavorite.text = product.mark
                itemWeightProductFavorite.text = product.weight

                cardContainerFavorite.setOnClickListener {
                    favoriteClickListener.onProductClicked(product, colorRGB)
                }
            }
        }
    }

}