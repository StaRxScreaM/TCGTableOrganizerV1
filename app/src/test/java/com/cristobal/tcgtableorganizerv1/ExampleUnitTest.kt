package com.cristobal.tcgtableorganizerv1

import com.cristobal.tcgtableorganizerv1.model.EventUi
import com.cristobal.tcgtableorganizerv1.model.TableStatus
import com.cristobal.tcgtableorganizerv1.model.TableUi
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun eventUi_holdsCorrectData() {
        val event = EventUi(
            dayShort = "ENE",
            dayNumber = "10",
            time = "10:00",
            title = "Commander Casual",
            storeLabelTime = "Tienda X - 10:00"
        )

        assertEquals("ENE", event.dayShort)
        assertEquals("10", event.dayNumber)
        assertEquals("10:00", event.time)
        assertEquals("Commander Casual", event.title)
        assertEquals("Tienda X - 10:00", event.storeLabelTime)
    }

    @Test
    fun tableUi_createsValidTable() {
        val table = TableUi(
            id = 1,
            label = "MESA 1",
            game = "MTG Commander",
            status = TableStatus.SEARCHING,
            playerSlots = 4,
            reserved = true
        )

        assertEquals(1, table.id)
        assertEquals("MESA 1", table.label)
        assertEquals("MTG Commander", table.game)
        assertEquals(TableStatus.SEARCHING, table.status)
        assertEquals(4, table.playerSlots)
        assertTrue(table.reserved)
    }
}
