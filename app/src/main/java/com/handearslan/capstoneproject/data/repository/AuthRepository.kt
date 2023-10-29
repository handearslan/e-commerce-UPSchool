package com.handearslan.capstoneproject.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.common.Resource
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signIn(email: String, password: String): Resource<Boolean> {
        try {
            val authSignIn = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (authSignIn.user != null) {
                return Resource.Success(true)
            } else {
                return Resource.Success(false)
            }
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Login failed.")
        }
    }

    suspend fun signUp(email: String, password: String): Resource<Boolean> {
        try {
            val authSignUp = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (authSignUp.user != null) {
                return Resource.Success(false)
            } else {
                return Resource.Success(true)
            }

        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Registration failed.")
        }
    }
}