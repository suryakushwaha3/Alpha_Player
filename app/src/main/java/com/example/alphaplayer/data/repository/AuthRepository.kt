package com.example.alphaplayer.data.repository

import com.example.alphaplayer.data.model.UserModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun createAccount(
        fullName: String, email: String, password: String
    ): Result<Boolean> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: ""
            val user = UserModel(
                uid = uid, fullName = fullName, email = email
            )

            firestore.collection("users").document(uid).set(user).await()

            Result.success(true)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Password must be at least 6 characters."))
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("This email is already registered."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Please enter a valid email address."))
        } catch (e: Exception) {
            Result.failure(Exception("Account creation failed."))
        }
    }

    suspend fun login(
        email: String, password: String
    ): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No account found with this email."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Invalid email or password."))
        } catch (e: Exception) {
            Result.failure(Exception("Login failed. Please try again."))
        }
    }

    // Fixed & Complete Google Sign-In logic
    suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            val authResult = auth.signInWithCredential(credential).await()

            val firebaseUser = authResult.user ?: return Result.failure(Exception("User is null"))

            // Check if user already exists in Firestore database
            val userDocRef = firestore.collection("users").document(firebaseUser.uid)
            val userSnapshot = userDocRef.get().await()

            // If user is signing in for the first time, create their profile in Firestore
            if (!userSnapshot.exists()) {
                val newUser = UserModel(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Google User",
                    email = firebaseUser.email ?: "",
                    profileImage = firebaseUser.photoUrl?.toString() ?: ""
                )
                userDocRef.set(newUser).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Result<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(true)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No account found with this email."))
        } catch (e: Exception) {
            Result.failure(Exception("Unable to send reset link."))
        }
    }

    suspend fun checkEmailExists(email: String): Boolean {
        return try {
            val result = firestore.collection("users").whereEqualTo("email", email).get().await()
            !result.isEmpty
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCurrentUser(): UserModel? {
        return try {
            val uid = auth.currentUser?.uid ?: return null
            firestore.collection("users").document(uid).get().await()
                .toObject(UserModel::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateProfile(
        fullName: String, phone: String, profileImage: String
    ): Result<Boolean> {
        return try {
            val uid =
                auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))

            firestore.collection("users").document(uid).update(
                mapOf(
                    "fullName" to fullName,
                    "phone" to phone,
                    "profileImage" to profileImage
                )
            ).await()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Profile update failed"))
        }
    }

    // Change Password
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Boolean> {
        return try {
            val user = auth.currentUser ?: return Result.failure(Exception("User not logged in"))
            val email = user.email ?: return Result.failure(Exception("Email not found"))

            val credential = EmailAuthProvider.getCredential(
                email,
                currentPassword
            )

            user.reauthenticate(credential).await()
            user.updatePassword(newPassword).await()

            Result.success(true)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Current password is incorrect."))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Password change failed."))
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}