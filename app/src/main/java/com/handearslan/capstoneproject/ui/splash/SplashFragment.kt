package com.handearslan.capstoneproject.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.handearslan.capstoneproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.checkUserLoggedIn()
        }, DELAY)

        observeData()
    }

    private fun observeData() {
        viewModel.splashState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SplashState.UserLoggedIn -> {
                    findNavController().navigate(R.id.splashToMainGraph)
                }

                is SplashState.UserNotLoggedIn -> {
                    findNavController().navigate(R.id.splashToSignIn)
                }
            }
        }
    }

    companion object {
        private const val DELAY = 3000L
    }
}