package com.ddam.sqli_challenge.ui.theme

import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ddam.sqli_challenge.model.Empresa

@Composable
fun CompanyListScreen(
    empresas: MutableState<List<Empresa>>,
    onAddClick: () -> Unit,
    onEditClick: (Empresa) -> Unit,
    onDeleteClick: (Empresa) -> Unit
) {
    var searchQuery = remember { mutableStateOf("") }
    var selectedClassification = remember { mutableStateOf("Todas") }
    var isDropdownExpanded = remember { mutableStateOf(false) }
    val classifications = listOf("Todas", "Consultoría", "Desarrollo a la medida", "Fábrica de software")

    // Filtrar empresas dinámicamente
    val filteredEmpresas = remember(searchQuery.value, selectedClassification.value, empresas.value) {
        empresas.value.filter { empresa ->
            empresa.nombre.contains(searchQuery.value, ignoreCase = true) &&
                    (selectedClassification.value == "Todas" || empresa.clasificacion == selectedClassification.value)
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding( bottom = 64.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 8.dp), // Espacio para el botón
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text("Buscar por nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            // Menú desplegable
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
                                selectedClassification.value = classification
                                isDropdownExpanded.value = false
                            },
                            interactionSource = remember { MutableInteractionSource() }
                        )
                    }
                }
            }

            // Lista filtrada
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(filteredEmpresas) { empresa ->
                    EmpresaItem(
                        empresa = empresa,
                        onEditClick = { onEditClick(empresa) },
                        onDeleteClick = { onDeleteClick(empresa) }
                    )
                }
            }
        }

        // Botón para agregar empresa
        Button(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Botón fijo en la parte inferior
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Agregar Empresa")
        }
    }
}

@Composable
fun EmpresaItem(
    empresa: Empresa,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth() // Caja que ocupa todo el ancho del elemento
        ) {
            // Contenido principal de la empresa
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 32.dp, end = 4.dp, bottom = 16.dp)
            ) {
                Text(text = "Nombre: ${empresa.nombre}")
                Text(text = "Teléfono: ${empresa.telefono}")
                Text(text = "Email: ${empresa.email}")
                Text(text = "URL: ${empresa.url}")
                Text(text = "Productos-servicios: ${empresa.telefono}")
                Text(text = "Clasificacion: ${empresa.clasificacion}")

            }

            // Botones alineados en la parte superior derecha
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd) // Alinea los botones en la parte superior derecha
                    .padding(8.dp), // Separación del borde
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre botones
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp) // Botones más pequeños
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }
        }
    }
}
