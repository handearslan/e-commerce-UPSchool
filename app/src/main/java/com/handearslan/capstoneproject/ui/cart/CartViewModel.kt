package com.handearslan.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.model.request.ClearCartRequest
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    fun getCartProducts(userId: String) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.getCartProducts(userId)) {
            is Resource.Success ->
                if (result.data.isEmpty()) {
                    CartState.EmptyScreen("No Products")
                } else {
                    CartState.SuccessState(result.data)
                }

            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowSnackbar(result.errorMessage)
        }
    }

    fun onDeleteClick(id: Int) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.onDeleteClick(id)) {
            is Resource.Success -> CartState.DeleteProduct("Product deleted")
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowSnackbar(result.errorMessage)
        }
    }


    fun clearCart(userId: String?) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value =
            when (val result = productRepository.clearCart(ClearCartRequest(userId = userId))) {
                is Resource.Success -> CartState.ClearCart("Cart cleared")
                is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
                is Resource.Error -> CartState.ShowSnackbar(result.errorMessage)
            }
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

