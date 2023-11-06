package com.handearslan.capstoneproject.ui.auth.signIn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.gone
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.common.visible
import com.handearslan.capstoneproject.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.checkInfo(email,password)
            }
            tvSignInUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSıgnUp)
            }
        }
        observeSignIn()
    }

    private fun observeSignIn() = with(binding){
        viewModel.signInState.observe(viewLifecycleOwner) {state->
            when(state){
                SignInState.Loading -> pbSignIn.visible()

                SignInState.SuccessState -> {
                    pbSignIn.gone()
                    findNavController().navigate(R.id.signInToMainGraph)
                }

                is SignInState.ShowSnackbar -> {
                    pbSignIn.gone()
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}