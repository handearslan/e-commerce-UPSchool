package com.handearslan.capstoneproject.ui.favorites

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
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> get() = _favoritesState

    fun getFavorites() = viewModelScope.launch {
        _favoritesState.value = FavoritesState.Loading

        _favoritesState.value =
            when (val result = productRepository.getFavorites(authRepository.getCurrentUserId())) {
                is Resource.Success -> FavoritesState.SuccessState(result.data)
                is Resource.Fail -> FavoritesState.EmptyScreen
                is Resource.Error -> FavoritesState.ShowSnackbar(result.errorMessage)
            }
    }

    fun deleteFromFavorites(product: ProductUI) {
        viewModelScope.launch {
            productRepository.deleteFromFavorites(product, authRepository.getCurrentUserId())
            getFavorites()
        }
    }

    fun clearAllFromFavorites() {
        viewModelScope.launch {
            productRepository.clearAllFromFavorites(authRepository.getCurrentUserId())
            _favoritesState.value = FavoritesState.EmptyScreen
        }
    }
}

sealed interface FavoritesState {
    object Loading : FavoritesState
    data class SuccessState(val products: List<ProductUI>) : FavoritesState
    object EmptyScreen : FavoritesState
    data class ShowSnackbar(val errorMessage: String) : FavoritesState
}