package com.ddam.sqli_challenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ddam.sqli_challenge.model.Empresa

import com.ddam.sqli_challenge.repository.EmpresaRepository
import com.ddam.sqli_challenge.ui.theme.AddCompanyScreen
import com.ddam.sqli_challenge.ui.theme.CompanyListScreen
import com.ddam.sqli_challenge.ui.theme.EditCompanyScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val repo = EmpresaRepository(this)
            val empresas = remember { mutableStateOf(repo.obtenerEmpresas()) }
            val isAdding = remember { mutableStateOf(false) }
            val empresaParaEditar = remember { mutableStateOf<Empresa?>(null) }

            when {
                isAdding.value -> {
                    // Pantalla para agregar una nueva empresa
                    AddCompanyScreen(
                        onAddCompany = { empresa ->

                            repo.agregarEmpresa(empresa)
                            empresas.value = repo.obtenerEmpresas() // Actualizar la lista
                            isAdding.value = false // Volver a la lista
                        },
                        onCancel = { isAdding.value = false } // Cancelar y volver a la lista
                    )
                }
                empresaParaEditar.value != null -> {
                    // Pantalla para editar una empresa existente
                    EditCompanyScreen(
                        empresa = empresaParaEditar.value!!, // Empresa seleccionada
                        onUpdateCompany = { empresaActualizada ->
                            repo.actualizarEmpresa(empresaActualizada) // Actualizar en la base de datos
                            empresas.value = repo.obtenerEmpresas() // Refrescar la lista
                            empresaParaEditar.value = null // Salir del modo de edición
                        },
                        onCancel = { empresaParaEditar.value = null } // Cancelar edición
                    )
                }
                else -> {
                    // Pantalla principal con la lista de empresas
                    CompanyListScreen(
                        empresas = empresas,
                        onAddClick = { isAdding.value = true }, // Navegar a agregar empresa
                        onEditClick = { empresa -> empresaParaEditar.value = empresa }, // Seleccionar empresa para editar
                        onDeleteClick = { empresa ->
                            repo.eliminarEmpresa(empresa.id)
                            empresas.value = repo.obtenerEmpresas() // Actualizar la lista
                        }
                    )
                }
            }
        }
    }
}