package com.marin.arquiproyet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marin.arquiproyet.data.local.DataStoreManager
import com.marin.arquiproyet.ui.components.CyberBackground
import com.marin.arquiproyet.ui.navigation.AppNavigation
import com.marin.arquiproyet.ui.theme.ArquiproyetTheme
import com.marin.arquiproyet.ui.viewmodel.MainViewModel
import com.marin.arquiproyet.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(dataStoreManager)
            )

            ArquiproyetTheme {
                CyberBackground {
                    AppNavigation(mainViewModel)
                }
            }
        }
    }
}