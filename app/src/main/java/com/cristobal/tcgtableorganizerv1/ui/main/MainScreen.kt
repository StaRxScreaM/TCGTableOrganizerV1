package com.cristobal.tcgtableorganizerv1.ui.main

import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cristobal.tcgtableorganizerv1.R
import com.cristobal.tcgtableorganizerv1.model.EventUi
import com.cristobal.tcgtableorganizerv1.ui.tables.TablesTab
import com.cristobal.tcgtableorganizerv1.ui.theme.*

/* ==========================================================
                      MODELO DE TIENDA
   ========================================================== */

data class StoreUi(
    val name: String,
    val location: String,
    val mapUrl: String
)

/* ==========================================================
                        ENUM PESTAÑAS
   ========================================================== */

enum class MainTab(val label: String) {
    HOME("INICIO"),
    EVENTS("EVENTOS"),
    TABLES("MESAS"),
    CHAT("CHAT"),
    PROFILE("PERFIL")
}

enum class HomeSection {
    PROMOS,
    STORES
}

/* ==========================================================
                     MAIN + BOTTOM BAR
   ========================================================== */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onOpenStore: (storeName: String, location: String, mapUrl: String) -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.HOME) }

    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_tcg),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .height(32.dp)
                                .padding(end = 8.dp)
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Perfil",
                        tint = colors.onPrimary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Ajustes",
                        tint = colors.onPrimary,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = colors.surface) {
                NavigationBarItem(
                    selected = selectedTab == MainTab.HOME,
                    onClick = { selectedTab = MainTab.HOME },
                    icon = { Icon(Icons.Outlined.Home, null) },
                    label = { Text(MainTab.HOME.label) }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.EVENTS,
                    onClick = { selectedTab = MainTab.EVENTS },
                    icon = { Icon(Icons.Filled.Event, null) },
                    label = { Text(MainTab.EVENTS.label) }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.TABLES,
                    onClick = { selectedTab = MainTab.TABLES },
                    icon = { Icon(Icons.Outlined.TableChart, null) },
                    label = { Text(MainTab.TABLES.label) }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.CHAT,
                    onClick = { selectedTab = MainTab.CHAT },
                    icon = { Icon(Icons.Outlined.ChatBubbleOutline, null) },
                    label = { Text(MainTab.CHAT.label) }
                )
                NavigationBarItem(
                    selected = selectedTab == MainTab.PROFILE,
                    onClick = { selectedTab = MainTab.PROFILE },
                    icon = { Icon(Icons.Outlined.Person, null) },
                    label = { Text(MainTab.PROFILE.label) }
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(colors.background)
        ) {
            when (selectedTab) {
                MainTab.HOME    -> HomeTab(onOpenStore = onOpenStore)
                MainTab.EVENTS  -> EventsTab()
                MainTab.TABLES  -> TablesTab()
                MainTab.CHAT    -> ChatTab()
                MainTab.PROFILE -> ProfileTab()
            }
        }
    }
}

/* ==========================================================
                           HOME
   ========================================================== */

