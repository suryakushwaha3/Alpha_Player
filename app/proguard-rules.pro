 # ===================================================================
 # 1. R8 FULL MODE & CORE COMPATIBILITY
 # ===================================================================
 -dontwarn **
 -keepattributes InnerClasses, Signature, EnclosingMethod, *Annotation*

 # ===================================================================
 # 2. GOOGLE SIGN-IN & AUTHENTICATION (Fixes Credential Errors)
 # ===================================================================
 -keep class com.google.android.gms.auth.api.** { *; }
 -keep class com.google.android.libraries.identity.googleid.** { *; }
 -keep class androidx.credentials.** { *; }
 -keep class com.google.android.gms.common.api.** { *; }
 -keep class com.google.firebase.auth.** { *; }

 # ===================================================================
 # 3. JETPACK COMPOSE & UI PRESERVATION (Fixes UI/TopBar Scale Bugs)
 # ===================================================================
 -keep class androidx.compose.** { *; }
 -keep class androidx.compose.material3.** { *; }
 -keep class androidx.compose.ui.** { *; }

 # Protect custom Layout Density & Composables (e.g., AutoScaledBox)
 -keepclassmembers class * {
     @androidx.compose.runtime.Composable <methods>;
 }

 # Preserve WindowInsets, Status Bars & Top Bar controllers
 -keep class androidx.core.view.WindowInsetsCompat { *; }
 -keep class androidx.core.view.WindowCompat { *; }

 # Preserve resource drawables and vector icons
 -keep class com.example.alphaplayer.R$* { *; }

 # ===================================================================
 # 4. VIEWMODEL & NAVIGATION PRESERVATION
 # ===================================================================
 -keep class com.example.alphaplayer.viewmodel.** { *; }
 -keep class com.example.alphaplayer.ui.navigation.** { *; }

 # ===================================================================
 # 5. DATA MODELS & KOTLINX SERIALIZATION (Added to fix text missing issue)
 # ===================================================================
 -keep class com.example.alphaplayer.data.model.** { *; }
 -keepclassmembers class com.example.alphaplayer.data.model.** {
     *;
 }
 -keepclassmembers class * {
     @kotlinx.serialization.Serializable <fields>;
 }
 -keep class kotlinx.serialization.** { *; }
 -dontwarn kotlinx.serialization.**

 # ===================================================================
 # 6. FIX: GOOGLE PLAY SERVICES & BROKER SECURITY EXCEPTION
 # ===================================================================
 -keep class com.google.android.gms.** { *; }
 -keep class com.google.common.** { *; }
 -dontwarn com.google.android.gms.**
 -dontwarn com.google.common.**

 -keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
 }

 -keep public class * extends android.os.Binder { *; }
 -keep public class * extends android.os.IInterface { *; }
 -keep interface android.os.IBinder { *; }