package com.example.alphaplayer.AuthScreens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.Auth.AuthViewModel
import com.example.alphaplayer.Navigation.MyNavRoutes

@Composable
fun LoginScreen(

    navController: NavHostController,

    onLoginClick: () -> Unit = {

        navController.navigate(MyNavRoutes.HomeScreen) {

            popUpTo(MyNavRoutes.LoginScreen) {

                inclusive = true

            }

        }

    },

    onSignUpClick: () -> Unit = {},

    onForgotPasswordClick: () -> Unit = {}

) {

    val authViewModel: AuthViewModel = viewModel()

    val context = LocalContext.current

    var email by remember {

        mutableStateOf("")

    }

    var password by remember {

        mutableStateOf("")

    }

    var passwordVisible by remember {

        mutableStateOf(false)

    }

    Box(

        modifier = Modifier.fillMaxSize(),

        contentAlignment = Alignment.Center

    ) {

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            elevation = CardDefaults.cardElevation(8.dp)

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 50.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(

                    text = "Alpha Player",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Login to continue")

                Spacer(modifier = Modifier.height(25.dp))

                OutlinedTextField(

                    value = email,

                    onValueChange = {

                        email = it

                    },

                    modifier = Modifier.fillMaxWidth(),

                    placeholder = {

                        Text("Email")

                    },

                    keyboardOptions = KeyboardOptions(

                        keyboardType = KeyboardType.Email

                    ),

                    singleLine = true

                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(

                    value = password,

                    onValueChange = {

                        password = it

                    },

                    modifier = Modifier.fillMaxWidth(),

                    placeholder = {

                        Text("Password")

                    },

                    singleLine = true,

                    visualTransformation =

                        if (passwordVisible)

                            VisualTransformation.None

                        else

                            PasswordVisualTransformation(),

                    trailingIcon = {

                        IconButton(

                            onClick = {

                                passwordVisible = !passwordVisible

                            }

                        ) {

                            Icon(

                                imageVector =

                                    if (passwordVisible)

                                        Icons.Default.Visibility

                                    else

                                        Icons.Default.VisibilityOff,

                                contentDescription = null

                            )

                        }

                    }

                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = {
                            onForgotPasswordClick()
                        }
                    ) {

                        Text("Forgot Password?")

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(

                    onClick = {

                        if (email.isBlank() || password.isBlank()) {

                            Toast.makeText(
                                context,
                                "Enter Email and Password",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            authViewModel.login(
                                email,
                                password
                            ) { success, message ->

                                Toast.makeText(
                                    context,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                if (success) {

                                    onLoginClick()

                                }

                            }

                        }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp),

                    enabled = email.isNotBlank() &&
                            password.isNotBlank(),

                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )

                ) {

                    Text(
                        text = "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(

                    onClick = {
                        onSignUpClick()
                    }

                ) {

                    Text("Create New Account")

                }

            }

        }

    }

}