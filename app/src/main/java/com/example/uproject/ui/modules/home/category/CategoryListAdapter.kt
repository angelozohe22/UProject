package com.example.uproject.ui.modules.home.category

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.bitmap.BitmapPool
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.example.uproject.R
import com.example.uproject.databinding.ItemCategoryBinding
import com.example.uproject.domain.model.Category

class CategoryListAdapter(
    private val categoryClickListener: OnCategoryClickListener
):RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private var _categoryList = emptyList<Category>()

    interface OnCategoryClickListener{
        fun onCategoryClicked(category: Category, colorRGB: Int)
    }

    fun setData(data: List<Category>){
        this._categoryList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = _categoryList[position]
        holder.bindView(category)
    }

    override fun getItemCount(): Int = _categoryList.size

    inner class CategoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val binding = ItemCategoryBinding.bind(itemView)
        fun bindView(category: Category){
            binding.apply {
                itemCategoryName.text = category.name
                var colorRGB = 0
                val imageLoader = ImageLoader.Builder(itemView.context)
                    .componentRegistry {
                        add(SvgDecoder(itemView.context))
                    }.build()
                val request = ImageRequest.Builder(itemView.context)
                    .data(category.imageUrl)
                    .target(itemImageCategory)
                    .transformations(object : Transformation{
                        override fun key(): String = "paletteTransformer"
                        override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
                            val palette = Palette.from(input).generate()
                            itemCardCategory.setCardBackgroundColor(palette.getLightVibrantColor(0))
                            val swatch = palette.vibrantSwatch
                            if (swatch != null) {
                                //Una funcion para variar el color rgb
                                val hsv = swatch.hsl
                                colorRGB = swatch.rgb
                                Color.colorToHSV(colorRGB, hsv)
                                hsv[1] *= 0.7f
                                colorRGB = Color.HSVToColor(hsv)
                                println("color RGB -> $colorRGB")
                                itemCardCategory.setCardBackgroundColor(colorRGB)
                            }
                            return input
                        }
                    }).build()
                imageLoader.enqueue(request)
                itemCardCategory.setOnClickListener {
                    categoryClickListener.onCategoryClicked(category, colorRGB)
                }
            }
        }
    }

}