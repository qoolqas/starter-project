package com.example.starterproject.presentation.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.starterproject.R
import com.example.starterproject.databinding.ItemCartBinding
import com.example.starterproject.ui_model.ProductUiModel
import com.example.starterproject.utils.toIdrCurrency

class CartAdapter(
) : ListAdapter<ProductUiModel, CartAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductUiModel) {
            binding.apply {
                tvTitle.text = data.name
                tvDesc.text = data.sortDescription
                tvPrice.text = data.price.toIdrCurrency()
                ivImg.load(
                    data.images
                )
                tvTotalQty.text = binding.root.context.getString(R.string.total_qty, data.totalCart.toString())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductUiModel>() {
            override fun areItemsTheSame(
                oldItem: ProductUiModel,
                newItem: ProductUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductUiModel,
                newItem: ProductUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}