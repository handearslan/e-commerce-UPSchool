package com.handearslan.capstoneproject.ui.auth.signUp

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

                viewModel.checkInfo(email, password)
            }

            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.signUpToSıgnIn)
            }
        }
        observeSignUp()
    }

    private fun observeSignUp() {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignUpState.Loading -> binding.pbSignUp.visible()

                SignUpState.SuccessState -> {
                    binding.pbSignUp.gone()
                    findNavController().navigate(R.id.signUpToMainGraph)
                }

                is SignUpState.ShowSnackbar -> {
                    binding.pbSignUp.gone()
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}