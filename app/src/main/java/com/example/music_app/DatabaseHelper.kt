package com.example.music_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private  const val DATABASE_NAME = "musica.db"
        private const val DATABASE_VERSION = 1

        //tabla
        private const val TABLE_USUARIOS = "usuarios"

        //columnas
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"
    }

    //Metodo para crear la base de datos y sus tablas
    override fun onCreate(p0: SQLiteDatabase?) {
        val crearTablaUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_CORREO TEXT NOT NULL UNIQUE,
                $COL_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        p0?.execSQL(crearTablaUsuarios)
    }

    //Metodo para actualizar la base de datos
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(p0)
    }

    //Metodo para registrar un usuario (Insertar)
    fun registrarUsuario (nombre : String, correo : String, password : String) : Boolean{

        //Crear variable con permisos para escribir en la BD
        val db = writableDatabase

        //Generar variable para guardar la informacion
        val datos = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_CORREO, correo)
            put(COL_PASSWORD, password)
        }

        val resultado = db.insert(TABLE_USUARIOS, null, datos)

        return resultado != -1L
    }

    //metodo para consulta de usuraios
    fun iniciarSesion(correo: String, password: String) : Boolean {
        val db = readableDatabase

        val query_sql = """
            SELECT * FROM $TABLE_USUARIOS
            WHERE $COL_CORREO = ? AND $COL_PASSWORD = ?
        """.trimIndent()

        val cursor = db.rawQuery(
            query_sql, arrayOf(correo, password)
        )

        //Contar el numero de resultados encontrados
        val usuarioEncontrado = cursor.count > 0

        cursor.close()
        db.close()

        return usuarioEncontrado
    }

}