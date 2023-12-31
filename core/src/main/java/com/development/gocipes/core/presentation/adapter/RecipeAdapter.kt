package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.databinding.ItemFoodBinding
import com.development.gocipes.core.utils.Extensions.showImage

class RecipeAdapter : ListAdapter<FoodItem, RecipeAdapter.FoodViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodItem) {
            binding.apply {
                sivFood.showImage(itemView.context, food.gambar ?: "")
                tvName.text = food.namaResep
                tvMinutes.text = food.namaResep
                tvCategory.text = food.deskripsi
            }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<FoodItem>() {
                override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem) =
                    oldItem == newItem
            }
    }
}