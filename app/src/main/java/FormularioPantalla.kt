package com.example.vistasistemas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vistasistemas.ui.theme.VistaSistemasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormularioPantalla : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VistaSistemasTheme {
                Formulario(onVolver = { finish() })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(onVolver: () -> Unit){
    // Valores que se muestran al usuario
    val rolesDisplay = listOf("Trabajo social", "Ludoteca", "Medicina", "Recepcion", "Psicologia", "Sistemas")
    val puestosDisplay = listOf("Coordinador", "Administrador", "Trabajador")

    // Mapeo a los valores que espera Laravel
    val rolesMap = mapOf(
        "Trabajo social" to "trabajo_social",
        "Ludoteca" to "ludoteca",
        "Medicina" to "medico",
        "Recepcion" to "recepcion",
        "Psicologia" to "psicologia",
        "Sistemas" to "sistemas"
    )

    val puestosMap = mapOf(
        "Coordinador" to "coordinador",
        "Administrador" to "administrador",
        "Trabajador" to "trabajador"
    )

    var expandedPuesto by remember { mutableStateOf(false) }
    var expandedRol by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var puestoDisplay by remember { mutableStateOf(puestosDisplay[0]) }
    var rolDisplay by remember { mutableStateOf(rolesDisplay[0]) }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.5f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    20.dp,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .weight(1f),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                Modifier.fillMaxWidth().padding(10.dp),
                Arrangement.SpaceEvenly
            ){
                Text(
                    "Formulario de registro",
                    color = Color(128,0,128),
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 40.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del usuario") },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(128,0,128),
                    cursorColor = Color(0xFF1976D2)
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(128,0,128),
                    cursorColor = Color(0xFF1976D2)
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(128,0,128),
                    cursorColor = Color(0xFF1976D2)
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            ExposedDropdownMenuBox(
                expanded = expandedPuesto,
                onExpandedChange = { expandedPuesto = !expandedPuesto }
            ) {
                OutlinedTextField(
                    value = puestoDisplay,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona un puesto") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(128,0,128),
                        cursorColor = Color(0xFF1976D2)
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPuesto)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth().padding(10.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedPuesto,
                    onDismissRequest = { expandedPuesto = false }
                ) {
                    puestosDisplay.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                puestoDisplay = opcion
                                expandedPuesto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            ExposedDropdownMenuBox(
                expanded = expandedRol,
                onExpandedChange = { expandedRol = !expandedRol }
            ) {
                OutlinedTextField(
                    value = rolDisplay,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona un rol") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(128,0,128),
                        cursorColor = Color(0xFF1976D2)
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRol)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth().padding(10.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedRol,
                    onDismissRequest = { expandedRol = false }
                ) {
                    rolesDisplay.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                rolDisplay = opcion
                                expandedRol = false
                            }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, Color.Black),
                        onClick = {
                            // Convertir los valores mostrados a los que espera Laravel
                            val nuevoUsuario = Usuario(
                                nombre = nombre,
                                email = email,
                                puesto = puestosMap[puestoDisplay] ?: "trabajador",
                                rol = rolesMap[rolDisplay] ?: "recepcion",
                                password = password
                            )

                            RetrofitClient.api.crearUsuario(nuevoUsuario).enqueue(object : Callback<Usuario> {
                                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                                    if(response.isSuccessful) {
                                        onVolver()
                                    }
                                }

                                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                                    t.printStackTrace()
                                }
                            })
                        }
                    ) {
                        Text("Agregar Usuario", color = Color(128, 0, 128))
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormularioPreview() {
    VistaSistemasTheme {
        Formulario(onVolver = {})
    }
}