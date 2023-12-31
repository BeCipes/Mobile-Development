package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.databinding.ItemInformationGridBinding
import com.development.gocipes.core.domain.model.information.Information
import com.development.gocipes.core.presentation.adapter.InformationAdapter.Companion.DIFF_CALLBACK
import com.development.gocipes.core.utils.Extensions.showImage

class InformationGridAdapter(val data: (Information) -> Unit) :
    ListAdapter<Information, InformationGridAdapter.InformationGridViewHolder>(DIFF_CALLBACK) {

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
        fun bind(information: Information) {
            binding.apply {
                sivGuide.showImage(itemView.context, information.imageUrl)
                tvName.text = information.name
            }
            itemView.setOnClickListener { data.invoke(information) }
        }
    }
}