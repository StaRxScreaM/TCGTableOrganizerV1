package com.cristobal.tcgtableorganizerv1.ui.tables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cristobal.tcgtableorganizerv1.model.TableStatus
import com.cristobal.tcgtableorganizerv1.model.TableUi
import com.cristobal.tcgtableorganizerv1.ui.theme.Burgundy
import com.cristobal.tcgtableorganizerv1.ui.theme.SecondaryGray
import com.cristobal.tcgtableorganizerv1.ui.theme.SurfaceGray
import com.cristobal.tcgtableorganizerv1.ui.theme.White
import kotlin.math.ceil

/**
 * Pestaña de MESAS:
 * - Máx. 40 mesas
 * - 10 mesas por página
 * - Estados: EMPTY, SEARCHING, WAITING, FULL
 * - Tap en la tarjeta abre diálogo de edición.
 */
@Composable
fun TablesTab() {

    // Lista de mesas manejada en memoria
    val tables = remember {
        mutableStateListOf<TableUi>().apply {
            // Ejemplo inicial de 6 mesas
            addAll(
                (1..6).map { index ->
                    TableUi(
                        id = index,
                        label = "MESA $index",
                        game = "MTG Commander",
                        status = if (index % 2 == 0) TableStatus.FULL else TableStatus.EMPTY,
                        playerSlots = 4,
                        reserved = false
                    )
                }
            )
        }
    }

    // Página actual (0-based)
    var currentPage by rememberSaveable { mutableStateOf(0) }

    // Mesa seleccionada para edición
    var selectedTable by remember { mutableStateOf<TableUi?>(null) }

    val pageSize = 10
    val totalPages = remember(tables.size) {
        if (tables.isEmpty()) 1 else ceil(tables.size / pageSize.toFloat()).toInt()
    }
    val pageIndex = currentPage.coerceIn(0, totalPages - 1)

    val startIndex = pageIndex * pageSize
    val pageTables = tables.drop(startIndex).take(pageSize)

    // Scroll para la zona de mesas
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Header + acciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = "MESAS",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${tables.size} / 40 mesas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryGray
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {
                        if (tables.size < 40) {
                            val nextId = (tables.maxOfOrNull { it.id } ?: 0) + 1
                            tables.add(
                                TableUi(
                                    id = nextId,
                                    label = "MESA $nextId",
                                    game = "MTG Commander",
                                    status = TableStatus.EMPTY,
                                    playerSlots = 4,
                                    reserved = false
                                )
                            )
                        }
                    },
                    enabled = tables.size < 40
                ) {
                    Text("Agregar mesa")
                }

                Spacer(Modifier.height(4.dp))

                TextButton(
                    onClick = {
                        if (tables.isNotEmpty()) {
                            tables.removeAt(tables.lastIndex)
                        }
                    },
                    enabled = tables.isNotEmpty()
                ) {
                    Text("Eliminar última")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🟢 Zona SCROLLEABLE de las mesas
        Column(
            modifier = Modifier
                .weight(1f)                // ocupa el espacio disponible
                .verticalScroll(scrollState) // permite scroll vertical
        ) {
            if (pageTables.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay mesas creadas", color = SecondaryGray)
                }
            } else {
                for (rowIndex in pageTables.indices step 2) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        val first = pageTables[rowIndex]
                        MesaCard(
                            table = first,
                            modifier = Modifier.weight(1f),
                            onClick = { selectedTable = first }
                        )

                        if (rowIndex + 1 < pageTables.size) {
                            val second = pageTables[rowIndex + 1]
                            MesaCard(
                                table = second,
                                modifier = Modifier.weight(1f),
                                onClick = { selectedTable = second }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Paginación
        if (tables.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Página ${pageIndex + 1} de $totalPages",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { if (currentPage > 0) currentPage-- },
                        enabled = currentPage > 0
                    ) {
                        Text("Anterior")
                    }

                    OutlinedButton(
                        onClick = { if (currentPage < totalPages - 1) currentPage++ },
                        enabled = currentPage < totalPages - 1
                    ) {
                        Text("Siguiente")
                    }
                }
            }
        }
    }

    // Diálogo de edición de mesa
    selectedTable?.let { table ->
        TableDetailDialog(
            table = table,
            onDismiss = { selectedTable = null },
            onSave = { updated ->
                val index = tables.indexOfFirst { it.id == updated.id }
                if (index != -1) {
                    tables[index] = updated
                }
                selectedTable = null
            },
            onDelete = {
                val index = tables.indexOfFirst { it.id == table.id }
                if (index != -1) {
                    tables.removeAt(index)
                }
                selectedTable = null
            }
        )
    }
}

