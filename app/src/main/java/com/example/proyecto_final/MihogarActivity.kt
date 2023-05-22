package com.example.proyecto_final

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class MihogarActivity : AppCompatActivity() {
    var btnEditar: Button? = null
    var btnMascota: ImageView? = null
    var btnAtras: ImageButton? = null
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mihogar)
        mDatabase = FirebaseDatabase.getInstance().reference


        // Si el usuario está conectado, muestra su información
        if (currentUser != null) {
            val email = currentUser!!.email


            // Actualiza la interfaz de usuario con la información del usuario
            // ...
            db.collection("Perfiles")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d("Seccessful:", document.id + " => " + document.data)
                            if (email == document.data["email-perfil"]) {
                                val textView = findViewById<TextView>(R.id.user_name)
                                textView.text = document.data["nombre_perfil"] as CharSequence?
                            }
                        }
                    } else {
                        Log.w("Error getting documents.", task.exception)
                    }
                }
        }
        btnAtras = findViewById(R.id.backButton)
        btnAtras.setOnClickListener(
            View.OnClickListener
            // Boton Editar
            {
                val intent = Intent(this@MihogarActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            })
        btnEditar = findViewById(R.id.botonEditar)
        btnEditar.setOnClickListener(
            View.OnClickListener
            // Boton Editar
            {
                val intent = Intent(this@MihogarActivity, CrearPerfilActivity::class.java)
                startActivity(intent)
            })
        btnMascota = findViewById(R.id.mascotaAddBoton)
        btnMascota.setOnClickListener(
            View.OnClickListener
            // Boton Agregar Mascota
            {
                val intent = Intent(this@MihogarActivity, MascotasActivity::class.java)
                startActivity(intent)
                finish()
            })
    }
}