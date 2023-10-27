package com.handearslan.capstoneproject.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.handearslan.capstoneproject.data.model.response.ProductListUI
import com.handearslan.capstoneproject.databinding.ItemProductBinding

class HomeSaleProductAdapter(
    private val onProductClick: (Int) -> Unit
) : ListAdapter<ProductListUI, HomeSaleProductAdapter.SaleProductViewHolder>(SaleProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductViewHolder {
        return SaleProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: SaleProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SaleProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductListUI) {
            with(binding) {
                tvTitle.text = product.title
                tvPrice.text = "${product.price.toString()} ₺"
                tvSalePrice.text = "${product.salePrice.toString()} ₺"

                if (product.saleState == true) {
                    tvSalePrice.visibility = View.VISIBLE
                    tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvSalePrice.visibility = View.GONE
                    tvPrice.paintFlags = 0
                }

                Glide.with(ivProduct).load(product.imageOne).into(ivProduct)

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class SaleProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductListUI>() {
        override fun areItemsTheSame(oldItem: ProductListUI, newItem: ProductListUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListUI, newItem: ProductListUI): Boolean {
            return oldItem == newItem
        }
    }
}
