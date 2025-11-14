package com.apps_moviles.taller_android_studio

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    val userName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dbFirebase = Firebase.firestore

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Bienvenido",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = userName.value,
                onValueChange = { userName.value = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = {
                        showPassword.value = !showPassword.value
                    }) {
                        Text(
                            if (showPassword.value) "Ocultar" else "Ver",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(55.dp),
                onClick = {
                    if (userName.value.isBlank() || password.value.isBlank()) {
                        Toast.makeText(
                            context,
                            "Por favor ingrese sus credenciales",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        dbFirebase.collection("usuarios")
                            .document(userName.value)
                            .get()
                            .addOnSuccessListener { usuario ->
                                if (usuario.exists()) {
                                    val pwd = usuario.getString("password")
                                    if (pwd == password.value) {
                                        Toast.makeText(context, "Inicio exitoso", Toast.LENGTH_SHORT)
                                            .show()
                                        onLoginSuccess(userName.value)
                                    } else {
                                        Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }
                }
            ) {
                Text(text = "Iniciar Sesión")
            }
        }
    }
}
