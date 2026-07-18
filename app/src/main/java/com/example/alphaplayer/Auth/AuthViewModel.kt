package com.example.alphaplayer.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()


    // ==========================
    // Login
    // ==========================

    fun login(
        email: String,
        password: String,
        result: (Boolean, String) -> Unit
    ) {

        viewModelScope.launch {

            repository.login(
                email,
                password
            ).onSuccess {

                result(
                    true,
                    "Login Successful"
                )

            }.onFailure {

                result(
                    false,
                    it.message ?: "Login Failed"
                )

            }

        }

    }


    // ==========================
    // Create Account
    // ==========================

    fun createAccount(
        fullName: String,
        email: String,
        password: String,
        result: (Boolean, String) -> Unit
    ) {

        viewModelScope.launch {

            repository.createAccount(
                fullName,
                email,
                password
            ).onSuccess {

                result(
                    true,
                    "Account Created Successfully"
                )

            }.onFailure {

                result(
                    false,
                    it.message ?: "Account Creation Failed"
                )

            }

        }

    }



    // ==========================
    // Forgot Password
    // ==========================

    fun resetPassword(
        email: String,
        result: (Boolean, String) -> Unit
    ) {

        viewModelScope.launch {

            repository.resetPassword(email)
                .onSuccess {

                    result(
                        true,
                        "Password Reset Link Sent Successfully"
                    )

                }
                .onFailure {

                    result(
                        false,
                        it.message ?: "Failed to Send Reset Link"
                    )

                }

        }

    }

    //check email available ya not
    fun checkEmailExists(
        email: String,
        result: (Boolean) -> Unit
    ) {

        viewModelScope.launch {

            result(
                repository.checkEmailExists(email)
            )

        }

    }


    // ==========================
    // Logout
    // ==========================

    fun logout() {

        repository.logout()

    }


    // ==========================
    // Check User Login
    // ==========================

    fun isUserLoggedIn(): Boolean {

        return repository.isUserLoggedIn()

    }

}