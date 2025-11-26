package com.cristobal.tcgtableorganizerv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cristobal.tcgtableorganizerv1.navigation.AppNavHost
import com.cristobal.tcgtableorganizerv1.ui.theme.TCGTableOrganizerV1Theme

/**
 * Activity principal de la app.
 * Aquí se inyecta el tema y el árbol de navegación con Compose.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TCGTableOrganizerV1Theme {
                AppNavHost()
            }
        }
    }
}
