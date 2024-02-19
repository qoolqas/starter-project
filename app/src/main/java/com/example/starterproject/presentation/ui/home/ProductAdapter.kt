package com.example.starterproject.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.starterproject.databinding.ItemProductBinding
import com.example.starterproject.ui_model.ProductUiModel
import com.example.starterproject.utils.toIdrCurrency

class ProductAdapter(
    private val onMinusClick: (ProductUiModel) -> Unit,
    private val onPlusClick: (ProductUiModel) -> Unit
) : ListAdapter<ProductUiModel, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding,onMinusClick,onPlusClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onMinusClick: (ProductUiModel) -> Unit,
        private val onPlusClick: (ProductUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductUiModel) {
            binding.apply {
                tvTitle.text = data.name
                tvDesc.text = data.sortDescription
                tvPrice.text = data.price.toIdrCurrency()
                ivImg.load(
                    data.images
                )
                clMinusQty.setOnClickListener {
                    onMinusClick.invoke(data)
                }
                clPlusQty.setOnClickListener {
                    onPlusClick.invoke(data)
                }
                tvTotalQty.text = data.totalCart.toString()
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