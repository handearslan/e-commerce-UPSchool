package com.handearslan.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.data.repository.AuthRepository
import com.handearslan.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    private var _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun getCartProducts() = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result =
            productRepository.getCartProducts(authRepository.getCurrentUserId())) {
            is Resource.Success -> CartState.SuccessState(result.data)
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowSnackbar(result.errorMessage)
        }
    }

    fun onDeleteClick(id: Int) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result =
            productRepository.onDeleteClick(id, authRepository.getCurrentUserId())) {
            is Resource.Success -> CartState.DeleteProduct(result.data.message.orEmpty())
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowSnackbar(result.errorMessage)
        }
    }

    fun clearCart() = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value =
            when (val result = productRepository.clearCart(authRepository.getCurrentUserId())) {
                is Resource.Success -> CartState.ClearCart(result.data.message.orEmpty())
                is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
                is Resource.Error -> CartState.ShowSnackbar(result.errorMessage)
            }
    }

    fun calculateTotalPrice(products: List<ProductUI>) {
        val totalPrice = products.map { product ->
            val price = if (product.salePrice > 0.0) {
                product.salePrice
            } else {
                product.price
            }
            price
        }.sum()
        _totalPrice.value = totalPrice
    }
}

sealed interface CartState {
    object Loading : CartState
    data class SuccessState(val product: List<ProductUI>) : CartState
    data class DeleteProduct(val message: String) : CartState
    data class ClearCart(val message: String) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowSnackbar(val errorMessage: String) : CartState
}