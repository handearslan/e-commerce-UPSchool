package com.handearslan.capstoneproject.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.model.request.ClearCartRequest
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

        if ((addressTitle.isEmpty() || address.isEmpty() || city.isEmpty() || district.isEmpty() ||
                    cardOwner.isEmpty() || cardNumber.length != 16 || cvc.length != 3 ||
                    year.length != 4 || month.length != 2)
        ) {
            _paymentState.value = PaymentState.ShowSnackbar("Please fill all the fields")

        } else {
            _paymentState.value= PaymentState.SuccessState
        }
    }
}

sealed interface PaymentState {
    object Loading : PaymentState
    object SuccessState: PaymentState
    data class ShowSnackbar(val errorMessage: String) : PaymentState
}