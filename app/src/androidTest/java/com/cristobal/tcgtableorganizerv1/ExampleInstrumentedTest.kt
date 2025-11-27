package com.cristobal.tcgtableorganizerv1

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, se ejecuta en un dispositivo/emulador Android.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext_packageNameIsCorrect() {
        // Contexto de la app en el dispositivo de prueba.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.cristobal.tcgtableorganizerv1", appContext.packageName)
    }

    @Test
    fun appNameString_isNotEmpty() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val appName = appContext.getString(R.string.app_name)

        assertTrue("El nombre de la app no debería estar vacío", appName.isNotBlank())
    }
}
