package com.handearslan.capstoneproject.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by viewModels<SignInViewModel>()
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.signInToHome)
        }

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (viewModel.isEmailValid(email) && viewModel.isPasswordValid(password)) {
                    viewModel.signIn(email, password)
                    observeSignIn()
                } else {
                    Snackbar.make(requireView(), "Invalid email or password", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            tvSignInUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSıgnUp)
            }
        }
    }

    private fun observeSignIn() {
        viewModel.signInResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.signInToHome)
                }

                is Resource.Fail -> {
                    Snackbar.make(requireView(), result.failMessage, Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    Snackbar.make(requireView(), result.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}