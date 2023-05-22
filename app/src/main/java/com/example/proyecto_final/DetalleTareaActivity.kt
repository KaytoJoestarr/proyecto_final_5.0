package com.example.proyecto_final

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

class DetalleTareaActivity : AppCompatActivity() {
    var btn_atras: ImageButton? = null
    var btn_ruleta: Button? = null
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tarea)
        mDatabase = FirebaseDatabase.getInstance().reference



        // Si el usuario está conectado, muestra su información
        if (currentUser != null) {
            val email = currentUser!!.email
            val des =


            // Actualiza la interfaz de usuario con la información del usuario
            // ...
            db.collection("Tareas")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d("Seccessful:", document.id + " => " + document.data)
                            if (email == document.data["descripcion_tarea"]) {
                                val textView = findViewById<TextView>(R.id.tv_nombre_user)
                                textView.text = document.data["nombre_perfil"] as CharSequence?
                            }
                        }
                    } else {
                        Log.w("Error getting documents.", task.exception)
                    }
                }
        }






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
        btn_atras = findViewById(R.id.btn_atras)
        btn_atras.setOnClickListener(
            View.OnClickListener
            // Boton Atras
            {
                val intent = Intent(this@DetalleTareaActivity, TareasActivity::class.java)
                startActivity(intent)
                finish()
            })
        btn_ruleta = findViewById(R.id.btn_ruleta)
        btn_ruleta.setOnClickListener(
            View.OnClickListener
            // Boton Atras
            {
                val intent = Intent(this@DetalleTareaActivity, RuletaActivity::class.java)
                startActivity(intent)
            })
    }
}