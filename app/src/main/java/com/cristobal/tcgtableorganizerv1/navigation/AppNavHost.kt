package com.cristobal.tcgtableorganizerv1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cristobal.tcgtableorganizerv1.ui.login.LoginScreen
import com.cristobal.tcgtableorganizerv1.ui.main.MainScreen

// Rutas de la app
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
}

/**
 * Árbol de navegación principal de la app.
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla con BottomBar y todas las pestañas
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
