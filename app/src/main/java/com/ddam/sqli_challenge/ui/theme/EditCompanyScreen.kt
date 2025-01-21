package com.ddam.sqli_challenge.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ddam.sqli_challenge.model.Empresa
import androidx.compose.material3.OutlinedTextField


@Composable
fun EditCompanyScreen(
    empresa: Empresa,
    onUpdateCompany: (Empresa) -> Unit,
    onCancel: () -> Unit
) {
    var nombre = remember { mutableStateOf(empresa.nombre) }
    var url = remember { mutableStateOf(empresa.url) }
    var telefono = remember { mutableStateOf(empresa.telefono) }
    var email = remember { mutableStateOf(empresa.email) }
    var productosServicios = remember { mutableStateOf(empresa.productosServicios) }
    var clasificacion = remember { mutableStateOf(empresa.clasificacion) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Editar Empresa", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = url.value,
            onValueChange = { url.value = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono.value,
            onValueChange = { telefono.value = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = productosServicios.value,
            onValueChange = { productosServicios.value = it },
            label = { Text("Productos y Servicios") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = clasificacion.value,
            onValueChange = { clasificacion.value = it },
            label = { Text("Clasificación") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    val empresaActualizada = Empresa(
                        id = empresa.id,
                        nombre = nombre.value,
                        url = url.value,
                        telefono = telefono.value,
                        email = email.value,
                        productosServicios = productosServicios.value,
                        clasificacion = clasificacion.value
                    )
                    onUpdateCompany(empresaActualizada)
                }
            ) {
                Text("Guardar")
            }
        }
    }
}