📱 TCG Table Organizer — Staff App

Aplicación móvil desarrollada con Android Studio + Kotlin + Jetpack Compose, diseñada para la gestión interna de tiendas TCG (Trading Card Games).
Permite organizar eventos, administrar mesas y centralizar información relevante para el personal administrativo.

El objetivo principal es ofrecer a los dueños y trabajadores una herramienta rápida, visual y eficiente, optimizada para el flujo real de una tienda TCG que opera con formatos casuales como Commander.

✨ Características Principales
🔐 1. Login Exclusivo para Administradores

Validación de correo y contraseña.

Opción “Mantener sesión iniciada”.

Mensajes de error claros.

Redirección segura al panel principal.

🏠 2. Dashboard (Home del Staff)

Evento destacado de la semana.

Acceso rápido a “Promos” o “Tiendas”.

Noticias o avisos de Wizards.

🗓 3. Vista de Eventos

Lista ordenada de fechas, horas y tiendas.

Diseño moderno con Material 3.

Filtros básicos (Eventos / Tiendas).

Tarjetas personalizadas similares a apps oficiales TCG.

🪑 4. Gestión de Mesas

Mapa visual simple y entendible.

Estado en vivo:

🟢 EMPTY

🔴 FULL

Fichas con:

Nombre de mesa

Estado actual

Slots de jugador representados visualmente

Futuro: añadir / eliminar mesas dinámicamente.

💬 5. Chat Interno (WIP)

Diseñado como lobby de administración para comunicación entre personal.

👤 6. Vista Perfil

Placeholder para futuras opciones del trabajador:

Datos personales

Configuración

Cerrar sesión
(y más).

🎨 Diseño & UI

100% Jetpack Compose.

Material Design 3.

Tema claro/oscuro automático.

Barras de navegación modernas.

Íconos extendidos (Material Icons Extended).

Logo propio integrado en TopBar y Login.

🧩 Tecnologías Utilizadas
Frontend

Kotlin

Jetpack Compose

Material 3

Compose BOM

Navigation Compose

State Management: remember, rememberSaveable, mutableStateOf

Testing

JUnit

Instrumented Tests (AndroidTest)

Compatibilidad

minSdk = 24

targetSdk = 34 / 36

Gradle Kotlin DSL (AGP 8.1+)

📂 Arquitectura del Proyecto
TCGTableOrganizerV1/
│── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/cristobal/tcgtableorganizerv1/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── navigation/       ← AppNavHost, rutas
│   │   │   │   ├── ui/
│   │   │   │   │   ├── login/        ← LoginScreen
│   │   │   │   │   ├── main/         ← Home, Events, Tables, Chat, Profile
│   │   │   │   │   ├── theme/        ← Colors, Theme, Typography
│   │   │   │   ├── model/            ← EventUi, TableUi
│   │   │   ├── res/
│   │   │       ├── drawable/         ← Logos, íconos
│   │   │       ├── values/           ← strings, themes XML
│   │   │       ├── mipmap/           ← App icons
│   ├── build.gradle.kts
│── settings.gradle.kts
└── README.md

🚀 Características en Detalle
🔐 Login

✔ Validación
✔ Mensajes de error
✔ Composable separado
✔ Redirección segura con Navigation Compose

🏠 Home (Dashboard del Staff)

✔ Evento destacado
✔ Estilo “card” profesional
✔ Pills (botones redondeados)
✔ Diseño limpio y escalable

🗓 Eventos

✔ Lista dinámica
✔ Cards con:

Día

Hora

Título del evento

Ícono de formato (SportsEsports)

Nombre de tienda + horario
✔ Separadores visuales
✔ Encabezado por año

🪑 Mesas

✔ Grid responsivo
✔ Cada mesa muestra:

Nombre

Estado

Chip visual (EMPTY/FULL)

Slots de jugador
✔ Preparada para evolucionar hacia datos reales

💬 Chat (Placeholder WIP)

Estructura lista

Pestaña integrada en BottomBar

Listo para implementar sockets o Firebase

👤 Perfil

Pantalla base preparada para configuraciones futuras.

🗺 Navegación

La app utiliza un árbol de navegación profesional con:

Nivel 1

AppNavHost

Login

Main

Nivel 2 (dentro de Main)

BottomBar con 5 módulos:

Home

Eventos

Mesas

Chat

Perfil

Todo usando:

NavHost()
composable()
rememberNavController()

📸 Capturas

Repositorio:
https://github.com/StaRxScreaM/TCGTableOrganizerV1/tree/master/app/ScreenShots

🧪 Testing
✔ Unit Test

app/src/test/...

Verificación de lógica base.

✔ Instrumented Test

app/src/androidTest/...

Validación de packageName.

🛠 Cómo Ejecutar el Proyecto
git clone https://github.com/StaRxScreaM/TCGTableOrganizerV1


Abrir en Android Studio

Sincronizar Gradle

Ejecutar en emulador o dispositivo físico

🗺 Roadmap (Futuras mejoras)
🔮 Funcionalidades avanzadas

Chat interno con texto + imágenes

Gestión dinámica de mesas

Panel de estadísticas

Base de datos local con Room

Login real con Firebase Auth o Supabase

Integración con API para eventos reales

📄 Licencia

MIT License

👤 Autor

Cristóbal Tapia (StaRxScreaM)
Proyecto desarrollado para Bootcamp UNAB 2025.
