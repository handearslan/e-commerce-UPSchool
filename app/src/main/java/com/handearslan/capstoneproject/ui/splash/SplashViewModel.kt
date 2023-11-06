package com.handearslan.capstoneproject.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.handearslan.capstoneproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

        private var _splashState = MutableLiveData<SplashState>()
        val splashState: LiveData<SplashState> get() = _splashState

       fun checkUserLoggedIn() {
           _splashState.value = if (authRepository.isUserLoggedIn()) {
               SplashState.UserLoggedIn
           } else {
               SplashState.UserNotLoggedIn
           }
       }
}

sealed interface SplashState {
    object UserLoggedIn : SplashState
    object UserNotLoggedIn : SplashState
}