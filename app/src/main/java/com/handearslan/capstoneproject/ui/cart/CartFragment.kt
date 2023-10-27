package com.handearslan.capstoneproject.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private lateinit var auth: FirebaseAuth

    private val cartAdapter = CartAdapter(
        onProductClick = ::onProductClick,
        onDeleteClick = ::onDeleteClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val userId = auth.currentUser?.uid

        if (userId != null) {
            viewModel.getCartProducts(userId)
        }

        with(binding) {
            rvCart.adapter = cartAdapter

            btnClear.setOnClickListener {
                viewModel.clearCart(auth.currentUser?.uid.toString())
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
                CartViewModel.CartState.Loading -> pbCart.visible()

                is CartViewModel.CartState.SuccessState -> {
                    pbCart.gone()
                    cartAdapter.submitList(state.product)

                }

                is CartViewModel.CartState.EmptyScreen -> {
                    pbCart.gone()
                    rvCart.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage

                }

                is CartViewModel.CartState.ShowPopUp -> {
                    pbCart.gone()
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()

                }

                is CartViewModel.CartState.DeleteProduct -> {
                    pbCart.gone()
                    Snackbar.make(requireView(), state.message, Snackbar.LENGTH_SHORT).show()
                    viewModel.getCartProducts(auth.currentUser?.uid.toString())
                }

                is CartViewModel.CartState.ClearCart -> {
                    pbCart.gone()
                    Snackbar.make(requireView(), state.message, Snackbar.LENGTH_SHORT).show()
                    viewModel.getCartProducts(auth.currentUser?.uid.toString())
                }
            }
        }

    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.cartToDetail(id))
    }

    private fun onDeleteClick(id: Int) {
        viewModel.onDeleteClick(id)
    }
}

