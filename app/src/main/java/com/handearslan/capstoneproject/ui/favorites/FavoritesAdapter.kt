package com.handearslan.capstoneproject.ui.favorites

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.handearslan.capstoneproject.data.model.Product
import com.handearslan.capstoneproject.databinding.ItemFavBinding


class FavoritesAdapter(
    private val onProductClick: (Int) -> Unit
): ListAdapter<Product, FavoritesAdapter.FavoritesViewHolder>(FavProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) =
        holder.bind(getItem(position))

    class FavoritesViewHolder(
        private val binding: ItemFavBinding,
        private val onProductClick: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                Glide.with(ivFav.context).load(product.imageOne).into(ivFav)
                tvTitleFav.text = product.title
                tvCategoriesFav.text = product.category
                if (product.saleState == true) {
                    tvPriceFav.text = product.price.toString()
                    tvPriceFav.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvSalePriceFav.text = "${product.salePrice.toString()} TL"
                    tvSalePriceFav.visibility = View.VISIBLE

                } else {
                    tvPriceFav.text = "${product.price.toString()} TL"
                    tvPriceFav.paintFlags = 0
                    tvSalePriceFav.visibility = View.GONE
                }

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class FavProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }

}