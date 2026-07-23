package com.example.alphaplayer.ui.screens.profile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    navController: NavHostController

) {

    Scaffold(

        containerColor = Color.Black,

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text = "Privacy Policy",

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
                            contentDescription = "Back",
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

            Text(

                text = "Privacy Policy",

                color = Color.White,

                fontSize = 26.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(20.dp))

            PolicyTitle("1. Information We Collect")

            PolicyText(
                "We collect basic account information such as your name, email address, and profile details to provide our services."
            )

            PolicyTitle("2. How We Use Your Information")

            PolicyText(
                "Your information is used to manage your account, improve app performance, personalize content, and enhance user experience."
            )

            PolicyTitle("3. Data Security")

            PolicyText(
                "We use secure Firebase services to protect your personal information from unauthorized access."
            )

            PolicyTitle("4. Third-Party Services")

            PolicyText(
                "Our application may use Firebase Authentication, Firestore Database, and other Google services."
            )

            PolicyTitle("5. Your Rights")

            PolicyText(
                "You may update or delete your profile information at any time through the application."
            )

            PolicyTitle("6. Contact Us")

            PolicyText(
                "For any privacy-related questions, contact us at:\n\nalphaplayer@gmail.com"
            )

            Spacer(modifier = Modifier.height(30.dp))

        }

    }

}

@Composable
fun PolicyTitle(

    text: String

) {

    Text(

        text = text,

        color = Color.White,

        fontSize = 20.sp,

        fontWeight = FontWeight.Bold

    )

    Spacer(modifier = Modifier.height(8.dp))

}
@Composable
fun PolicyText(

    text: String

) {

    Text(

        text = text,
        color = Color.LightGray,
        fontSize = 15.sp,
        lineHeight = 24.sp)

    Spacer(modifier = Modifier.height(20.dp))

}