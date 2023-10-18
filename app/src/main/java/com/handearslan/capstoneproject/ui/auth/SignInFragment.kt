package com.handearslan.capstoneproject.ui.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.R
import com.handearslan.capstoneproject.common.viewBinding
import com.handearslan.capstoneproject.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private lateinit var sharedPref: SharedPreferences

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //amaç: login yapıldıysa, uygulama kapanıp yeniden açıldığında login işlemleri istenmemesini sağlamak
        sharedPref = requireActivity().getSharedPreferences(
            "AppSettings",
            Context.MODE_PRIVATE
        )

        val islogin = sharedPref.getBoolean("isLogin", false)

        if (islogin) {
            findNavController().navigate(R.id.signInToHome)
        }

        auth = FirebaseAuth.getInstance()

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (checkFields(email, password)) {
                    signIn(email, password)
                }
            }

            tvSignInUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSıgnUp)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    sharedPref.edit().putBoolean("isLogin", true).apply()
                    findNavController().navigate(R.id.signInToHome)
                } else {
                    Snackbar.make(requireView(), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                binding.tilEmail.error = "E-mail is not valid!"
                false
            }

            password.isEmpty() -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password is empty!"
                false
            }

            password.length < 6 -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password length should be more than six character!"
                false
            }

            else -> true
        }
    }
}




