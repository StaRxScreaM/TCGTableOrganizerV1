package com.cristobal.tcgtableorganizerv1.model

/**
 * Modelo de evento para la vista de EVENTOS.
 */
data class EventUi(
    val dayShort: String,      // "ENE"
    val dayNumber: String,     // "10"
    val time: String,          // "10:00 a.m."
    val title: String,         // "Commander Casual"
    val storeLabelTime: String // Texto adicional (hora / tienda)
)
