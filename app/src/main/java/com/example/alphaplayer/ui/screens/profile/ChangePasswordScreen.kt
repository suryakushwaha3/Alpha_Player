package com.example.alphaplayer.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(

    navController: NavHostController,

    authViewModel: AuthViewModel = viewModel()

) {

    val context = LocalContext.current

    var oldPassword by remember {

        mutableStateOf("")

    }

    var newPassword by remember {

        mutableStateOf("")

    }

    var confirmPassword by remember {

        mutableStateOf("")

    }

    Scaffold(

        containerColor = Color.Black,

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text = "Change Password",

                        color = Color.White

                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            navController.popBackStack()

                        }

                    ) {

                        Icon(

                            imageVector = Icons.Default.ArrowBack,

                            contentDescription = null,

                            tint = Color.White

                        )

                    }

                },

                colors = TopAppBarDefaults.topAppBarColors(

                    containerColor = Color.Black

                )

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(20.dp)

        ) {

            Icon(

                imageVector = Icons.Default.Lock,

                contentDescription = null,

                tint = Color(0xFFE50914),

                modifier = Modifier.size(70.dp)

            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(

                value = oldPassword,

                onValueChange = {

                    oldPassword = it

                },

                label = {

                    Text("Current Password")

                },

                visualTransformation = PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth(),

                singleLine = true

            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(

                value = newPassword,

                onValueChange = {

                    newPassword = it

                },

                label = {

                    Text("New Password")

                },

                visualTransformation = PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth(),

                singleLine = true

            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(

                value = confirmPassword,

                onValueChange = {

                    confirmPassword = it

                },

                label = {

                    Text("Confirm Password")

                },

                visualTransformation = PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth(),

                singleLine = true

            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(

                onClick = {

                    when {

                        oldPassword.isBlank() -> {

                            Toast.makeText(

                                context,

                                "Enter current password",

                                Toast.LENGTH_SHORT

                            ).show()

                        }

                        newPassword.isBlank() -> {

                            Toast.makeText(

                                context,

                                "Enter new password",

                                Toast.LENGTH_SHORT

                            ).show()

                        }

                        confirmPassword.isBlank() -> {

                            Toast.makeText(

                                context,

                                "Confirm your password",

                                Toast.LENGTH_SHORT

                            ).show()

                        }

                        newPassword != confirmPassword -> {

                            Toast.makeText(

                                context,

                                "Passwords do not match",

                                Toast.LENGTH_SHORT

                            ).show()

                        }

                        newPassword.length < 6 -> {

                            Toast.makeText(

                                context,

                                "Password must be at least 6 characters",

                                Toast.LENGTH_SHORT

                            ).show()

                        }

                        else -> {

                            authViewModel.changePassword(

                                currentPassword = oldPassword,

                                newPassword = newPassword

                            ) { success, message ->

                                Toast.makeText(

                                    context,

                                    message,

                                    Toast.LENGTH_SHORT

                                ).show()

                                if (success) {

                                    navController.popBackStack()

                                }

                            }

                        }

                    }

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor = Color(0xFFE50914)

                )

            ) {

                Text(

                    text = "Change Password",

                    fontSize = 18.sp,

                    color = Color.White

                )

            }

        }

    }

}