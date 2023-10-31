package com.handearslan.capstoneproject.ui.success

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.model.request.ClearCartRequest
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.data.repository.ProductRepository
import com.handearslan.capstoneproject.ui.cart.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _successState = MutableLiveData<SuccessState>()
    val successState: LiveData<SuccessState> get() = _successState

    fun clearCart(userId: String?) = viewModelScope.launch {

        productRepository.clearCart(ClearCartRequest(userId = userId))
    }
}

sealed interface SuccessState {
    data class Success(val message: String) : SuccessState
}
