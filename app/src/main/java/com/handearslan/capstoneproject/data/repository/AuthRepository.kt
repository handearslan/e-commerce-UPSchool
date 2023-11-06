package com.handearslan.capstoneproject.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.handearslan.capstoneproject.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    fun logOut() = firebaseAuth.signOut()

    fun getCurrentUserId() = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun signIn(email: String, password: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val authSignIn = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (authSignIn.user != null) {
                    Resource.Success(true)
                } else {
                    Resource.Success(false)
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun signUp(email: String, password: String): Resource<Boolean>  =
        withContext(Dispatchers.IO) {
            try {
                val authSignUp = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (authSignUp.user != null) {
                    Resource.Success(false)
                } else {
                    Resource.Success(true)
                }

            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
}