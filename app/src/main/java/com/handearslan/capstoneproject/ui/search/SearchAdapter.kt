package com.handearslan.capstoneproject.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.handearslan.capstoneproject.data.model.Product
import com.handearslan.capstoneproject.databinding.ItemSearchBinding

class SearchAdapter(

    private val onProductClick: (Int) -> Unit
) : ListAdapter<Product, SearchAdapter.ProductSearchViewHolder>(ProductSearchDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSearchViewHolder {
        return ProductSearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: ProductSearchViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductSearchViewHolder(
        private val binding: ItemSearchBinding,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                tvSearchTitle.text = product.title
                tvSearchPrice.text = "${product.price} ₺"

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductSearchDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}