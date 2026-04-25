package com.marin.arquiproyet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.marin.arquiproyet.ui.theme.ArquiproyetTheme
import com.marin.arquiproyet.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArquiproyetTheme {
                AppNavigation()
            }
        }
    }
}