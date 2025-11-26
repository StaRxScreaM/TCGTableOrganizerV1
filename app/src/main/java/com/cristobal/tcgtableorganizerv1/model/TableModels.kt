package com.cristobal.tcgtableorganizerv1.model

/**
 * Estados posibles de una mesa visible para jugadores y staff.
 */
enum class TableStatus {
    EMPTY,      // Mesa vacía
    SEARCHING,  // Buscando jugadores
    WAITING,    // A la espera (ej: 15 minutos)
    FULL        // Mesa llena
}

/**
 * Modelo de mesa para la vista de MESAS.
 *
 * @param id          Identificador interno (1..40)
 * @param label       Nombre visible de la mesa (ej: "MESA 1")
 * @param game        Juego que se está jugando (MTG, Pokémon, Star Wars, etc.)
 * @param status      Estado actual de la mesa
 * @param playerSlots Número de jugadores configurado para esta mesa (2 a 4)
 * @param reserved    Indica si la mesa está reservada
 */
data class TableUi(
    val id: Int,
    val label: String,
    val game: String,
    val status: TableStatus,
    val playerSlots: Int,
    val reserved: Boolean
)
