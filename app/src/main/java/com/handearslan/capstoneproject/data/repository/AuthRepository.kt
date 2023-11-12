package com.handearslan.capstoneproject.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.handearslan.capstoneproject.common.Resource
import com.handearslan.capstoneproject.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

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

    suspend fun signUp(email: String, password: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val authSignUp =
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (authSignUp.user != null) {
                    saveUserToFirestore(authSignUp.user!!.uid, email)
                    Resource.Success(false)
                } else {
                    Resource.Success(true)
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getUser(userId: String): Resource<User> = withContext(Dispatchers.IO) {
        try {
            val documentSnapshot =
                firebaseFirestore.collection("users").document(userId).get().await()

            if (documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                user?.let { Resource.Success(it.copy(userId = documentSnapshot.id)) }
                    ?: Resource.Error("User is null")
            } else {
                Resource.Error("User not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun saveUserToFirestore(userId: String, email: String) {
        val userMap = hashMapOf(
            "email" to email
        )

        firebaseFirestore.collection("users").document(userId).set(userMap).await()
    }
}