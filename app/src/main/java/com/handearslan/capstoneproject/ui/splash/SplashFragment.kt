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

    private val splash_delay: Long = 3000 // Millisaniye cinsinden gösterilecek süre (3 saniye)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Belirtilen süre sonra ana ekrana yönlendirme yapmak için Handler kullanalım
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, splash_delay)

        return view
    }

    // Bir sonraki ekranı açmak için bu fonksiyonu kullanabilirsiniz
    private fun navigateToNextScreen() {
        val action = SplashFragmentDirections.splashToSignIn()
        findNavController().navigate(action)
    }
}