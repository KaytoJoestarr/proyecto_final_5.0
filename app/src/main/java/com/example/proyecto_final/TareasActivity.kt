package com.example.proyecto_final

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

class TareasActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var tareasArrayList: ArrayList<Tarea>? = null
    var tareasAdapter: TareasAdapter? = null
    var progressDialog: ProgressDialog? = null
    var btnCrear: ImageButton? = null
    var btnAtras: ImageButton? = null
    var auth = FirebaseAuth.getInstance()

    // Obtiene el usuario actualmente conectado
    var currentUser = auth.currentUser
    var db = FirebaseFirestore.getInstance()
    private var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareas)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Obteniendo los datos...")
        progressDialog!!.show()
        recyclerView = findViewById(R.id.my_recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        tareasArrayList = ArrayList()
        tareasAdapter = TareasAdapter(this@TareasActivity, tareasArrayList)
        recyclerView.setAdapter(tareasAdapter)
        btnCrear = findViewById(R.id.crearButton)
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
        btnCrear.setOnClickListener(View.OnClickListener { // Realiza la navegación a la otra pantalla
            val intent = Intent(this@TareasActivity, CrearTareaActivity::class.java)
            startActivity(intent)
        })
        btnAtras = findViewById(R.id.btn_atras)
        btnAtras.setOnClickListener(View.OnClickListener { // Realiza la navegación a la otra pantalla
            val intent = Intent(this@TareasActivity, HomeActivity::class.java)
            startActivity(intent)
        })
        EventChangeListener()
    }

    private fun EventChangeListener() {
        db.collection("Tareas")
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    if (progressDialog!!.isShowing) progressDialog!!.dismiss()
                    Log.e("Error al conectar la Base de Datos...", error.message!!)
                    return@EventListener
                }
                for (dc in value!!.documentChanges) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        tareasArrayList!!.add(dc.document.toObject(Tarea::class.java))
                    }
                    tareasAdapter!!.notifyDataSetChanged()
                    if (progressDialog!!.isShowing) progressDialog!!.dismiss()
                }
            })
    }
}