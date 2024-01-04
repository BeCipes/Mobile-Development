package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.databinding.ItemGuideBinding
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.presentation.adapter.TechniqueGridAdapter.Companion.DIFF_CALLBACK
import com.development.gocipes.core.utils.Extensions.showImage

class TechniqueAdapter(val id: (Int) -> Unit) :
    ListAdapter<Technique, TechniqueAdapter.GuideViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        val binding = ItemGuideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GuideViewHolder(private val binding: ItemGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(technique: Technique) {
            binding.apply {
                sivGuide.showImage(itemView.context, technique.cover ?: "")
                tvName.text = technique.title
            }

            itemView.setOnClickListener { id.invoke(technique.id ?: 0) }
        }

    }
}