@Composable
fun HomeTab(
    onOpenStore: (storeName: String, location: String, mapUrl: String) -> Unit
) {

    val colors = MaterialTheme.colorScheme
    var selectedHomeSection by rememberSaveable { mutableStateOf(HomeSection.PROMOS) }
    var showEventDetail by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "EVENTO DESTACADO DE LA SEMANA",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .background(BurgundyDark)
                    .padding(16.dp)
            ) {
                Text(
                    text = "COMMANDER NIGHT",
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "DIC 05 - ÁREA 52",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onPrimary
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { showEventDetail = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Burgundy,
                        contentColor = White
                    )
                ) {
                    Text("VER MÁS")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "TIENDAS DESTACADAS",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HomePillButton(
                text = "PROMOS",
                isActive = selectedHomeSection == HomeSection.PROMOS,
                onClick = { selectedHomeSection = HomeSection.PROMOS },
                modifier = Modifier.weight(1f)
            )
            HomePillButton(
                text = "TIENDAS",
                isActive = selectedHomeSection == HomeSection.STORES,
                onClick = { selectedHomeSection = HomeSection.STORES },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        when (selectedHomeSection) {
            HomeSection.PROMOS -> {
                Text(
                    text = "PROMOS DESTACADAS",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                Spacer(Modifier.height(8.dp))
                PromoCarousel()
            }

            HomeSection.STORES -> {
                Text(
                    text = "TIENDAS AFILIADAS",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                Spacer(Modifier.height(8.dp))
                StoresList(onOpenStore = onOpenStore)
            }
        }

        if (showEventDetail) {
            EventDetailDialog(
                onDismiss = { showEventDetail = false }
            )
        }
    }
}

/* ==========================================================
                 DIALOGO DETALLE DE EVENTO
   ========================================================== */

@Composable
fun EventDetailDialog(
    onDismiss: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        },
        title = {
            Text(
                text = "Commander Night – ÁREA 52",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // 🖼 Banner del evento (weekly_event.png en drawable)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surfaceVariant
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.weekly_event),
                        contentDescription = "Banner Commander Night",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = "DIC 05 - ÁREA 52",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface
                )

                Text(
                    text = "Todos los viernes, desde las 19:00 hasta las 03:00, la base de Área 52 abre sus compuertas para el Commander Night. " +
                            "Trae tu mazo y únete a otras personas fanáticas del Magic para una noche completa de Commander.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurface
                )

                Text(
                    text = "Por solo $2.000 por persona puedes entrar a mesas con premio para el ganador y sorteos sorpresa durante la noche. " +
                            "Además, nuestra cafetería temática tiene burgers, pizzas, snacks y néctares galácticos para mantener tu maná cargado hasta la madrugada.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onSurface
                )
            }
        }
    )
}

/* ==========================================================
                       BOTÓN PÍLDORA
   ========================================================== */

@Composable
fun HomePillButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (isActive) Burgundy else SurfaceGray
    val txt = if (isActive) White else TextPrimaryLight

    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = txt,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/* ==========================================================
                     CARRUSEL DE PROMOS
   ========================================================== */

@Composable
fun PromoCarousel() {

    val colors = MaterialTheme.colorScheme

    val promoImages = listOf(
        R.drawable.promo_1,
        R.drawable.promo_2,
        R.drawable.promo_3,
        R.drawable.promo_4,
        R.drawable.promo_5
    )

    if (promoImages.isEmpty()) {
        Text("Sin promos disponibles por ahora", color = colors.onSurfaceVariant)
        return
    }

    var currentIndex by rememberSaveable { mutableStateOf(0) }
    var showFullscreen by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(promoImages) {
        while (promoImages.isNotEmpty()) {
            delay(4000L)
            currentIndex = (currentIndex + 1) % promoImages.size
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { showFullscreen = true },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Image(
                painter = painterResource(id = promoImages[currentIndex]),
                contentDescription = "Promo ${currentIndex + 1}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = {
                    currentIndex =
                        if (currentIndex == 0) promoImages.lastIndex else currentIndex - 1
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Anterior",
                    tint = Burgundy
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                promoImages.indices.forEach { index ->
                    val isSelected = index == currentIndex
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 10.dp else 6.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(
                                if (isSelected)
                                    Burgundy
                                else
                                    colors.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                    )
                }
            }

            IconButton(
                onClick = {
                    currentIndex =
                        if (currentIndex == promoImages.lastIndex) 0 else currentIndex + 1
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Siguiente",
                    tint = Burgundy
                )
            }
        }
    }

    /* FULLSCREEN */
    if (showFullscreen) {
        Dialog(onDismissRequest = { showFullscreen = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCC000000)),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconButton(onClick = { showFullscreen = false }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Cerrar",
                                tint = White
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black)
                    ) {
                        Image(
                            painter = painterResource(id = promoImages[currentIndex]),
                            contentDescription = "Promo ${currentIndex + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        TextButton(
                            onClick = {
                                currentIndex =
                                    if (currentIndex == 0) promoImages.lastIndex else currentIndex - 1
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Anterior",
                                tint = White
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Anterior", color = White)
                        }

                        TextButton(
                            onClick = {
                                currentIndex =
                                    if (currentIndex == promoImages.lastIndex) 0 else currentIndex + 1
                            }
                        ) {
                            Text("Siguiente", color = White)
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Siguiente",
                                tint = White
                            )
                        }
                    }
                }
            }
        }
    }
}

