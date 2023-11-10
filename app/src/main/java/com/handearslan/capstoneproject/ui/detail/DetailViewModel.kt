package com.handearslan.capstoneproject.ui.detail

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
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    private var product: ProductUI? = null

    fun getProductDetail(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result =
            productRepository.getProductDetail(id, authRepository.getCurrentUserId())) {
            is Resource.Success -> {
                product = result.data
                DetailState.SuccessState(result.data)
            }

            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowSnackbar(result.errorMessage)
        }
    }

    fun addToCart(id: Int) = viewModelScope.launch {
        _detailState.value =
            when (val result = productRepository.addToCart(id, authRepository.getCurrentUserId())) {
                is Resource.Success -> DetailState.ShowSnackbar(result.data.message.orEmpty())
                is Resource.Fail -> DetailState.ShowSnackbar(result.failMessage)
                is Resource.Error -> DetailState.ShowSnackbar(result.errorMessage)
            }
    }

    fun setFavoriteState() = viewModelScope.launch {
        product?.let { product ->
            if (product.isFav) {
                productRepository.deleteFromFavorites(product, authRepository.getCurrentUserId())
            } else {
                productRepository.addToFavorites(product, authRepository.getCurrentUserId())
            }
            getProductDetail(product.id)
        }
    }
}

sealed interface DetailState {
    object Loading : DetailState
    data class SuccessState(val product: ProductUI) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowSnackbar(val errorMessage: String) : DetailState
}