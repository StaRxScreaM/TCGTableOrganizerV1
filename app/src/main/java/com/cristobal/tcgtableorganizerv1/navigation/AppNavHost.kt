package com.cristobal.tcgtableorganizerv1.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.cristobal.tcgtableorganizerv1.ui.login.LoginScreen
import com.cristobal.tcgtableorganizerv1.ui.main.MainScreen
import com.cristobal.tcgtableorganizerv1.ui.store.StoreDetailScreen

// Rutas de la app
sealed class Screen(val route: String) {

    object Login : Screen("login")
    object Main : Screen("main")

    // 🏪 Ruta detalle de tienda
    object StoreDetail : Screen("storeDetail/{storeName}/{location}/{mapUrl}") {
        fun createRoute(storeName: String, location: String, mapUrl: String): String {
            val encodedName = Uri.encode(storeName)
            val encodedLocation = Uri.encode(location)
            val encodedUrl = Uri.encode(mapUrl)
            return "storeDetail/$encodedName/$encodedLocation/$encodedUrl"
        }
    }
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
        // 🔐 Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 🏠 Pantalla principal con BottomBar
        composable(Screen.Main.route) {
            MainScreen(
                onOpenStore = { name, location, mapUrl ->
                    navController.navigate(
                        Screen.StoreDetail.createRoute(name, location, mapUrl)
                    )
                }
            )
        }

        // 🏪 Detalle de tienda
        composable(
            route = Screen.StoreDetail.route,
            arguments = listOf(
                navArgument("storeName") { type = NavType.StringType },
                navArgument("location") { type = NavType.StringType },
                navArgument("mapUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val storeName = backStackEntry.arguments
                ?.getString("storeName")
                ?.let { Uri.decode(it) } ?: "Tienda"

            val location = backStackEntry.arguments
                ?.getString("location")
                ?.let { Uri.decode(it) } ?: "Sin ubicación"

            val mapUrl = backStackEntry.arguments
                ?.getString("mapUrl")
                ?.let { Uri.decode(it) } ?: ""

            StoreDetailScreen(
                storeName = storeName,
                storeLocation = location,
                mapUrl = mapUrl,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
