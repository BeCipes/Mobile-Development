package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.databinding.ItemAnalysisBinding
import com.development.gocipes.core.utils.Extensions.showImage

class AnalysisAdapter(val id: (Int) -> Unit) :
    ListAdapter<IngridientItem, AnalysisAdapter.AnalysisViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisViewHolder {
        val binding =
            ItemAnalysisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnalysisViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnalysisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnalysisViewHolder(private val binding: ItemAnalysisBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(analysis: IngridientItem) {
            binding.apply {
                ivAnalysis.showImage(itemView.context, analysis.gambar ?: "")
                tvHeadIngridient.text = analysis.namaBahan
                tvDescIngridient.text = analysis.deskripsi
            }
            itemView.setOnClickListener { id.invoke(analysis.id ?: 0) }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<IngridientItem>() {
                override fun areItemsTheSame(
                    oldItem: IngridientItem,
                    newItem: IngridientItem,
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: IngridientItem,
                    newItem: IngridientItem,
                ) = oldItem == newItem
            }
    }
}