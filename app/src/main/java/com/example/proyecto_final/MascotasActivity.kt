package com.example.proyecto_final

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class MascotasActivity : AppCompatActivity() {
    var btnContinuar: Button? = null
    var btn_atras: ImageButton? = null
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotas)
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
        btnContinuar = findViewById(R.id.boton_continuar)
        btnContinuar.setOnClickListener(
            View.OnClickListener
            // Boton Continuar
            {
                val intent = Intent(this@MascotasActivity, MihogarActivity::class.java)
                startActivity(intent)
                finish()
            })
        btn_atras = findViewById(R.id.backButton)
        btn_atras.setOnClickListener(
            View.OnClickListener
            // Boton Atras
            {
                val intent = Intent(this@MascotasActivity, MihogarActivity::class.java)
                startActivity(intent)
                finish()
            })
    }
}