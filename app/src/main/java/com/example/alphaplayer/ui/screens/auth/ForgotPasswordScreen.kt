package com.example.alphaplayer.ui.screens.auth

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LockReset
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alphaplayer.viewmodel.AuthViewModel

// Helper Composable to keep UI responsive & auto-scaled on every phone screen
@Composable
fun ForgotPasswordAutoScaledBox(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Fixed Status Bar Icons visibility for Dark Cyber Background
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

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

    // Much Darker & Deep Cyber-Purple / Obsidian Gradient Theme (Matching Login & SignUp)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF030108), // Almost Pitch Black Violet
            Color(0xFF0A0314), // Ultra Dark Deep Purple
            Color(0xFF17082B)  // Rich Dark Magenta-Violet Shade
        )
    )

    ForgotPasswordAutoScaledBox {
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
                    // Back Button Top Alignment
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(0xFF130624)
                            ),
                            modifier = Modifier.border(1.dp, Color(0xFFD946EF).copy(alpha = 0.3f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFFD946EF)
                            )
                        }
                    }

                    // Header Icon Container with Glow
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
                                imageVector = Icons.Outlined.LockReset,
                                contentDescription = null,
                                tint = Color(0xFFD946EF),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title & Description
                    Text(
                        text = "Forgot Password? 🔐",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Don't worry! Enter your registered email address below to receive a password reset link.",
                        fontSize = 13.sp,
                        color = Color(0xFFD8B4FE),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Main Form Card (Bigger top/bottom padding matching Login and SignUp screens)
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
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it.trim() },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Email Address", color = Color(0xFFC084FC)) },
                                placeholder = { Text("example@gmail.com", color = Color(0xFF4A1D70)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Email,
                                        contentDescription = null,
                                        tint = Color(0xFFC084FC)
                                    )
                                },
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

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    if (email.isBlank()) {
                                        Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                                    } else {
                                        isLoading = true
                                        authViewModel.checkEmailExists(email) { exists ->
                                            if (exists) {
                                                authViewModel.resetPassword(email) { success, message ->
                                                    isLoading = false
                                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                                    if (success) {
                                                        navController.popBackStack()
                                                    }
                                                }
                                            } else {
                                                isLoading = false
                                                Toast.makeText(context, "No account found with this email.", Toast.LENGTH_LONG).show()
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
                                enabled = email.isNotBlank() && !isLoading
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
                                            text = "Send Reset Link",
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

                    TextButton(
                        onClick = { navController.popBackStack() },
                        enabled = !isLoading
                    ) {
                        Text(
                            text = "Back to Login",
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