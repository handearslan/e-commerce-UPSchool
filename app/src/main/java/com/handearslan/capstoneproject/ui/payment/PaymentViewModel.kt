package com.handearslan.capstoneproject.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.repository.AuthRepository
import com.handearslan.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _paymentState = MutableLiveData<PaymentState>()
    val paymentState: LiveData<PaymentState> get() = _paymentState


    fun clearCart() = viewModelScope.launch {
        val result = productRepository.clearCart(authRepository.getCurrentUserId())
        _paymentState.value = when (result) {
            is Resource.Success -> PaymentState.SuccessState
            else -> PaymentState.ShowSnackbar("Failed")
        }
    }

    fun checkInfo(
        addressTitle: String,
        address: String,
        city: String,
        district: String,
        cardOwner: String,
        cardNumber: String,
        month: String,
        year: String,
        cvc: String
    ) = viewModelScope.launch {
        _paymentState.value = PaymentState.Loading

        val errorMessage = when {
            cardOwner.isEmpty() -> "Please enter Card Owner"
            cardNumber.length != 16 -> "Card Number must be 16 digits"
            month.length != 2 -> "Please enter a valid Month"
            year.length != 4 -> "Please enter a valid Year"
            cvc.length != 3 -> "CVC must be 3 digits"
            city.isEmpty() -> "Please enter City"
            district.isEmpty() -> "Please enter District"
            address.isEmpty() -> "Please enter Address"
            addressTitle.isEmpty() -> "Please enter Address Title"
            else -> null
        }

        errorMessage?.let {
            _paymentState.value = PaymentState.ShowSnackbar(it)
        } ?: run {
            clearCart()
        }
    }
}

sealed interface PaymentState {
    object Loading : PaymentState
    object SuccessState : PaymentState
    data class ShowSnackbar(val errorMessage: String) : PaymentState
}