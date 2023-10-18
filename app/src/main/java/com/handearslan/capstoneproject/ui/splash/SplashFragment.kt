package com.handearslan.capstoneproject.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.handearslan.capstoneproject.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val splash_screen_delay: Long = 3000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            openNextScreen()
        }, splash_screen_delay)

        return view
    }

    private fun openNextScreen() {
        val action = SplashFragmentDirections.splashToSignIn()
        findNavController().navigate(action)
    }
}