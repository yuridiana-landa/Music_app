package com.example.music_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnRegistrar : Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        dbHelper = DatabaseHelper(this)

        //Crear elemento para registrar un usuario
        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
    }

    //metodo para registrar un usuario
    private fun registrarUsuario () {
        //obtener la informacion que escribe el usuario
        val nombre = etNombre.text.toString().trim()
        val correo = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        //validar que el usuario llene los campos
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = dbHelper.registrarUsuario(nombre, correo, password)

        //validar registro
        if (resultado) {
            //Si el resultado es verdadero
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
        }
    }
}