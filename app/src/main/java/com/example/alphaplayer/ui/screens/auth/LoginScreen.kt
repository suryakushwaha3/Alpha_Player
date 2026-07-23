//
//package com.example.alphaplayer.ui.screens.auth
//
//import android.app.Activity
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.credentials.CredentialManager
//import androidx.credentials.CustomCredential
//import androidx.credentials.GetCredentialRequest
//import androidx.credentials.exceptions.GetCredentialCancellationException
//import androidx.credentials.exceptions.GetCredentialException
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.alphaplayer.R
//import com.example.alphaplayer.ui.navigation.MyNavRoutes
//import com.example.alphaplayer.viewmodel.AuthViewModel
//import com.google.android.libraries.identity.googleid.GetGoogleIdOption
//import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
//import kotlinx.coroutines.launch
//
//@Composable
//fun LoginScreen(
//    navController: NavHostController,
//    onLoginClick: () -> Unit = {
//        navController.navigate(MyNavRoutes.HomeScreen) {
//            popUpTo(MyNavRoutes.LoginScreen) { inclusive = true }
//        }
//    },
//    onSignUpClick: () -> Unit = {},
//    onForgotPasswordClick: () -> Unit = {}
//) {
//    val authViewModel: AuthViewModel = viewModel()
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var passwordVisible by remember { mutableStateOf(false) }
//    var isLoading by remember { mutableStateOf(false) }
//
//    val webClientId = "995822665794-aehkp9emfccm8na4nrvrnim652hgauhb.apps.googleusercontent.com"
//
//    fun launchGoogleSignIn() {
//        val activity = context as? Activity
//        if (activity == null) {
//            Toast.makeText(context, "Activity Context not found!", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val credentialManager = CredentialManager.create(activity)
//
//        // Account selection dialog hamesha dikhane ke liye setAutoSelectEnabled(false) set kiya hai
//        val googleIdOption = GetGoogleIdOption.Builder()
//            .setFilterByAuthorizedAccounts(false)
//            .setServerClientId(webClientId)
//            .setAutoSelectEnabled(false)
//            .build()
//
//        val request = GetCredentialRequest.Builder()
//            .addCredentialOption(googleIdOption)
//            .build()
//
//        coroutineScope.launch {
//            isLoading = true
//            try {
//                val result = credentialManager.getCredential(
//                    request = request,
//                    context = activity
//                )
//
//                val credential = result.credential
//                if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//                    val idToken = googleIdTokenCredential.idToken
//
//                    Log.d("GOOGLE_SIGNIN", "ID Token Received: $idToken")
//
//                    authViewModel.loginWithGoogle(idToken) { success, message ->
//                        isLoading = false
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                        if (success) {
//                            onLoginClick()
//                        }
//                    }
//                } else {
//                    isLoading = false
//                    Log.e("GOOGLE_SIGNIN", "Unrecognized credential type: ${credential.type}")
//                }
//            } catch (e: GetCredentialCancellationException) {
//                isLoading = false
//                Log.d("GOOGLE_SIGNIN", "User cancelled sign-in")
//            } catch (e: GetCredentialException) {
//                isLoading = false
//                Log.e("GOOGLE_SIGNIN_ERROR", "GetCredentialException: ${e.message}", e)
//                Toast.makeText(context, "Sign-In Failed: ${e.message}", Toast.LENGTH_LONG).show()
//            } catch (e: Exception) {
//                isLoading = false
//                Log.e("GOOGLE_SIGNIN_ERROR", "Unknown Exception: ${e.message}", e)
//                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .statusBarsPadding()
//                .navigationBarsPadding()
//                .verticalScroll(rememberScrollState())
//                .padding(horizontal = 20.dp, vertical = 24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Alpha Player",
//                fontSize = 36.sp,
//                fontWeight = FontWeight.ExtraBold,
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            Text(
//                text = "Log in to your account",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
//                shape = RoundedCornerShape(20.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 22.dp, vertical = 75.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    OutlinedTextField(
//                        value = email,
//                        onValueChange = { email = it.trim() },
//                        modifier = Modifier.fillMaxWidth(),
//                        label = { Text("Email Address") },
//                        placeholder = { Text("john.doe@example.com") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                        singleLine = true,
//                        shape = RoundedCornerShape(12.dp),
//                        enabled = !isLoading
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = { password = it },
//                        modifier = Modifier.fillMaxWidth(),
//                        label = { Text("Password") },
//                        placeholder = { Text("Enter your password") },
//                        singleLine = true,
//                        shape = RoundedCornerShape(12.dp),
//                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                        trailingIcon = {
//                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                                Icon(
//                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
//                                )
//                            }
//                        },
//                        enabled = !isLoading
//                    )
//                    Spacer(modifier = Modifier.height(12.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        TextButton(
//                            onClick = { onForgotPasswordClick() },
//                            enabled = !isLoading
//                        ) {
//                            Text(
//                                text = "Forgot Password?",
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(24.dp))
//                    Button(
//                        onClick = {
//                            if (email.isBlank() || password.isBlank()) {
//                                Toast.makeText(context, "Please enter your email and password.", Toast.LENGTH_SHORT).show()
//                            } else {
//                                isLoading = true
//                                authViewModel.login(email, password) { success, message ->
//                                    isLoading = false
//                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                                    if (success) {
//                                        onLoginClick()
//                                    }
//                                }
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        shape = RoundedCornerShape(16.dp),
//                        enabled = email.isNotBlank() && password.isNotBlank() && !isLoading,
//                        colors = ButtonDefaults.buttonColors(contentColor = Color.White)
//                    ) {
//                        if (isLoading) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(24.dp),
//                                color = Color.White,
//                                strokeWidth = 2.5.dp
//                            )
//                        } else {
//                            Text(
//                                text = "Log In",
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                HorizontalDivider(modifier = Modifier.weight(1f))
//                Text(
//                    text = "OR CONTINUE WITH",
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                HorizontalDivider(modifier = Modifier.weight(1f))
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            OutlinedButton(
//                onClick = { launchGoogleSignIn() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp),
//                shape = RoundedCornerShape(16.dp),
//                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
//                colors = ButtonDefaults.outlinedButtonColors(
//                    containerColor = Color.White,
//                    contentColor = Color.Black
//                ),
//                enabled = !isLoading
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.google),
//                    contentDescription = "Google Logo",
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(16.dp))
//                Text(
//                    text = "Continue with Google",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Don't have an account?",
//                    fontSize = 15.sp,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                TextButton(
//                    onClick = { onSignUpClick() },
//                    enabled = !isLoading
//                ) {
//                    Text(
//                        text = "Sign Up",
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }
//    }
//}
package com.example.alphaplayer.ui.screens.auth

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

    // Fixed Status Bar Icons visibility for Light Background
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
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

    AutoScaledBox {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Alpha Player",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Log in to your account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp, vertical = 75.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it.trim() },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email Address") },
                            placeholder = { Text("john.doe@example.com") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isLoading
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Password") },
                            placeholder = { Text("Enter your password") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                    )
                                }
                            },
                            enabled = !isLoading
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { onForgotPasswordClick() },
                                enabled = !isLoading
                            ) {
                                Text(
                                    text = "Forgot Password?",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
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
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            enabled = email.isNotBlank() && password.isNotBlank() && !isLoading,
                            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.5.dp
                                )
                            } else {
                                Text(
                                    text = "Log In",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = "OR CONTINUE WITH",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = { launchGoogleSignIn() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    enabled = !isLoading
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Continue with Google",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextButton(
                        onClick = { onSignUpClick() },
                        enabled = !isLoading
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}