package com.handearslan.capstoneproject.ui.profile

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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState> get() = _profileState

    fun getUser() = viewModelScope.launch {
        _profileState.value = ProfileState.Loading

        val result = authRepository.getUser(authRepository.getCurrentUserId())

        _profileState.value = when (result) {
            is Resource.Success -> {
                val user = result.data
                ProfileState.SuccessState(user?.email.orEmpty())
            }

            is Resource.Fail -> ProfileState.EmptyScreen(result.failMessage)
            is Resource.Error -> ProfileState.ShowSnackbar("Failed to retrieve user data")
        }
    }

    fun logOut() = viewModelScope.launch {
        authRepository.logOut()

    }
}

sealed interface ProfileState {
    object Loading : ProfileState
    data class SuccessState(val email: String) : ProfileState
    data class EmptyScreen(val failMessage: String) : ProfileState
    data class ShowSnackbar(val errorMessage: String) : ProfileState
}