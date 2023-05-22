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
import java.util.*

class RuletaActivity : AppCompatActivity() {
    private var randomButton: Button? = null
    var btn_atras: ImageButton? = null
    private var resultTextView: TextView? = null
    private val tareas = arrayOf(
        "Ordenar Habitacion",
        "Pasear a los perros",
        "Lavar la Ropa",
        "Lavar los platos",
        "Cambiar la caja de arena"
    )
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruleta)
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
        randomButton = findViewById(R.id.button)
        resultTextView = findViewById(R.id.text_nombre_tarea)
        randomButton.setOnClickListener(View.OnClickListener {
            val longitudArreglo = tareas.size
            val random = Random()
            val indiceAleatorio = random.nextInt(longitudArreglo)
            val numeroAleatorio = tareas[indiceAleatorio]
            resultTextView.setText(numeroAleatorio)
        })
        btn_atras = findViewById(R.id.backButton)
        btn_atras.setOnClickListener(
            View.OnClickListener
            // Boton Atras
            {
                val intent = Intent(this@RuletaActivity, DetalleTareaActivity::class.java)
                startActivity(intent)
                finish()
            })
    }
}