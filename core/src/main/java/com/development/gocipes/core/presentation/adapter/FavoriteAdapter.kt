package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.data.remote.response.favorite.GetFavoriteItem
import com.development.gocipes.core.databinding.ItemFavoriteBinding
import com.development.gocipes.core.domain.model.favorite.Favorite
import com.development.gocipes.core.utils.Extensions.showImage

class FavoriteAdapter (val data: (String) -> Unit) :
    ListAdapter<GetFavoriteItem, FavoriteAdapter.FavoriteViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: GetFavoriteItem) {
//            val categoryNames = favorite.category.map { it.name }

            binding.apply {
                sivFavorite.showImage(itemView.context, favorite.resep?.gambar ?: "")
                tvTitle.text = favorite.resep?.namaResep
//                tvCategory.text = categoryNames.joinToString(", ")
                tvTimerContainer.tvTime.text = "20 menit"
            }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<GetFavoriteItem>() {
                override fun areItemsTheSame(
                    oldItem: GetFavoriteItem,
                    newItem: GetFavoriteItem
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: GetFavoriteItem,
                    newItem: GetFavoriteItem
                ) = oldItem == newItem
            }
    }
}