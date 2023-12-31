package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.databinding.ItemFoodBinding
import com.development.gocipes.core.utils.Extensions.showImage

class RecipeAdapter(val id: (Int) -> Unit) :
    ListAdapter<CategoryItem, RecipeAdapter.FoodViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryItem: CategoryItem) {
            binding.apply {
                sivFood.showImage(itemView.context, categoryItem.resep?.gambar ?: "")
                tvName.text = categoryItem.resep?.namaResep
                tvMinutes.text = categoryItem.resep?.namaResep
                tvCategory.text = categoryItem.resep?.deskripsi
            }

            itemView.setOnClickListener { id.invoke(categoryItem.id ?: 0) }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<CategoryItem>() {
                override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem) =
                    oldItem == newItem
            }
    }
}