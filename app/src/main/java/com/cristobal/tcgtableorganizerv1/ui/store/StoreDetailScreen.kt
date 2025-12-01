package com.cristobal.tcgtableorganizerv1.ui.store

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cristobal.tcgtableorganizerv1.R
import com.cristobal.tcgtableorganizerv1.ui.theme.Burgundy
import com.cristobal.tcgtableorganizerv1.ui.theme.White

@Composable
fun StoreDetailScreen(
    storeName: String,
    storeLocation: String,
    mapUrl: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // 🖼 Imagen principal según tienda (front_)
    val storeImage = when {
        storeName.contains("Área 52", ignoreCase = true) ->
            R.drawable.front_area52

        storeName.contains("Top Deck", ignoreCase = true) ->
            R.drawable.front_topdeck

        storeName.contains("Magicsur", ignoreCase = true) ->
            R.drawable.front_magicsur

        storeName.contains("Entre Juegos", ignoreCase = true) ->
            R.drawable.front_entrejuegos

        storeName.contains("Magic4ever", ignoreCase = true) ->
            R.drawable.front_magic4ever

        else -> null
    }

    // 🏷 Oferta destacada según tienda (oferta_)
    val offerImage = when {
        storeName.contains("Área 52", ignoreCase = true) ->
            R.drawable.oferta_area52

        storeName.contains("Top Deck", ignoreCase = true) ->
            R.drawable.oferta_topdeck

        storeName.contains("Magicsur", ignoreCase = true) ->
            R.drawable.oferta_magicsur

        storeName.contains("Entre Juegos", ignoreCase = true) ->
            R.drawable.oferta_entrejuegos

        storeName.contains("Magic4ever", ignoreCase = true) ->
            R.drawable.oferta_m4e

        else -> null
    }

    // 📝 Descripción breve por tienda
    val storeDescription = when {
        storeName.contains("Área 52", ignoreCase = true) ->
            "Cafetería temática, juegos de mesa, torneos TCG semanales, Star Wars Unlimited y mucho más."

        storeName.contains("Top Deck", ignoreCase = true) ->
            "Especialistas en Commander, venta de singles, eventos competitivos y comunidad activa."

        storeName.contains("Magicsur", ignoreCase = true) ->
            "Tienda clásica de Magic con productos sellados, usados y comunidad competitiva."

        storeName.contains("Entre Juegos", ignoreCase = true) ->
            "Juegos de mesa, TCG, partidas demostrativas y ambiente familiar en Nueva de Lyon."

        storeName.contains("Magic4ever", ignoreCase = true) ->
            "MTG, Yu-Gi-Oh!, accesorios y eventos casuales en pleno centro de Santiago."

        else -> "Espacio de juegos de mesa, TCG y comunidad de jugadores."
    }

    fun openMaps() {
        val primaryUri = mapUrl.takeIf { it.isNotBlank() }?.let { Uri.parse(it) }
        val fallbackUri = Uri.parse("geo:0,0?q=${Uri.encode(storeLocation)}")

        val mapsIntent = Intent(Intent.ACTION_VIEW, primaryUri ?: fallbackUri).apply {
            setPackage("com.google.android.apps.maps")
        }

        try {
            context.startActivity(mapsIntent)
        } catch (e: ActivityNotFoundException) {
            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, primaryUri ?: fallbackUri))
            } catch (e2: Exception) {
                Toast.makeText(
                    context,
                    "No se pudo abrir Google Maps en este dispositivo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {

        // 🔺 HERO: imagen usando todo el ancho y ~1/3 de la pantalla
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 3)
        ) {
            if (storeImage != null) {
                Image(
                    painter = painterResource(id = storeImage),
                    contentDescription = "Imagen de la tienda",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.surfaceVariant)
                )
            }

            // Degradado para que el texto se lea bien
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xCC000000)
                            )
                        )
                    )
            )

            // Texto SOBRE la imagen, alineado abajo
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = storeName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF5F5F5)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = storeDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE0E0E0)
                )
            }
        }

        // 🔻 Contenido restante
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Dirección debajo de la imagen
            Text(
                text = "Dirección:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.onBackground
            )
            Text(
                text = storeLocation,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurface
            )

            // 🎁 Oferta destacada
            Text(
                text = "Oferta destacada",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.onBackground
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant)
            ) {
                if (offerImage != null) {
                    Image(
                        painter = painterResource(id = offerImage),
                        contentDescription = "Oferta destacada de la tienda",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colors.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aquí puedes mostrar una oferta destacada.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Maps (más pequeño)
            Button(
                onClick = { openMaps() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Burgundy,
                    contentColor = White
                )
            ) {
                Text("Abrir en Google Maps")
            }

            // Botón Volver (mismo tamaño)
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.surface,
                    contentColor = colors.onSurface
                )
            ) {
                Text("Volver")
            }
        }
    }
}
