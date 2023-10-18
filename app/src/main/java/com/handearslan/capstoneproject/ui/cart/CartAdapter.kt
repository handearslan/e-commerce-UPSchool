package com.handearslan.capstoneproject.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.handearslan.capstoneproject.data.model.DeleteFromCartItem
import com.handearslan.capstoneproject.data.model.Product
import com.handearslan.capstoneproject.databinding.ItemCartBinding
import kotlin.reflect.KFunction1

class CartAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : ListAdapter<Product, CartAdapter.CartViewHolder>(
    ProductDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick, onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val onProductClick: (Int) -> Unit,
        private val onDeleteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                Glide.with(ivCart.context).load(product.imageOne).into(ivCart)
                tvTitleCart.text = product.title
                tvCategories.text = product.category
                if (product.saleState == true) {
                    tvPriceCart.text = product.price.toString()
                    tvPriceCart.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvSalePriceCart.text = "${product.salePrice.toString()} TL"
                    tvSalePriceCart.visibility = View.VISIBLE

                } else {
                    tvPriceCart.text = "${product.price.toString()} TL"
                    tvPriceCart.paintFlags = 0
                    tvSalePriceCart.visibility = View.GONE
                }

                ivDelete.setOnClickListener {
                    onDeleteClick(product.id ?: 1)
                }

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}