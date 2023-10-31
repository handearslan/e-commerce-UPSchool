package com.handearslan.capstoneproject.ui.favorites

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.databinding.ItemFavBinding

class FavoritesAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (ProductUI) -> Unit
) : ListAdapter<ProductUI, FavoritesAdapter.FavoritesViewHolder>(FavProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) =
        holder.bind(getItem(position))

    class FavoritesViewHolder(
        private val binding: ItemFavBinding,
        private val onProductClick: (Int) -> Unit, private val onDeleteClick: (ProductUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
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
                    onProductClick(product.id)
                }

                ivDeleteFav.setOnClickListener {
                    onDeleteClick(product)
                }
            }
        }
    }

    class FavProductDiffCallback : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean =
            oldItem == newItem
    }
}