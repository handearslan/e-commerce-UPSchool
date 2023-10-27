package com.handearslan.capstoneproject.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _signUpResult = MutableLiveData<Resource<Boolean>>()
    val signUpResult: LiveData<Resource<Boolean>> get() = _signUpResult

    fun signUp(email: String, password: String) {
        authRepository.signUp(email, password).let { result ->
            if (result is Resource.Success) {
                _signUpResult.value = Resource.Success(result.data)
            } else if (result is Resource.Error) {
                _signUpResult.value = Resource.Error(result.errorMessage)
            }
        }
    }

    fun isEmailValid(email: String) = authRepository.isEmailValid(email)

    fun isPasswordValid(password: String) = authRepository.isPasswordValid(password)
}