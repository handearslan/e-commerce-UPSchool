package com.handearslan.capstoneproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.handearslan.capstoneproject.MainApplication
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        with(binding) {

            val navController = navHostFragment.navController
            NavigationUI.setupWithNavController(bottomNav, navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.signInFragment, R.id.signUpFragment, R.id.detailFragment -> {
                        bottomNav.visibility = View.GONE
                    }

                    R.id.homeFragment, R.id.searchFragment, R.id.cartFragment, R.id.favoritesFragment -> {
                        bottomNav.visibility = View.VISIBLE
                    }

                    else -> {
                        bottomNav.visibility = View.GONE
                    }
                }
            }
        }
    }
}