package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val i = Intent(this@SplashActivity, InicioSesionActivity::class.java)
            startActivity(i)
            finish()
        }, 3000) // 3000 milisegundos = 3 segundos
    }
}