package com.example.alphaplayer.ui.screens.auth

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.res.painterResource
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
import androidx.navigation.NavHostController
import com.example.alphaplayer.R
import com.example.alphaplayer.ui.navigation.MyNavRoutes
import com.example.alphaplayer.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

// Helper Composable to keep UI responsive & auto-scaled on every phone screen
@Composable
fun AutoScaledBox(
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
fun LoginScreen(
    navController: NavHostController,
    onLoginClick: () -> Unit = {
        navController.navigate(MyNavRoutes.HomeScreen) {
            popUpTo(MyNavRoutes.LoginScreen) { inclusive = true }
        }
    },
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    // Fixed Status Bar Icons visibility for Dark Cyber Background
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val webClientId = "995822665794-a3llfuu6reap75blc9db0nf4snr4qg7m.apps.googleusercontent.com"

    // --- Google Sign-In Activity Result Launcher ---
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken

                if (idToken != null) {
                    Log.d("GOOGLE_SIGNIN", "ID Token Received: $idToken")
                    authViewModel.loginWithGoogle(idToken) { success, message ->
                        isLoading = false
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            onLoginClick()
                        }
                    }
                } else {
                    isLoading = false
                    Toast.makeText(context, "ID Token null mila!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                isLoading = false
                Log.e("GOOGLE_SIGNIN_ERROR", "Status Code: ${e.statusCode} | Message: ${e.message}")
                Toast.makeText(context, "Google Sign-In Failed (Error Code: ${e.statusCode})", Toast.LENGTH_LONG).show()
            }
        } else {
            isLoading = false
            Log.d("GOOGLE_SIGNIN", "User cancelled Google Sign-In")
        }
    }

    fun launchGoogleSignIn() {
        isLoading = true
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        // Force Account Selection Dialog Everytime
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
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

    // Much Darker & Deep Cyber-Purple / Obsidian Gradient Theme
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF030108), // Almost Pitch Black Violet
            Color(0xFF0A0314), // Ultra Dark Deep Purple
            Color(0xFF17082B)  // Rich Dark Magenta-Violet Shade
        )
    )

    AutoScaledBox {
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
                    // Header Title
                    Text(
                        text = "Alpha Player",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Subtitle
                    Text(
                        text = "Log in to your account",
                        fontSize = 14.sp,
                        color = Color(0xFFD8B4FE),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

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
                                .padding(horizontal = 24.dp, vertical = 32.dp), // Top and bottom padding increased to make the card taller/bigger
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Email Input Field
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it.trim() },
                                label = { Text("Email Address", color = Color(0xFFC084FC)) },
                                placeholder = { Text("john.doe@example.com", color = Color(0xFF4A1D70)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.fillMaxWidth(),
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

                            Spacer(modifier = Modifier.height(18.dp))

                            // Password Input Field
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Password", color = Color(0xFFC084FC)) },
                                placeholder = { Text("Enter your password", color = Color(0xFF4A1D70)) },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                            tint = Color(0xFFC084FC)
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
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

                            // Forgot Password Text Button aligned to the right
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                TextButton(
                                    onClick = { onForgotPasswordClick() },
                                    enabled = !isLoading,
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(
                                        text = "Forgot Password?",
                                        color = Color(0xFFF472B6),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Primary CTA Button
                            Button(
                                onClick = {
                                    if (email.isBlank() || password.isBlank()) {
                                        Toast.makeText(context, "Please enter your email and password.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        isLoading = true
                                        authViewModel.login(email, password) { success, message ->
                                            isLoading = false
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                            if (success) {
                                                onLoginClick()
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
                                enabled = email.isNotBlank() && password.isNotBlank() && !isLoading
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
                                            color = Color.White,
                                            modifier = Modifier.size(24.dp),
                                            strokeWidth = 2.5.dp
                                        )
                                    } else {
                                        Text(
                                            text = "Log In",
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.15f))
                        Text(
                            text = "OR CONTINUE WITH",
                            modifier = Modifier.padding(horizontal = 12.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFD8B4FE)
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.15f))
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Google Sign-In Button
                    OutlinedButton(
                        onClick = {
                            if (!isLoading) {
                                launchGoogleSignIn()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF130624).copy(alpha = 0.7f),
                            contentColor = Color.White
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color(0xFFD946EF),
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = "Google Logo",
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Continue with Google",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Footer Navigation Option
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account?",
                            fontSize = 13.sp,
                            color = Color(0xFFD8B4FE)
                        )
                        TextButton(
                            onClick = { onSignUpClick() },
                            enabled = !isLoading
                        ) {
                            Text(
                                text = "Sign Up",
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