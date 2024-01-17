package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.databinding.ItemSearchBinding
import com.development.gocipes.core.utils.Extensions.showImage
import java.util.Locale

class SearchAdapter(val data: (FoodItem) -> Unit) :
    ListAdapter<FoodItem, SearchAdapter.SearchViewHolder>(DIFF_UTIL) {

    private var unFilteredList = listOf<FoodItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodItem) {
            binding.apply {
                sivFood.showImage(itemView.context, food.gambar ?: "")
                tvName.text = food.namaResep
            }

            itemView.setOnClickListener { data.invoke(food) }
        }
    }

    fun modifyList(list: List<FoodItem>) {
        unFilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<FoodItem>()

        if (!query.isNullOrEmpty()) {
            list.addAll(unFilteredList.filter {
                it.namaResep?.lowercase(Locale.getDefault())
                    ?.contains(query.toString().lowercase(Locale.getDefault())) ?: false
            })
        } else {
            list.addAll(unFilteredList)
        }

        submitList(list)
    }

    companion object {
        val DIFF_UTIL =
            object : DiffUtil.ItemCallback<FoodItem>() {
                override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem) =
                    oldItem == newItem
            }
    }
}