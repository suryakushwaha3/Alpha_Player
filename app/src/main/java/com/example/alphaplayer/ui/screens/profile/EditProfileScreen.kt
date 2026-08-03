package com.example.alphaplayer.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

    // Action saving loading state
    var isSaving by remember { mutableStateOf(false) }

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
        containerColor = Color(0xFF0B0B0E), // Deep rich black background for premium feel
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
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
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0B0B0E)
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0B0B0E)),
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
                    .background(Color(0xFF0B0B0E))
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // 🌟 Glowing Profile Picture Container
                Box(
                    modifier = Modifier
                        .size(115.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0xFFE50914).copy(alpha = 0.3f), Color.Transparent)
                            )
                        )
                        .border(1.5.dp, Color(0xFFE50914).copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1C1C24)
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Color(0xFFB3B3C1),
                                modifier = Modifier.size(85.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                        color = Color(0xFFE50914),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 🌟 Professional Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF14141A)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF22222D)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        // Full Name Field
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isSaving,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,       // टाइप करते वक्त टेक्स्ट सफेद दिखेगा
                                unfocusedTextColor = Color.White,     // बिना फोकस के भी टेक्स्ट सफेद दिखेगा
                                focusedBorderColor = Color(0xFFE50914),
                                unfocusedBorderColor = Color(0xFF2D2D3A),
                                focusedLabelColor = Color(0xFFE50914),
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color(0xFFE50914)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Email Field
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            enabled = true,
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                disabledTextColor = Color.Gray,       // अगर ईमेल डिसएबल है तो ग्रे दिखेगा
                                focusedBorderColor = Color(0xFFE50914),
                                unfocusedBorderColor = Color(0xFF2D2D3A),
                                focusedLabelColor = Color(0xFFE50914),
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color(0xFFE50914),
                                disabledBorderColor = Color(0xFF2D2D3A),
                                disabledContainerColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Phone Number Field
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Phone Number") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isSaving,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFE50914),
                                unfocusedBorderColor = Color(0xFF2D2D3A),
                                focusedLabelColor = Color(0xFFE50914),
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color(0xFFE50914)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // 🌟 Save Changes Button with Progress Loader
                Button(
                    onClick = {
                        when {
                            fullName.isBlank() -> {
                                Toast.makeText(context, "Enter Full Name", Toast.LENGTH_SHORT).show()
                            }
                            phone.length != 10 -> {
                                Toast.makeText(context, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                isSaving = true
                                authViewModel.updateProfile(
                                    fullName = fullName,
                                    phone = phone,
                                    profileImage = profileImage
                                ) { success, message ->
                                    isSaving = false
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                    shape = RoundedCornerShape(14.dp),
                    enabled = !isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914),
                        disabledContainerColor = Color(0xFFE50914).copy(alpha = 0.5f)
                    )
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Text(
                            text = "Save Changes",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}