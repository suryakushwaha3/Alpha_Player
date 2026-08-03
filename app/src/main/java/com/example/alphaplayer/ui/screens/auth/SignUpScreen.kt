package com.example.alphaplayer.ui.screens.auth

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alphaplayer.viewmodel.AuthViewModel

// Helper Composable to keep UI responsive & auto-scaled on every phone screen
@Composable
fun SignUpAutoScaledBox(
    targetDesignWidthDp: Float = 390f,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val currentWidthDp = maxWidth.value
        if (currentWidthDp > 0) {
            val scaleFactor = currentWidthDp / targetDesignWidthDp
            val currentDensity = LocalDensity.current

            val customDensity = Density(
                density = currentDensity.density * scaleFactor,
                fontScale = currentDensity.fontScale
            )

            CompositionLocalProvider(LocalDensity provides customDensity) {
                content()
            }
        } else {
            content()
        }
    }
}

@Composable
fun SignUpScreen(
    onCreateAccountClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    // Fixed Status Bar Icons visibility for Dark Cyber Background
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Multi-Layer Dynamic Background Pulse Animation
    val infiniteTransition = rememberInfiniteTransition(label = "EliteBackgroundPulse")
    val scaleAnim by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbScale"
    )

    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "OrbAlpha"
    )

    // Much Darker & Deep Cyber-Purple / Obsidian Gradient Theme
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF030108), // Almost Pitch Black Violet
            Color(0xFF0A0314), // Ultra Dark Deep Purple
            Color(0xFF17082B)  // Rich Dark Magenta-Violet Shade
        )
    )

    SignUpAutoScaledBox {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            // Dynamic Glowing Neon Orb for Depth (Intense Neon Magenta Glow)
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .scale(scaleAnim)
                    .blur(140.dp)
                    .background(Color(0xFFC026D3).copy(alpha = alphaAnim), CircleShape)
            )

            // Smooth Card Entrance Animation
            var visibleState by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                visibleState = true
            }

            AnimatedVisibility(
                visible = visibleState,
                enter = fadeIn(animationSpec = tween(700)) +
                        slideInVertically(animationSpec = tween(700, easing = FastOutSlowInEasing)) { it / 4 } +
                        scaleIn(initialScale = 0.92f, animationSpec = tween(700)),
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 4.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Modern Circular Icon Badge with Glowing Border
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFF130624),
                        modifier = Modifier
                            .size(76.dp)
                            .border(1.dp, Color(0xFFD946EF).copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                            .shadow(16.dp, RoundedCornerShape(24.dp), spotColor = Color(0xFFC026D3))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.PersonAdd,
                                contentDescription = null,
                                tint = Color(0xFFD946EF),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title & Subtitle
                    Text(
                        text = "Get Started ",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Create an account to continue with Alpha Player",
                        fontSize = 13.sp,
                        color = Color(0xFFD8B4FE),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Main Premium Card (Bigger top/bottom padding matching Login screen style)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 32.dp, shape = RoundedCornerShape(32.dp), spotColor = Color(0xFFC026D3))
                            .border(
                                width = 1.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.2f), Color.White.copy(alpha = 0.03f))
                                ),
                                shape = RoundedCornerShape(32.dp)
                            ),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B0415).copy(alpha = 0.85f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Full Name Input
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Full Name", color = Color(0xFFC084FC)) },
                                placeholder = { Text("John Doe", color = Color(0xFF4A1D70)) },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                enabled = !isLoading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD946EF),
                                    unfocusedBorderColor = Color(0xFF3B0764),
                                    focusedLabelColor = Color(0xFFD946EF),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color(0xFF130624),
                                    unfocusedContainerColor = Color(0xFF130624)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Email Input
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it.trim() },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Email Address", color = Color(0xFFC084FC)) },
                                placeholder = { Text("example@gmail.com", color = Color(0xFF4A1D70)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                enabled = !isLoading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD946EF),
                                    unfocusedBorderColor = Color(0xFF3B0764),
                                    focusedLabelColor = Color(0xFFD946EF),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color(0xFF130624),
                                    unfocusedContainerColor = Color(0xFF130624)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Password Input
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Password", color = Color(0xFFC084FC)) },
                                placeholder = { Text("Enter password", color = Color(0xFF4A1D70)) },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                            contentDescription = null,
                                            tint = Color(0xFFC084FC)
                                        )
                                    }
                                },
                                enabled = !isLoading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD946EF),
                                    unfocusedBorderColor = Color(0xFF3B0764),
                                    focusedLabelColor = Color(0xFFD946EF),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color(0xFF130624),
                                    unfocusedContainerColor = Color(0xFF130624)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Confirm Password Input
                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Confirm Password", color = Color(0xFFC084FC)) },
                                placeholder = { Text("Re-enter password", color = Color(0xFF4A1D70)) },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                        Icon(
                                            imageVector = if (confirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                            contentDescription = null,
                                            tint = Color(0xFFC084FC)
                                        )
                                    }
                                },
                                enabled = !isLoading,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD946EF),
                                    unfocusedBorderColor = Color(0xFF3B0764),
                                    focusedLabelColor = Color(0xFFD946EF),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color(0xFF130624),
                                    unfocusedContainerColor = Color(0xFF130624)
                                )
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Create Account Action Button with Neon Gradient
                            Button(
                                onClick = {
                                    if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                    } else if (password != confirmPassword) {
                                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                    } else {
                                        isLoading = true
                                        authViewModel.createAccount(
                                            fullName = name,
                                            email = email,
                                            password = password
                                        ) { success, message ->
                                            isLoading = false
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                            if (success) {
                                                onCreateAccountClick()
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .shadow(elevation = 12.dp, shape = RoundedCornerShape(16.dp), spotColor = Color(0xFFC026D3)),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(0.dp),
                                enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && !isLoading
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.horizontalGradient(
                                                colors = listOf(Color(0xFF7E22CE), Color(0xFF9333EA), Color(0xFFC084FC))
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = Color.White,
                                            strokeWidth = 2.5.dp
                                        )
                                    } else {
                                        Text(
                                            text = "Create Account",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Bottom Login Prompt Navigation
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account?",
                            fontSize = 13.sp,
                            color = Color(0xFFD8B4FE)
                        )
                        TextButton(
                            onClick = onLoginClick,
                            enabled = !isLoading
                        ) {
                            Text(
                                text = "Log In",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF472B6)
                            )
                        }
                    }
                }
            }
        }
    }
}