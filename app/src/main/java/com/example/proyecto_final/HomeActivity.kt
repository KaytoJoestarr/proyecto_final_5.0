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

class HomeActivity : AppCompatActivity() {
    var btn_mihogar: ImageButton? = null
    var mis_tareas_button: ImageButton? = null
    var notas_button: ImageButton? = null
    var btnLogout: Button? = null
    var tv_usuario: TextView? = null

    // Inicializa Firebase Auth
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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
                                val textView = findViewById<TextView>(R.id.tv_nombre_user)
                                textView.text = document.data["nombre_perfil"] as CharSequence?
                            }
                        }
                    } else {
                        Log.w("Error getting documents.", task.exception)
                    }
                }
        }
        btn_mihogar = findViewById(R.id.btn_mihogar)
        btn_mihogar.setOnClickListener(
            View.OnClickListener
            // Boton Mi hogar
            {
                val intent = Intent(this@HomeActivity, MihogarActivity::class.java)
                startActivity(intent)
                finish()
            })
        mis_tareas_button = findViewById(R.id.mis_tareas_button)
        mis_tareas_button.setOnClickListener(
            View.OnClickListener
            // Boton Mis Tareas
            {
                val intent = Intent(this@HomeActivity, TareasActivity::class.java)
                startActivity(intent)
            })
        notas_button = findViewById(R.id.notas_button)
        notas_button.setOnClickListener(
            View.OnClickListener
            // Boton Mis Notas
            {
                val intent = Intent(this@HomeActivity, IntercambiarTareaActivity::class.java)
                startActivity(intent)
            })
        btnLogout = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@HomeActivity, InicioSesionActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}