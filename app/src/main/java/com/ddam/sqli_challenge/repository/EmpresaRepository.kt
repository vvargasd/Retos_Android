package com.ddam.sqli_challenge.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ddam.sqli_challenge.data.EmpresaDatabaseHelper
import com.ddam.sqli_challenge.model.Empresa

class EmpresaRepository(context: Context) {
    private val dbHelper = EmpresaDatabaseHelper(context)

    // Create (Agregar una empresa)
    fun agregarEmpresa(empresa: Empresa): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", empresa.nombre)
            put("url", empresa.url)
            put("telefono", empresa.telefono)
            put("email", empresa.email)
            put("productos_servicios", empresa.productosServicios)
            put("clasificacion", empresa.clasificacion)
        }
        return db.insert("empresas", null, values)
    }

    // Retrieve (Obtener todas las empresas)
    fun obtenerEmpresas(): List<Empresa> {
        val db = dbHelper.readableDatabase
        val cursor = db.query("empresas", null, null, null, null, null, null)
        return cursorToList(cursor)
    }

    // Update (Actualizar una empresa)
    fun actualizarEmpresa(empresa: Empresa): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", empresa.nombre)
            put("url", empresa.url)
            put("telefono", empresa.telefono)
            put("email", empresa.email)
            put("productos_servicios", empresa.productosServicios)
            put("clasificacion", empresa.clasificacion)
        }
        return db.update("empresas", values, "id = ?", arrayOf(empresa.id.toString()))
    }

    // Delete (Eliminar una empresa)
    fun eliminarEmpresa(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("empresas", "id = ?", arrayOf(id.toString()))
    }

    // Helper para convertir Cursor a List<Empresa>
    private fun cursorToList(cursor: Cursor): List<Empresa> {
        val empresas = mutableListOf<Empresa>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val nombre = getString(getColumnIndexOrThrow("nombre"))
                val url = getString(getColumnIndexOrThrow("url"))
                val telefono = getString(getColumnIndexOrThrow("telefono"))
                val email = getString(getColumnIndexOrThrow("email"))
                val productosServicios = getString(getColumnIndexOrThrow("productos_servicios"))
                val clasificacion = getString(getColumnIndexOrThrow("clasificacion"))
                empresas.add(Empresa(id, nombre, url, telefono, email, productosServicios, clasificacion))
            }
        }
        cursor.close()
        return empresas
    }
}