/* ==========================================================
                  LISTA DE TIENDAS AFILIADAS
   ========================================================== */

@Composable
fun StoresList(
    onOpenStore: (storeName: String, location: String, mapUrl: String) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    // 🔥 TIENDAS TCG EN SANTIAGO
    val stores = listOf(
        StoreUi(
            name = "Área 52 - Santiago",
            location = "Av. Sta. Isabel 962, 7501307 Providencia, Región Metropolitana",
            mapUrl = "https://maps.app.goo.gl/u6aR93WcGybEaZt79"
        ),
        StoreUi(
            name = "Top Deck - Providencia",
            location = "José Manuel Infante 1251, Providencia, Región Metropolitana",
            mapUrl = "https://maps.app.goo.gl/p8JBds8CWd9XKdZ9A"
        ),
        StoreUi(
            name = "Magicsur Chile",
            location = "Seminario 507, Providencia, Región Metropolitana",
            mapUrl = "https://maps.app.goo.gl/ZHLn45jE6YzEDWWH7"
        ),
        StoreUi(
            name = "Entre Juegos - Providencia",
            location = "Nueva de Lyon 61, Providencia, Región Metropolitana",
            mapUrl = "https://maps.app.goo.gl/kNf9DJFQ4TXMgLYP9"
        ),
        StoreUi(
            name = "Magic4ever",
            location = "Moneda 840, Santiago, Región Metropolitana",
            mapUrl = "https://maps.app.goo.gl/4SQqpFEAGxQwqA1fA"
        )
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        stores.forEach { store ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Columna de textos (nombre + dirección)
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = store.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.onSurface
                        )
                        Text(
                            text = store.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.onSurfaceVariant,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Botón "Ver"
                    Text(
                        text = "Ver",
                        style = MaterialTheme.typography.labelMedium,
                        color = Burgundy,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onOpenStore(store.name, store.location, store.mapUrl)
                        }
                    )
                }
            }
        }
    }
}

/* ==========================================================
                         EVENTOS
   ========================================================== */

@Composable
fun EventsTab() {

    val colors = MaterialTheme.colorScheme

    val events = listOf(
        EventUi("ENE", "10", "10:00 a.m.", "Commander Casual", "10:00 a.m."),
        EventUi("ENE", "10", "11:00 a.m.", "Commander Casual", "11:00 a.m."),
        EventUi("ENE", "11", "1:00 p.m.", "Commander Casual", "1:00 p.m.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "EVENTOS",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground
        )
        Text(
            text = "2026",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurfaceVariant
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .
                    background(Burgundy),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Eventos",
                    color = White,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(colors.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tiendas",
                    color = colors.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(events) { event ->
                EventCard(event)
            }
        }
    }
}

@Composable
fun EventCard(event: EventUi) {

    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(event.dayShort, color = colors.onSurfaceVariant)
                Text(
                    event.dayNumber,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(event.time, color = colors.onSurfaceVariant)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = event.storeLabelTime,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = colors.onSurface
                )
            }
        }
    }
}

/* ==========================================================
                     CHAT & PERFIL
   ========================================================== */

@Composable
fun ChatTab() {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Chat en desarrollo", color = colors.onSurfaceVariant)
    }
}

@Composable
fun ProfileTab() {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Perfil en desarrollo", color = colors.onSurfaceVariant)
    }
}

