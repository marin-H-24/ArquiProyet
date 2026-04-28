package com.marin.arquiproyet.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marin.arquiproyet.ui.screens.DetailScreen
import com.marin.arquiproyet.ui.screens.MainScreen
import com.marin.arquiproyet.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main",
        // Animaciones Cyber-Luxury: Deslizamiento lateral con fundido suave
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        }
    ) {
        // Pantalla Principal: Pasa el ViewModel para gestionar proyectos e ideas globales
        composable("main") {
            MainScreen(
                viewModel = viewModel,
                onNavigateToDetail = { projectName ->
                    navController.navigate("detail/$projectName")
                }
            )
        }

        // Pantalla de Detalle: Ahora recibe el ViewModel para no perder iconos ni apartados
        composable(
            route = "detail/{projectName}",
            arguments = listOf(navArgument("projectName") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Proyecto"

            DetailScreen(
                projectName = projectName,
                viewModel = viewModel, // Conexión vital para la persistencia
                onBack = { navController.popBackStack() }
            )
        }
    }
}