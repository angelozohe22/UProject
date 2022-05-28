package com.example.uproject.ui.modules.bag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.uproject.R
import com.example.uproject.data.source.local.entity.BagProductEntity
import com.example.uproject.databinding.ItemProductBagBinding
import kotlin.math.roundToInt

class BagAdapter(
    private val bagList: List<BagProductEntity>,
    private val onProductBagClickListener: OnProductBagClickListener
): RecyclerView.Adapter<BagAdapter.BagViewHolder>() {

    interface OnProductBagClickListener{
        fun onProductBagClicked(idproduct: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        return BagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_bag, parent, false))
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        val bagProduct = bagList[position]
        holder.bindView(bagProduct)
    }

    override fun getItemCount(): Int = bagList.size

    inner class BagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemProductBagBinding.bind(itemView)
        fun bindView(bagProduct: BagProductEntity){
            binding.apply {
                itemImageProductBag.load(bagProduct.image)
                itemNameProductBag.text         = bagProduct.name
                itemMarkProductBag.text         = bagProduct.mark
                itemWeightProductBag.text       = bagProduct.weight
                itemQuantityProductBag.text     = "x".plus(bagProduct.quantity)
                val price = bagProduct.price_by_one*bagProduct.quantity
                val finalPrice = (price * 100.0).roundToInt() / 100.0
                itemRealPriceProductBag.text    = "S/ ".plus(finalPrice)

                btnDeleteProductBag.setOnClickListener {
                    onProductBagClickListener.onProductBagClicked(bagProduct.idproduct)
                }
            }
        }
    }

}