package com.handearslan.capstoneproject.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _signInResult = MutableLiveData<Resource<Boolean>>()
    val signInResult: LiveData<Resource<Boolean>> get() = _signInResult

    fun signIn(email: String, password: String) {

        authRepository.signIn(email, password).let { result ->
            if (result is Resource.Success) {
                _signInResult.value = Resource.Success(result.data)
            } else if (result is Resource.Error) {
                _signInResult.value = Resource.Error(result.errorMessage)
            }
        }
    }

    fun isEmailValid(email: String) = authRepository.isEmailValid(email)

    fun isPasswordValid(password: String) = authRepository.isPasswordValid(password)
}