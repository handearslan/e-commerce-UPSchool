package com.handearslan.capstoneproject.ui.home

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
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val productAdapter = HomeProductAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    private val saleProductAdapter = HomeSaleProductAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        with(binding) {
            rvProducts.adapter = productAdapter

            rvSaleProduct.adapter = saleProductAdapter

            ibLogOut.setOnClickListener {
                findNavController().navigate(R.id.homeToSignIn)
                viewModel.logOut()
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    pbProducts.visible()
                }

                is HomeState.SuccessState -> {
                    pbProducts.gone()
                    productAdapter.submitList(state.products)
                    saleProductAdapter.submitList(state.saleProducts)
                }

                is HomeState.EmptyScreen -> {
                    pbProducts.gone()
                    tvProducts.gone()
                    tvSale.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is HomeState.ShowSnackbar -> {
                    pbProducts.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }
    private fun onFavClick(product: ProductUI) {
        viewModel.setFavoriteState(product)
    }
}