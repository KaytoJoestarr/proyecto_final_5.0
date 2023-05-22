package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class CrearTareaActivity : AppCompatActivity() {
    var boton_hecho: Button? = null
    var nombre_tarea: EditText? = null
    var fecha_tarea: EditText? = null
    var hora_tarea: EditText? = null
    var descripcion_tarea: EditText? = null

    //FirebaseFirestore db;
    var btnAtras: ImageButton? = null
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tarea)
        nombre_tarea = findViewById(R.id.txt_nombre_tarea)
        fecha_tarea = findViewById(R.id.txt_fecha_tarea)
        hora_tarea = findViewById(R.id.txt_hora_tarea)
        boton_hecho = findViewById(R.id.guardarTareaButton)
        mDatabase = FirebaseDatabase.getInstance().reference
        descripcion_tarea = findViewById(R.id.txt_des_tarea)


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
        boton_hecho.setOnClickListener(View.OnClickListener {
            crearDatos()
            val intent = Intent(this@CrearTareaActivity, TareasActivity::class.java)
            startActivity(intent)
        })
        btnAtras = findViewById(R.id.btn_atras)
        btnAtras.setOnClickListener(View.OnClickListener { // Realiza la navegación a la otra pantalla
            val intent = Intent(this@CrearTareaActivity, TareasActivity::class.java)
            startActivity(intent)
        })
    }

    private fun crearDatos() {
        val nom_tar = nombre_tarea!!.text.toString()
        val des_tar = descripcion_tarea!!.text.toString()
        val fec_tar = fecha_tarea!!.text.toString()
        val hor_tar = hora_tarea!!.text.toString()


        val map: MutableMap<String?, Any?> = HashMap<Any?, Any?>()
        map["nombre_tarea"] = nom_tar
        map["descripcion_tarea"] = des_tar
        map["fecha_tarea"] = fec_tar
        map["hora_tarea"] = hor_tar
        db.collection("Tareas").document().set(map)
        Toast.makeText(this@CrearTareaActivity, "Guardado Correctamenteeeee! :D", Toast.LENGTH_SHORT)
            .show()
    }
}