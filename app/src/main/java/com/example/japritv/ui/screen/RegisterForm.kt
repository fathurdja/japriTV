package com.example.japritv.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R
import com.example.japritv.ui.theme.JapriTvTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegistrationForm(
    referralCode: String = "0",
    onSuccess: (String) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.japripay),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(bottom = 8.dp)
                )

                // Form Title
                Text(
                    text = "Form Registrasi",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Full Name
                FormField(
                    label = "Nama Lengkap:",
                    value = name,
                    onValueChange = { name = it }
                )
                FormField(
                    label = "email",
                    value = email,
                    onValueChange = { email = it }
                )


                // PIN
                FormField(
                    label = "PIN (Minimal 8 karakter):",
                    value = pin,
                    onValueChange = { pin = it },
                    keyboardType = KeyboardType.NumberPassword,
                    isPassword = true
                )
                // Referral Code
                FormField(
                    label = "Kode Referral (Upline):",
                    value = referralCode,
                    backgroundColor = Color.LightGray,
                    onValueChange = {}
                )

                // Register Button
                Button(
                    onClick = {
                        if (email.isBlank() || pin.length < 8 || pin.isBlank() || name.isBlank()) {
                            errorMessage = "Nama, Email dan PIN harus diisi, dan PIN minimal 8 karakter"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }

                        auth.fetchSignInMethodsForEmail(email)
                            .addOnCompleteListener { methodTask ->
                                if (methodTask.isSuccessful) {
                                    val signInMethods = methodTask.result?.signInMethods
                                    if (!signInMethods.isNullOrEmpty()) {
                                        // Email sudah terdaftar
                                        Toast.makeText(context, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Email belum terdaftar, lanjut buat akun
                                        auth.createUserWithEmailAndPassword(email, pin)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    val user = auth.currentUser
                                                    user?.getIdToken(true)
                                                        ?.addOnSuccessListener { result ->
                                                            val token = result.token
                                                            Log.d("RegistrationForm", "Token: $token")
                                                            if (token != null) {
                                                                onSuccess(token)
                                                            }
                                                        }
                                                        ?.addOnFailureListener {
                                                            errorMessage = "Gagal ambil token: ${it.localizedMessage}"
                                                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                                        }
                                                } else {
                                                    errorMessage = task.exception?.localizedMessage ?: "Registrasi gagal"
                                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                    }
                                } else {
                                    Toast.makeText(context, "Gagal memeriksa email", Toast.LENGTH_SHORT).show()
                                }
                            }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B5998)
                    )
                ) {
                    Text(
                        text = "DAFTAR",
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.White
                    )
                }

            }
        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    backgroundColor: Color = Color.White,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None
        )
    }
}

@Preview
@Composable
private fun RegistrationFormPreview() {
    JapriTvTheme {
        RegistrationForm(
            referralCode = "123456"
        )
    }
}