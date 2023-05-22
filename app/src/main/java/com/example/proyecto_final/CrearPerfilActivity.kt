package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CrearPerfilActivity : AppCompatActivity() {
    var boton_continuar: Button? = null
    var nombre_perfil: EditText? = null
    var db: FirebaseFirestore? = null
    var email: String? = null

    // Inicializa Firebase Auth
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_perfil)
        nombre_perfil = findViewById(R.id.txt_nombre_perfil)
        boton_continuar = findViewById(R.id.btn_continuar)
        db = FirebaseFirestore.getInstance()
        boton_continuar.setOnClickListener(View.OnClickListener {
            crearDatos()
            val intent = Intent(this@CrearPerfilActivity, HomeActivity::class.java)
            startActivity(intent)
        })
    }

    private fun crearDatos() {
        val nom_per = nombre_perfil!!.text.toString()
        val map: MutableMap<String?, Any?> = HashMap<Any?, Any?>()
        map["nombre_perfil"] = nom_per
        map["email-perfil"] = currentUser!!.email.also { email = it }
        db!!.collection("Perfiles").document().set(map)
        Toast.makeText(this@CrearPerfilActivity, "Guardado Correctamente! :D", Toast.LENGTH_SHORT)
            .show()
    }
}