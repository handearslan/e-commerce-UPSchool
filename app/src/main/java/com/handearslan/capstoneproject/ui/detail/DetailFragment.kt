package com.handearslan.capstoneproject.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.gone
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.common.visible
import com.handearslan.capstoneproject.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)

        with(binding) {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnAddCart.setOnClickListener {
                viewModel.addToCart(args.id)
            }

            ivEmptyFav.setOnClickListener {
                viewModel.setFavoriteState()
            }
        }
        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> pbDetail.visible()

                is DetailState.SuccessState -> {
                    pbDetail.gone()

                    ivEmptyFav.setBackgroundResource(
                        if (state.product.isFav) R.drawable.ic_fav
                        else R.drawable.ic_empty_fav
                    )

                    Glide.with(ivProduct).load(state.product.imageOne).into(ivProduct)
                    tvTitle.text = state.product.title
                    tvPrice.text = "${state.product.price} ₺"
                    tvDescription.text = state.product.description
                    tvCategory.text = state.product.category
                    tvQuantity.text = state.product.count.toString()

                    state.product.rate.let { rate ->
                        ratingBar.rating = rate.toFloat()
                    }

                    if (state.product.saleState) {
                        tvSalePrice.visibility = View.VISIBLE
                        tvSalePrice.text = "${state.product.salePrice} ₺"
                        tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        tvSalePrice.visibility = View.GONE
                        tvPrice.paintFlags = 0
                    }
                }

                is DetailState.EmptyScreen -> {
                    pbDetail.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is DetailState.ShowSnackbar -> {
                    pbDetail.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}