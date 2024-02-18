package com.example.starterproject.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starterproject.data.ProductResponse
import com.example.starterproject.databinding.ItemProductBinding

class ProductAdapter : ListAdapter<ProductResponse, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductResponse) {
            binding.apply {
                tvProductName.text = data.name
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductResponse>() {
            override fun areItemsTheSame(
                oldItem: ProductResponse,
                newItem: ProductResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductResponse,
                newItem: ProductResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}