package com.cristobal.tcgtableorganizerv1.model

enum class TableStatus { EMPTY, FULL }

data class TableUi(
    val name: String,
    val status: TableStatus
)
