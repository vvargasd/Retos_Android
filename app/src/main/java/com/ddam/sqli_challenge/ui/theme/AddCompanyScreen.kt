package com.ddam.sqli_challenge.ui.theme

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ddam.sqli_challenge.model.Empresa

@Composable
fun AddCompanyScreen(
    onAddCompany: (Empresa) -> Unit,
    onCancel: () -> Unit
) {
    var nombre = remember { mutableStateOf("") }
    var url = remember { mutableStateOf("") }
    var telefono = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var productosServicios = remember { mutableStateOf("") }
    var selectedClassification = remember { mutableStateOf("") }
    var isDropdownExpanded = remember { mutableStateOf(false) }
    val classifications = listOf("Consultoría", "Desarrollo a la medida", "Fábrica de software")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Agregar Empresa", style = MaterialTheme.typography.titleLarge)

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

        // Dropdown para seleccionar clasificación
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = if (selectedClassification.value.isEmpty()) "Seleccionar clasificación" else selectedClassification.value,
                onValueChange = {},
                label = { Text("Clasificación") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { isDropdownExpanded.value = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir clasificación")
                    }
                }
            )

            DropdownMenu(
                expanded = isDropdownExpanded.value,
                onDismissRequest = { isDropdownExpanded.value = false }
            ) {
                classifications.forEach { classification ->
                    DropdownMenuItem(
                        text = { Text(classification) },
                        onClick = {
                            selectedClassification.value = classification // Actualizamos la clasificación seleccionada
                            isDropdownExpanded.value = false // Cerramos el menú desplegable
                            Log.d("AddCompanyScreen", "Clasificación seleccionada: $classification")
                        },
                        interactionSource = remember { MutableInteractionSource() }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
            Button(
                onClick = {

                    if (nombre.value.isNotBlank() && selectedClassification.value.isNotEmpty()) {
                        val nuevaEmpresa = Empresa(
                            id = 0,
                            nombre = nombre.value,
                            url = url.value,
                            telefono = telefono.value,
                            email = email.value,
                            productosServicios = productosServicios.value,
                            clasificacion = selectedClassification.value
                        )
                        onAddCompany(nuevaEmpresa)
                    }
                }
            ) {
                Text("Guardar")
            }
        }
    }
}