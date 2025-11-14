package com.apps_moviles.taller_android_studio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

data class Product(
    val name: String = "",
    val price: Long = 0,
    val description: String = "",
    val image: String = ""
)

@Composable
fun HomeScreen(username: String, onLogout: () -> Unit) {

    val db = Firebase.firestore
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    // Cargar productos desde Firebase
    LaunchedEffect(true) {
        db.collection("productos").get().addOnSuccessListener { result ->
            products = result.documents.mapNotNull { doc ->
                doc.toObject(Product::class.java)
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = "Hola, $username",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = onLogout,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                items(products) { product ->
                    ProductCard(product = product)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}
