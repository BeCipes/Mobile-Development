package com.development.gocipes.view.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.databinding.ItemCategoryBinding
import com.development.gocipes.model.Category
import com.development.gocipes.utils.Extensions.showImage

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                sivCategory.showImage(itemView.context, category.imageUrl)
                tvName.text = category.name
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem
        }
    }
}