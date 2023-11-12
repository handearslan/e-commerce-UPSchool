package com.handearslan.capstoneproject.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.gone
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.common.visible
import com.handearslan.capstoneproject.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter = CartAdapter(
        onProductClick = ::onProductClick,
        onDeleteClick = ::onDeleteClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartProducts()

        with(binding) {
            rvCart.adapter = cartAdapter

            tvClear.setOnClickListener {
                viewModel.clearCart()

            }
            btnPayment.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.cartToPayment())
            }

        }
        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                CartState.Loading -> pbCart.visible()

                is CartState.SuccessState -> {
                    pbCart.gone()
                    cartAdapter.submitList(state.product)

                    tvTotalAmount.visible()
                    tvTotalPrice.visible()
                    viewModel.calculateTotalPrice(state.product)
                }

                is CartState.EmptyScreen -> {
                    pbCart.gone()
                    rvCart.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvTotalAmount.gone()
                    tvTotalPrice.gone()
                    cardView.gone()
                    btnPayment.gone()
                    tvEmpty.text = state.failMessage
                }

                is CartState.ShowSnackbar -> {
                    pbCart.gone()
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()
                }

                is CartState.DeleteProduct -> {
                    pbCart.gone()
                    Snackbar.make(requireView(), state.message, 500).show()
                    viewModel.getCartProducts()
                }

                is CartState.ClearCart -> {
                    pbCart.gone()
                    Snackbar.make(requireView(), state.message, 500).show()
                    viewModel.getCartProducts()
                }
            }
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            tvTotalAmount.text = "${totalPrice.toString()} ₺"
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.cartToDetail(id))
    }

    private fun onDeleteClick(id: Int) {
        viewModel.onDeleteClick(id)
    }
}