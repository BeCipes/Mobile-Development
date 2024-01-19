package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.databinding.ItemSearchBinding
import com.development.gocipes.core.utils.Extensions.showImage
import java.util.Locale

class SearchAnalysisAdapter(val id: (Int) -> Unit) :
    ListAdapter<IngridientItem, SearchAnalysisAdapter.SearchViewHolder>(DIFF_UTIL) {

    private var unFilteredList = listOf<IngridientItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun  bind(food: IngridientItem) {
            binding.apply {
                sivFood.showImage(itemView.context, food.gambar ?: "")
                tvName.text = food.namaBahan
            }

            itemView.setOnClickListener { id.invoke(food.id ?: 0) }
        }
    }

    fun modifyList(list: List<IngridientItem>) {
        unFilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<IngridientItem>()

        if (!query.isNullOrEmpty()) {
            list.addAll(unFilteredList.filter {
                it.namaBahan?.lowercase(Locale.getDefault())
                    ?.contains(query.toString().lowercase(Locale.getDefault())) ?: false
            })
        } else {
            list.addAll(unFilteredList)
        }

        submitList(list)
    }

    companion object {
        val DIFF_UTIL =
            object : DiffUtil.ItemCallback<IngridientItem>() {
                override fun areItemsTheSame(oldItem: IngridientItem, newItem: IngridientItem) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: IngridientItem, newItem: IngridientItem) =
                    oldItem == newItem
            }
    }
}