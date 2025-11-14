package com.apps_moviles.taller_android_studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.apps_moviles.taller_android_studio.navigation.NavigationWrapper
import com.apps_moviles.taller_android_studio.ui.theme.Taller_android_studioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller_android_studioTheme {
                NavigationWrapper()
            }
        }
    }
}