/**
 * Tarjeta individual de cada mesa en la grilla.
 */
@Composable
fun MesaCard(
    table: TableUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val (bgChip, txtChip) = when (table.status) {
        TableStatus.EMPTY     -> SurfaceGray to Color.DarkGray
        TableStatus.SEARCHING -> Color(0xFF1976D2) to White
        TableStatus.WAITING   -> Color(0xFFFFA000) to Color.Black
        TableStatus.FULL      -> Burgundy to White
    }

    Card(
        modifier = modifier
            .height(140.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = table.label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = table.game,
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryGray
                )
                if (table.reserved) {
                    Text(
                        text = "RESERVADA",
                        style = MaterialTheme.typography.bodySmall,
                        color = Burgundy
                    )
                }
            }

            // Chip de estado
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(bgChip)
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = table.status.name,
                    color = txtChip,
                    fontWeight = FontWeight.Bold
                )
            }

            // Puntos de jugadores (2 a 4)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(table.playerSlots) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3A3A3A))
                    )
                }
            }
        }
    }
}

/**
 * Diálogo para editar una mesa:
 * - juego
 * - número de jugadores (2–4)
 * - estado (cualquier estado)
 * - reserva
 */
@Composable
fun TableDetailDialog(
    table: TableUi,
    onDismiss: () -> Unit,
    onSave: (TableUi) -> Unit,
    onDelete: () -> Unit
) {
    var gameText by rememberSaveable { mutableStateOf(table.game) }
    var playerSlots by rememberSaveable { mutableStateOf(table.playerSlots.coerceIn(2, 4)) }
    var status by rememberSaveable { mutableStateOf(table.status) }
    var reserved by rememberSaveable { mutableStateOf(table.reserved) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar ${table.label}") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Juego que se está jugando
                OutlinedTextField(
                    value = gameText,
                    onValueChange = { gameText = it },
                    label = { Text("Juego (MTG, Pokémon, Star Wars…)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Número de jugadores (2–4)
                Column {
                    Text(
                        text = "Número de jugadores",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (2..4).forEach { slots ->
                            val isSelected = slots == playerSlots
                            Button(
                                onClick = { playerSlots = slots },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Burgundy else SurfaceGray,
                                    contentColor = if (isSelected) White else Color(0xFF333333)
                                )
                            ) {
                                Text("$slots")
                            }
                        }
                    }
                }

                // Estado: EMPTY / SEARCHING / WAITING / FULL en grilla 2x2
                Column {
                    Text(
                        text = "Estado de la mesa",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(8.dp))

                    val allStatuses = TableStatus.values()

                    // Fila 1: primeros dos estados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allStatuses.take(2).forEach { s ->
                            val isSelected = s == status
                            Button(
                                onClick = { status = s },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Burgundy else SurfaceGray,
                                    contentColor = if (isSelected) White else Color(0xFF333333)
                                )
                            ) {
                                Text(s.name)
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // Fila 2: siguientes dos estados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allStatuses.drop(2).forEach { s ->
                            val isSelected = s == status
                            Button(
                                onClick = { status = s },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Burgundy else SurfaceGray,
                                    contentColor = if (isSelected) White else Color(0xFF333333)
                                )
                            ) {
                                Text(s.name)
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "WAITING se usa para mesas que esperan jugadores (máx. 15 minutos según las reglas de la tienda).",
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryGray
                    )
                }

                // Reservada
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = reserved,
                        onCheckedChange = { reserved = it }
                    )
                    Text("Mesa reservada")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        table.copy(
                            game = gameText.ifBlank { "Sin juego asignado" },
                            playerSlots = playerSlots.coerceIn(2, 4),
                            status = status,
                            reserved = reserved
                        )
                    )
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = onDelete) {
                    Text("Eliminar")
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        }
    )
}

