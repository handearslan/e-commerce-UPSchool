package com.handearslan.capstoneproject.ui.auth.signIn

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> get() = _signInState

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _signInState.value = SignInState.Loading

        _signInState.value = when (val result = authRepository.signIn(email, password)) {
            is Resource.Success -> SignInState.SuccessState
            is Resource.Error -> SignInState.ShowSnackbar(result.errorMessage)
            is Resource.Fail -> SignInState.ShowSnackbar(result.failMessage)
        }
    }

    fun checkInfo(email: String, password: String) {
        when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _signInState.value = SignInState.ShowSnackbar("Invalid email")
            }

            password.isEmpty() || password.length <= 5 -> {
                _signInState.value = SignInState.ShowSnackbar("Invalid password")
            }

            else -> {
                signIn(email, password)
            }
        }
    }
}

sealed interface SignInState {
    object Loading : SignInState
    object SuccessState : SignInState
    data class ShowSnackbar(val errorMessage: String) : SignInState
}
