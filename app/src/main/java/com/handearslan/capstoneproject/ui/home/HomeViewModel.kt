package com.handearslan.capstoneproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.model.response.ProductUI
import com.handearslan.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    fun getProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when (val result = productRepository.getProducts()) {
            is Resource.Success -> HomeState.SuccessState(
                result.data,
                result.data.filter { it.saleState == true })

            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowSnackbar(result.errorMessage)
        }
    }

    fun logOut() = viewModelScope.launch {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        Log.e("HomeViewModel", "User Logged Out")
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product)
        } else {
            productRepository.addToFavorites(product)
        }
        getProducts()
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class SuccessState(
        val products: List<ProductUI>,
        val saleProducts: List<ProductUI>
    ) : HomeState

    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowSnackbar(val errorMessage: String) : HomeState
}


