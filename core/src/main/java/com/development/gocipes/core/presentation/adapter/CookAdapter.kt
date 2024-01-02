package com.development.gocipes.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.development.gocipes.core.R
import com.development.gocipes.core.data.remote.response.step.StepItem
import com.development.gocipes.core.databinding.ItemCookBinding
import com.development.gocipes.core.utils.Extensions.showImage

class CookAdapter : ListAdapter<StepItem, CookAdapter.CookViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookViewHolder {
        val binding = ItemCookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CookViewHolder(private val binding: ItemCookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: StepItem) {
            binding.apply {
                sivCook.showImage(itemView.context, step.gambar ?: "")
                tvStep.text = itemView.context.getString(R.string.by_step, step.stepNo.toString())
                tvDescription.text = step.stepDesc
            }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<StepItem>() {
                override fun areItemsTheSame(oldItem: StepItem, newItem: StepItem) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: StepItem, newItem: StepItem) =
                    oldItem == newItem
            }
    }
}