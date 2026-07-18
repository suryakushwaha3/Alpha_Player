package com.example.alphaplayer.Auth

import com.example.alphaplayer.ViewModel.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirebaseFirestore.getInstance()


    // ==========================
    // Create Account
    // ==========================

    suspend fun createAccount(
        fullName: String,
        email: String,
        password: String
    ): Result<Boolean> {

        return try {

            val result = auth.createUserWithEmailAndPassword(
                email,
                password
            ).await()

            val uid = result.user?.uid ?: ""

            val user = UserModel(
                uid = uid,
                fullName = fullName,
                email = email
            )

            firestore.collection("users")
                .document(uid)
                .set(user)
                .await()

            Result.success(true)

        } catch (e: FirebaseAuthWeakPasswordException) {

            Result.failure(
                Exception("Password must be at least 6 characters.")
            )

        } catch (e: FirebaseAuthUserCollisionException) {

            Result.failure(
                Exception("This email is already registered.")
            )

        } catch (e: FirebaseAuthInvalidCredentialsException) {

            Result.failure(
                Exception("Please enter a valid email address.")
            )

        } catch (e: Exception) {

            Result.failure(
                Exception("Account creation failed.")
            )

        }

    }


    // ==========================
    // Login
    // ==========================

    suspend fun login(
        email: String,
        password: String
    ): Result<Boolean> {

        return try {

            auth.signInWithEmailAndPassword(
                email,
                password
            ).await()

            Result.success(true)

        } catch (e: FirebaseAuthInvalidUserException) {

            Result.failure(
                Exception("No account found with this email.")
            )

        } catch (e: FirebaseAuthInvalidCredentialsException) {

            Result.failure(
                Exception("Invalid email or password.")
            )

        } catch (e: Exception) {

            Result.failure(
                Exception("Login failed. Please try again.")
            )

        }

    }


    // ==========================
    // Forgot Password
    // ==========================

    suspend fun resetPassword(
        email: String
    ): Result<Boolean> {

        return try {

            auth.sendPasswordResetEmail(email)
                .await()

            Result.success(true)

        } catch (e: FirebaseAuthInvalidUserException) {

            Result.failure(
                Exception("No account found with this email.")
            )

        } catch (e: Exception) {

            Result.failure(
                Exception("Unable to send reset link.")
            )

        }

    }


    // ==========================
    // Check Email Exists
    // ==========================

    suspend fun checkEmailExists(
        email: String
    ): Boolean {

        return try {

            val result = firestore
                .collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()

            !result.isEmpty

        } catch (e: Exception) {

            false

        }

    }


    // ==========================
    // Logout
    // ==========================

    fun logout() {

        auth.signOut()

    }


    // ==========================
    // Check Current User
    // ==========================

    fun isUserLoggedIn(): Boolean {

        return auth.currentUser != null

    }

}