package com.handearslan.capstoneproject.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.repository.AuthRepository
import com.handearslan.capstoneproject.ui.auth.signIn.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _userState = MutableLiveData<UserState>()
    val userState: LiveData<UserState> get() = _userState

    fun getUser() = viewModelScope.launch {
        _userState.value = UserState.Loading

        val result = authRepository.getUser(authRepository.getCurrentUserId())

        _userState.value = when (result) {
            is Resource.Success -> {
                val user = result.data
                UserState.SuccessState(user?.email.orEmpty())
            }
            is Resource.Fail -> UserState.EmptyScreen(result.failMessage)
            is Resource.Error -> UserState.ShowSnackbar("Failed to retrieve user data")
        }
    }

    fun logOut() = viewModelScope.launch {
        authRepository.logOut()

    }
}

sealed interface UserState {
    object Loading : UserState
    data class SuccessState(val email: String) : UserState
    data class EmptyScreen(val failMessage: String) : UserState
    data class ShowSnackbar(val errorMessage: String) : UserState
}