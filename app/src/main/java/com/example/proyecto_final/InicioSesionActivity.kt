package com.example.proyecto_final

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class InicioSesionActivity() : AppCompatActivity() {
    var editTextEmail: TextInputEditText? = null
    var editTextPassword: TextInputEditText? = null
    var signInGoogle: SignInButton? = null
    var signIn: Button? = null
    var signUp: TextView? = null
    var firebaseAuth1 = FirebaseAuth.getInstance()
    var firebaseAuth2 = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var mGoogleSignClient: GoogleSignInClient? = null
    var pogressDialog: ProgressDialog? = null
    var RC_SIGN_IN = 40
    private val client: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        signIn = findViewById(R.id.sign_in)
        signUp = findViewById(R.id.sign_up)
        signInGoogle = findViewById(R.id.sign_in_google)
        signUp.setOnClickListener(
            View.OnClickListener
            // Boton Sign Up
            {
                val intent = Intent(this@InicioSesionActivity, CrearCuentaActivity::class.java)
                startActivity(intent)
                finish()
            })
        signIn.setOnClickListener(object : View.OnClickListener {
            // Boton Sign In
            override fun onClick(view: View) {
                val email: String
                val password: String
                email = editTextEmail.getText().toString()
                password = editTextPassword.getText().toString()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(
                        this@InicioSesionActivity,
                        "Ingresa tu Email",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(
                        this@InicioSesionActivity,
                        "Ingresa tu Password",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                firebaseAuth1.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@InicioSesionActivity,
                                "Logeado exitosamente! :D",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@InicioSesionActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@InicioSesionActivity,
                                "Error al logearse :(",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        })
        pogressDialog = ProgressDialog(this@InicioSesionActivity)
        pogressDialog!!.setTitle("Creando su cuenta...:D")
        pogressDialog!!.setTitle("Estamos creando su cuenta..:D")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignClient = GoogleSignIn.getClient(this, gso)
        signInGoogle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                signInGoogle()
            }
        })
    }

    private fun signInGoogle() {
        val intent = mGoogleSignClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuth(account.idToken)
            } catch (e: ApiException) {
                throw RuntimeException(e)
            }
        }
    }

    private fun firebaseAuth(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth2.signInWithCredential(credential)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful) {
                        val user = firebaseAuth2.currentUser
                        val intent = Intent(this@InicioSesionActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@InicioSesionActivity,
                            "Error de Autenticacion :(",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}