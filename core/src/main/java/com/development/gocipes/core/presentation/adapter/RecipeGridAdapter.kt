package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.databinding.ItemFoodGridBinding
import com.development.gocipes.core.presentation.adapter.RecipeAdapter.Companion.DIFF_CALLBACK
import com.development.gocipes.core.utils.Extensions.showImage

class RecipeGridAdapter(val id: (Int) -> Unit) :
    ListAdapter<CategoryItem, RecipeGridAdapter.FoodGridViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGridViewHolder {
        val binding =
            ItemFoodGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodGridViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodGridViewHolder(private val binding: ItemFoodGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryItem) {
            val food = category.resep
            binding.apply {
                sivFood.showImage(itemView.context, food?.gambar ?: "")
                tvName.text = food?.namaResep
                tvMinutes.text = "${category.resep?.id} menit"
                tvCategory.text = category.kategori.namaKategori
            }

            itemView.setOnClickListener { id.invoke(category.id ?: 0) }
        }
    }
}