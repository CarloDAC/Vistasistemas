package com.example.vistasistemas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vistasistemas.ui.theme.VistaSistemasTheme

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
    val roles = listOf("Trabajo social", "Ludoteca", "Medicina", "Recepcion", "Psicologia")
    val puestos = listOf("Coordinador", "Administrador", "Trabajador")

    var expandedPuesto by remember { mutableStateOf(false) }
    var expandedRol by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var puesto by remember { mutableStateOf(puestos[0]) }
    var rol by remember { mutableStateOf(roles[0]) }
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
                .weight(1f), colors = CardDefaults.cardColors(
                Color.White
            )
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Row(Modifier.fillMaxWidth().padding(10.dp),
                Arrangement.SpaceEvenly){
               Text("Formulario de registro", color = Color(128,0,128), fontSize = 40.sp,
                   fontFamily = FontFamily.Serif, lineHeight = 40.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(value = nombre,
                onValueChange = {
                    nombre = it
                },
                label = { Text("Nombre del usuario") },
                modifier = Modifier.fillMaxWidth().padding(10.dp)
                  ,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(128,0,128),
                    cursorColor = Color(0xFF1976D2)
                ))


            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(value = email,
                onValueChange = {
                   email = it
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(128,0,128),
                    cursorColor = Color(0xFF1976D2)
                ))

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
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
                    value = puesto,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona una opción") },
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
                    puestos.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                puesto = opcion
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
                    value = rol,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona una opción") },
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
                    roles.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                rol = opcion
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
                shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(modifier = Modifier,
                            shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, Color.Black)
                    ,
                        onClick = {
                            onVolver()
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