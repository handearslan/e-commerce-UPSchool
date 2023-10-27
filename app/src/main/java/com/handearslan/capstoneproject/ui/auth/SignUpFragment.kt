package com.handearslan.capstoneproject.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmailUp.text.toString()
                val password = etPasswordUp.text.toString()

                if (viewModel.isEmailValid(email) && viewModel.isPasswordValid(password)) {
                    viewModel.signUp(email, password)
                    observeSignUp()
                } else {
                    Snackbar.make(requireView(), "Invalid email or password", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.signUpToSıgnIn)
            }
        }
    }

    private fun observeSignUp() {
        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data) {
                        findNavController().navigate(R.id.signUpToSıgnIn)

                        Snackbar.make(requireView(), "Authentication successful", Snackbar.LENGTH_SHORT).show()
                    }
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