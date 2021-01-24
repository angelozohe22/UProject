package com.example.uproject.ui.activities.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.uproject.R
import com.example.uproject.databinding.ItemProductSearchListBinding
import com.example.uproject.domain.model.Product

class SearchProductAdapter: RecyclerView.Adapter<SearchProductAdapter.SearchViewProductViewHolder>() {

    private var _productSearchList = emptyList<Product>()

    fun setData(data: List<Product>){
        this._productSearchList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewProductViewHolder {
        return SearchViewProductViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_product_search_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewProductViewHolder, position: Int) {
        val product = _productSearchList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int = _productSearchList.size

    inner class SearchViewProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemProductSearchListBinding.bind(itemView)
        fun bindView(product: Product){
            binding.apply {
                itemImageProductSearch.load(product.image)
                itemNameProductSearch.text      = product.name
                itemMarkProductSearch.text      = product.name
                itemWeightProductSearch.text    = product.weight
                itemRealPriceProductSearch.text = "S/ ${product.price}"
            }
        }
    }

}