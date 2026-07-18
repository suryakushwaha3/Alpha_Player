package com.example.alphaplayer.AuthScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.Auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    navController: NavHostController
) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    var email by remember {
        mutableStateOf("")
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Forgot Password")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )

                    }

                }

            )

        }

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            contentAlignment = Alignment.Center

        ) {

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                shape = RoundedCornerShape(20.dp),

                elevation = CardDefaults.cardElevation(8.dp)

            ) {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center

                ) {

                    Text(
                        text = "Reset Your Password",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Enter your registered email address and we'll send you a password reset link."
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    OutlinedTextField(

                        value = email,

                        onValueChange = {
                            email = it.trim()
                        },

                        modifier = Modifier.fillMaxWidth(),

                        label = {
                            Text("Registered Email")
                        },

                        leadingIcon = {

                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )

                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),

                        singleLine = true

                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(

                        onClick = {

                            if (email.isBlank()) {

                                Toast.makeText(
                                    context,
                                    "Please enter your email",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {

                                authViewModel.checkEmailExists(email) { exists ->

                                    if (exists) {

                                        authViewModel.resetPassword(
                                            email
                                        ) { success, message ->

                                            Toast.makeText(
                                                context,
                                                message,
                                                Toast.LENGTH_LONG
                                            ).show()

                                            if (success) {

                                                navController.popBackStack()

                                            }

                                        }

                                    } else {

                                        Toast.makeText(
                                            context,
                                            "No account found with this email.",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }

                                }

                            }

                        },

                        modifier = Modifier.fillMaxWidth(),

                        enabled = email.isNotBlank()

                    ) {

                        Text("Send Reset Link")

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(

                        onClick = {

                            navController.popBackStack()

                        }

                    ) {

                        Text("Back to Login")

                    }

                }

            }

        }

    }

}