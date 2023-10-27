package com.handearslan.capstoneproject.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.handearslan.capstoneproject.data.model.response.ProductListUI
import com.handearslan.capstoneproject.databinding.ItemSearchBinding

class SearchAdapter(

    private val onProductClick: (Int) -> Unit
) : ListAdapter<ProductListUI, SearchAdapter.ProductSearchViewHolder>(ProductSearchDiffUtilCallBack()) {

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

        fun bind(product: ProductListUI) {
            with(binding) {
                tvSearchTitle.text = product.title
                tvSearchPrice.text = "${product.price} ₺"

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductSearchDiffUtilCallBack : DiffUtil.ItemCallback<ProductListUI>() {
        override fun areItemsTheSame(oldItem: ProductListUI, newItem: ProductListUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListUI, newItem: ProductListUI): Boolean {
            return oldItem == newItem
        }
    }
}