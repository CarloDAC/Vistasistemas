package com.example.vistasistemas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vistasistemas.ui.theme.VistaSistemasTheme
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback // ← Agregar este import
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VistaSistemasTheme {
                SistemasPrincipal()
            }
        }
    }
}


@Composable
fun SistemasPrincipal() {
    val rolesDisplayMap = mapOf(
        "trabajo_social" to "Trabajo social",
        "ludoteca" to "Ludoteca",
        "medico" to "Medicina",
        "recepcion" to "Recepcion",
        "psicologia" to "Psicologia",
        "sistemas" to "Sistemas"
    )

    val puestosDisplayMap = mapOf(
        "coordinador" to "Coordinador",
        "administrador" to "Administrador",
        "trabajador" to "Trabajador"
    )

    val context = LocalContext.current


    var usuarios by remember { mutableStateOf(listOf<Usuario>()) }

    LaunchedEffect(Unit) {
        RetrofitClient.api.obtenerUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if(response.isSuccessful){
                    usuarios = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Button(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .shadow(
                            20.dp, shape = RoundedCornerShape(40.dp),
                            ambientColor = Color.Black, spotColor = Color.Black
                        ),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {}
                ) {
                    Text("Cerrar sesion", color = Color(128, 0, 128))
                }
            }

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val useScroll = maxWidth < 600.dp

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .background(Color.White)
                        .then(
                            if (useScroll) Modifier.horizontalScroll(rememberScrollState())
                            else Modifier
                        )
                        .border(5.dp, shape = RectangleShape, color = Color.Black)
                ) {
                    Row(
                        modifier = Modifier
                            .then(if (!useScroll) Modifier.fillMaxWidth() else Modifier)
                            .background(Color.White)
                            .padding(8.dp),
                        horizontalArrangement = if (!useScroll) Arrangement.SpaceBetween else Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Nombre",
                            color = Color(128, 0, 128),
                            modifier = Modifier
                                .padding(8.dp)
                                .then(
                                    if (useScroll) Modifier.width(200.dp)
                                    else Modifier.weight(2f)
                                )
                        )
                        Text(
                            "Email",
                            color = Color(128, 0, 128),
                            modifier = Modifier
                                .padding(8.dp)
                                .then(
                                    if (useScroll) Modifier.width(250.dp)
                                    else Modifier.weight(2.5f)
                                )
                        )
                        Text(
                            "Puesto",
                            color = Color(128, 0, 128),
                            modifier = Modifier
                                .padding(8.dp)
                                .then(
                                    if (useScroll) Modifier.width(150.dp)
                                    else Modifier.weight(1.5f)
                                )
                        )
                        Text(
                            "Rol",
                            color = Color(128, 0, 128),
                            modifier = Modifier
                                .padding(8.dp)
                                .then(
                                    if (useScroll) Modifier.width(150.dp)
                                    else Modifier.weight(1.5f)
                                )
                        )
                        if (useScroll) {
                            Text(
                                "",
                                color = Color(128, 0, 128),
                                modifier = Modifier
                                    .padding(8.dp).width(120.dp)
                            )
                        } else {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize().background(Color.White)
                    ) {
                        items(usuarios.size) { index ->
                            val usuario = usuarios[index]

                            // Mapeo para mostrar valores amigables
                            val puestoDisplay = when(usuario.puesto.lowercase()) {
                                "coordinador" -> "Coordinador"
                                "administrador" -> "Administrador"
                                "trabajador" -> "Trabajador"
                                else -> usuario.puesto
                            }

                            val rolDisplay = when(usuario.rol.lowercase()) {
                                "trabajo_social" -> "Trabajo social"
                                "ludoteca" -> "Ludoteca"
                                "medico" -> "Medicina"
                                "recepcion" -> "Recepcion"
                                "psicologia" -> "Psicologia"
                                "sistemas" -> "Sistemas"
                                else -> usuario.rol
                            }

                            Row(
                                modifier = Modifier
                                    .then(if (!useScroll) Modifier.fillMaxWidth() else Modifier)
                                    .padding(horizontal = 15.dp, vertical = 4.dp)
                                    .background(Color(230,230,250), shape = RoundedCornerShape(10.dp)),
                                horizontalArrangement = if (!useScroll) Arrangement.SpaceBetween else Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    usuario.nombre,
                                    color = Color(128, 0, 128),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .then(
                                            if (useScroll) Modifier.width(200.dp)
                                            else Modifier.weight(2f)
                                        )
                                )
                                Text(
                                    usuario.email,
                                    color = Color(128, 0, 128),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .then(
                                            if (useScroll) Modifier.width(250.dp)
                                            else Modifier.weight(2.5f)
                                        )
                                )
                                Text(
                                    puestoDisplay,
                                    color = Color(128, 0, 128),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .then(
                                            if (useScroll) Modifier.width(150.dp)
                                            else Modifier.weight(1.5f)
                                        )
                                )
                                Text(
                                    rolDisplay,
                                    color = Color(128, 0, 128),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .then(
                                            if (useScroll) Modifier.width(150.dp)
                                            else Modifier.weight(1.5f)
                                        )
                                )
                                if (useScroll) {
                                    IconButton(modifier = Modifier.padding(8.dp), onClick = {}) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                                    }
                                    IconButton(modifier = Modifier.padding(8.dp), onClick = {}) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(onClick = {}) {
                                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                                        }
                                        IconButton(onClick = {}) {
                                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, FormularioPantalla::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(50.dp)
                    ),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text("Agregar Usuario", color = Color(128, 0, 128))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VistaSistemasTheme {
        SistemasPrincipal()
    }
}