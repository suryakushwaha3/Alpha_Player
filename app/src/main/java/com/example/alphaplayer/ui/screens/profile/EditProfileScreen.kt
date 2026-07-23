package com.example.alphaplayer.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(

    navController: NavHostController,

    authViewModel: AuthViewModel = viewModel()

) {

    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var phone by remember { mutableStateOf("") }

    var profileImage by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {

        authViewModel.getCurrentUser { user ->

            user?.let {

                fullName = it.fullName

                email = it.email

                phone = it.phone

                profileImage = it.profileImage

            }

            isLoading = false

        }

    }

    Scaffold(

        containerColor = Color.Black,

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text = "Edit Profile",

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

        if (isLoading) {

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),

                contentAlignment = Alignment.Center

            ) {

                CircularProgressIndicator(

                    color = Color(0xFFE50914)

                )

            }

        } else {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(20.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Card(

                    modifier = Modifier.size(120.dp),

                    shape = CircleShape,

                    colors = CardDefaults.cardColors(

                        containerColor = Color.DarkGray

                    )

                ) {

                    Box(

                        modifier = Modifier.fillMaxSize(),

                        contentAlignment = Alignment.Center

                    ) {

                        Icon(

                            imageVector = Icons.Default.AccountCircle,

                            contentDescription = null,

                            tint = Color.White,

                            modifier = Modifier.size(90.dp)

                        )

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(

                    onClick = {

                        Toast.makeText(

                            context,

                            "Profile image feature coming soon",

                            Toast.LENGTH_SHORT

                        ).show()

                    }

                ) {

                    Text(

                        text = "Change Profile Photo",

                        color = Color(0xFFE50914)

                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(

                    value = fullName,

                    onValueChange = {

                        fullName = it

                    },
                    label = {

                        Text("Full Name")

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true

                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(

                    value = email,

                    onValueChange = {email=it },

                    enabled = true,

                    label = {

                        Text("Email")

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true

                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(

                    value = phone,

                    onValueChange = {

                        phone = it

                    },

                    label = {

                        Text("Phone Number")

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true

                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(

                    onClick = {

                        when {

                            fullName.isBlank() -> {

                                Toast.makeText(

                                    context,

                                    "Enter Full Name",

                                    Toast.LENGTH_SHORT

                                ).show()

                            }

                            phone.length != 10 -> {

                                Toast.makeText(

                                    context,

                                    "Enter Valid Phone Number",

                                    Toast.LENGTH_SHORT

                                ).show()

                            }

                            else -> {

                                authViewModel.updateProfile(

                                    fullName = fullName,

                                    phone = phone,

                                    profileImage = profileImage

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

                        text = "Save Changes",

                        fontSize = 18.sp,

                        fontWeight = FontWeight.Bold

                    )

                }

            }

        }

    }

}