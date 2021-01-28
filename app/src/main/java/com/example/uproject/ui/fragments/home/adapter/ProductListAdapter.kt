package com.example.uproject.ui.fragments.home.adapter

import android.graphics.Paint
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import coil.load
import com.example.uproject.R
import com.example.uproject.databinding.ItemProductBinding
import com.example.uproject.domain.model.Product

class ProductListAdapter: RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(){

    private var _productList = emptyList<Product>()
    private var switchNumber: Int = 0
    fun setData(data: List<Product>){
        this._productList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapter.ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ProductViewHolder, position: Int) {
        val product = _productList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int = _productList.size

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemProductBinding.bind(itemView)
        fun bindView(product: Product){
            binding.apply {
                itemImageProduct.load(product.image)
                itemNameProduct.text            = product.name
                itemMarkProduct.text            = product.mark
                itemWeightProduct.text          = product.weight
                itemOfferProduct.text           = product.offer.toString().plus("% DESC")
                //itemRealPriceProduct.text       = "S/ ${product.price}"
                itemRealPriceProduct.paintFlags = itemRealPriceProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                //Log.e("price", product.price.toString())
                val discountPrice   = product.price - product.price*product.offer/100
                //Log.e("discountPrice", discountPrice.toString())
                val finalPrice      = Math.round(discountPrice*100.0) / 100.0
                //Log.e("finalPrice", finalPrice.toString())
                itemDiscountPriceProduct.text   = "S/ $finalPrice"

                icFavoriteProduct.apply{
                    setOnClickListener {
                        when(switchNumber){
                            0 ->{
                                playAnimations(this, R.drawable.avd_animation_heart_1)
                                switchNumber++
                            }
                            1 ->{
                                playAnimations(this, R.drawable.avd_animation_heart_2)
                                switchNumber--
                            }
                        }
                    }
                }
            }
        }

        private fun playAnimations(view: AppCompatImageView, animatedVectorDrawableID: Int){
            view.setImageDrawable(ContextCompat.getDrawable(view.context,animatedVectorDrawableID))
            val drawable = view.drawable
            when(drawable){
                is AnimatedVectorDrawable ->{
                    drawable.start()
                }
                is AnimatedVectorDrawableCompat ->{
                    drawable.start()
                }
            }
        }
    }
}