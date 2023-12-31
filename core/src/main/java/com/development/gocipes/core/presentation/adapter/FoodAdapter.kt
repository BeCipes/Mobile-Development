package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.databinding.ItemFoodBinding
import com.development.gocipes.core.domain.model.food.Food
import com.development.gocipes.core.utils.Extensions.showImage

class FoodAdapter(val data: (Food) -> Unit) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.apply {
                sivFood.showImage(itemView.context, food.imageUrl)
                tvName.text = food.name
                tvMinutes.text = food.minutes
                tvCategory.text = food.category
            }

            itemView.setOnClickListener { data.invoke(food) }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Food>() {
                override fun areItemsTheSame(oldItem: Food, newItem: Food) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: Food, newItem: Food) =
                    oldItem == newItem
            }
    }
}