package com.cristobal.tcgtableorganizerv1.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cristobal.tcgtableorganizerv1.R
import com.cristobal.tcgtableorganizerv1.ui.theme.Burgundy
import com.cristobal.tcgtableorganizerv1.ui.theme.White

/**
 * Pantalla de inicio de sesión del staff de TCG Table Organizer.
 *
 * @param onLoginSuccess Callback que se ejecuta cuando el login es exitoso.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var keepLogged by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    // Credenciales de ejemplo para la actividad
    val adminEmail = "admin@tienda.cl"
    val adminPass = "admin123"

    fun login() {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        when {
            !trimmedEmail.contains("@") -> {
                error = "Correo inválido"
            }
            trimmedPassword.length < 4 -> {
                error = "La contraseña es muy corta"
            }
            trimmedEmail == adminEmail && trimmedPassword == adminPass -> {
                error = null
                Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }
            else -> {
                error = "Credenciales incorrectas"
            }
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF111111))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Logo principal de la app
                Image(
                    painter = painterResource(id = R.drawable.logo_tcg_1),
                    contentDescription = "Logo TCG Table Organizer",
                    modifier = Modifier
                        .height(220.dp)
                        .padding(bottom = 8.dp)
                )

                // Nombre de la app
                Text(
                    text = "TCG TABLE ORGANIZER",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = White,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                // Campo correo
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(Modifier.height(12.dp))

                // Campo contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Contraseña") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(Modifier.height(8.dp))

                // Mantener sesión iniciada
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = keepLogged,
                        onCheckedChange = { keepLogged = it }
                    )
                    Text(
                        text = "Mantener sesión iniciada",
                        color = White
                    )
                }

                // Mensaje de error
                if (error != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = error!!,
                        color = Color(0xFFFFB3B3)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Botón ingresar
                Button(
                    onClick = { login() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Burgundy,
                        contentColor = White
                    )
                ) {
                    Text("Ingresar")
                }
            }
        }
    }
}


