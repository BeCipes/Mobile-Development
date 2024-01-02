package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.databinding.ItemSearchBinding
import com.development.gocipes.core.domain.model.food.Food
import com.development.gocipes.core.utils.Extensions.showImage
import java.util.Locale

class SearchAdapter(val data: (Food) -> Unit) : ListAdapter<Food, SearchAdapter.SearchViewHolder>(DIFF_CALLBACK) {

    private var unFilteredList = listOf<Food>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.apply {
                sivFood.showImage(itemView.context, food.imageUrl)
                tvName.text = food.name
            }

            itemView.setOnClickListener { data.invoke(food) }
        }
    }

    fun modifyList(list: List<Food>) {
        unFilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<Food>()

        if (!query.isNullOrEmpty()) {
            list.addAll(unFilteredList.filter {
                it.name.lowercase(Locale.getDefault())
                    .contains(query.toString().lowercase(Locale.getDefault()))
            })
        } else {
            list.addAll(unFilteredList)
        }

        submitList(list)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem == newItem
            }
        }
    }
}