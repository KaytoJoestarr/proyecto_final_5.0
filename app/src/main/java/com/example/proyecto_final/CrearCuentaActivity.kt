package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class CrearCuentaActivity : AppCompatActivity() {
    var editTextEmail: TextInputEditText? = null
    var editTextPassword: TextInputEditText? = null
    var signUp: Button? = null
    var btn_atras: ImageButton? = null
    var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        firebaseAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        btn_atras = findViewById(R.id.btn_atras)
        signUp = findViewById(R.id.sign_up)
        signUp.setOnClickListener(View.OnClickListener {
            val email: String
            val password: String
            email = editTextEmail.getText().toString()
            password = editTextPassword.getText().toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@CrearCuentaActivity, "Ingrese un Email", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@CrearCuentaActivity, "Ingrese una Password", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            firebaseAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@CrearCuentaActivity,
                            "Registro Exitoso! :D",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@CrearCuentaActivity, CrearPerfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@CrearCuentaActivity,
                            "Error de Registro :(",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
}