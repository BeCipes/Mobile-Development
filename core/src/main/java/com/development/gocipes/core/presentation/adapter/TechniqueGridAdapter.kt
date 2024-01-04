package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.databinding.ItemInformationGridBinding
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.utils.Extensions.showImage

class TechniqueGridAdapter(val id: (Int) -> Unit) :
    ListAdapter<Technique, TechniqueGridAdapter.InformationGridViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationGridViewHolder {
        val binding =
            ItemInformationGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationGridViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class InformationGridViewHolder(private val binding: ItemInformationGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(technique: Technique) {
            binding.apply {
                sivGuide.showImage(itemView.context, technique.cover ?: "")
                tvName.text = technique.title
            }
            itemView.setOnClickListener { id.invoke(technique.id ?: 0) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Technique>() {
            override fun areItemsTheSame(oldItem: Technique, newItem: Technique) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Technique, newItem: Technique) =
                oldItem == newItem
        }
    }
}