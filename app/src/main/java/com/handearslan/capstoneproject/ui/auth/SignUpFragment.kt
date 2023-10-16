package com.handearslan.capstoneproject.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        with(binding) {

            btnSignUp.setOnClickListener {
                val email = etEmailUp.text.toString()
                val password = etPasswordUp.text.toString()

            if (checkFields(email, password)) {
                    signUp(email, password)
                }
            }

            tvHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.signUpToSıgnIn)
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                binding.tilEmailUp.error = "E-mail is not valid!"
                false
            }

            password.isEmpty() -> {
                binding.tilEmailUp.isErrorEnabled = false
                binding.tilPasswordUp.error = "Password is empty!"
                false
            }

            password.length < 6 -> {
                binding.tilEmailUp.isErrorEnabled = false
                binding.tilPasswordUp.error = "Password length should be more than six character!"
                false
            }

            else -> true
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.signUpToSıgnIn)
                } else {
                    Snackbar.make(requireView(), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }
}

