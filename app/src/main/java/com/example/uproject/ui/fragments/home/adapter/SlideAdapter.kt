package com.example.uproject.ui.fragments.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.uproject.R
import com.example.uproject.databinding.ItemSlideBinding
import com.example.uproject.domain.model.SlideItem

class SlideAdapter(private val viewPager2: ViewPager2) : RecyclerView.Adapter<SlideAdapter.SlideAdapterViewHolder>() {

    private var _slideItemList = emptyList<SlideItem>()

    fun setData(data: List<SlideItem>){
        this._slideItemList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SlideAdapter.SlideAdapterViewHolder {
        return SlideAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false))
    }

    override fun onBindViewHolder(holder: SlideAdapter.SlideAdapterViewHolder, position: Int) {
        val slideItem = _slideItemList[position]
        holder.bindView(slideItem)
    }

    override fun getItemCount(): Int = _slideItemList.size

    inner class SlideAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemSlideBinding.bind(itemView)
        fun bindView(slideItem: SlideItem){
            binding.imageSlide.load(slideItem.image)
        }
    }

}