package com.example.alphaplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alphaplayer.data.model.UserModel
import com.example.alphaplayer.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    fun login(
        email: String, password: String, result: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            repository.login(email, password)
                .onSuccess { result(true, "Login Successful") }
                .onFailure { result(false, it.message ?: "Login Failed") }
        }
    }

    // Standardized Google Sign-In via Repository (Supports Firestore Sync)
    fun loginWithGoogle(
        idToken: String, result: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            repository.signInWithGoogle(idToken)
                .onSuccess { result(true, "Google Sign-In successful!") }
                .onFailure { result(false, it.message ?: "Google Sign-In failed.") }
        }
    }

    fun createAccount(
        fullName: String, email: String, password: String, result: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            repository.createAccount(fullName, email, password)
                .onSuccess { result(true, "Account Created Successfully") }
                .onFailure { result(false, it.message ?: "Account Creation Failed") }
        }
    }

    fun resetPassword(
        email: String, result: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            repository.resetPassword(email)
                .onSuccess { result(true, "Password Reset Link Sent Successfully") }
                .onFailure { result(false, it.message ?: "Failed to Send Reset Link") }
        }
    }

    fun checkEmailExists(
        email: String, result: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            result(repository.checkEmailExists(email))
        }
    }

    fun getCurrentUser(result: (UserModel?) -> Unit) {
        viewModelScope.launch {
            result(repository.getCurrentUser())
        }
    }

    fun updateProfile(
        fullName: String, phone: String, profileImage: String, result: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            repository.updateProfile(fullName, phone, profileImage)
                .onSuccess { result(true, "Profile Updated Successfully") }
                .onFailure { result(false, it.message ?: "Profile Update Failed") }
        }
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        result: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            repository.changePassword(
                currentPassword,
                newPassword
            ).onSuccess {
                result(
                    true,
                    "Password changed successfully."
                )
            }.onFailure {
                result(
                    false,
                    it.message ?: "Password change failed."
                )
            }
        }
    }

    fun logout() {
        repository.logout()
    }

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }
}