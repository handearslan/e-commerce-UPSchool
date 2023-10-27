package com.handearslan.capstoneproject.data.repository

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.common.Resource
import javax.inject.Inject


class AuthRepository (
    private val firebaseAuth: FirebaseAuth
) {

    fun signIn(email: String, password: String): Resource<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Login failed.")
        }
    }

    fun signUp(email: String, password: String): Resource<Boolean> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
            Resource.Success(true)

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Registration failed.")
        }
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }
}