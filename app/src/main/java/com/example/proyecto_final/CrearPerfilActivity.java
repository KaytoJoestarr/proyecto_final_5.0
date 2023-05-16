package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearPerfilActivity extends AppCompatActivity {

    Button boton_continuar;
    EditText nombre_perfil;
    FirebaseFirestore db;

    String email;

    // Inicializa Firebase Auth
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // Obtiene el usuario actualmente conectado
    FirebaseUser currentUser = auth.getCurrentUser();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil);




        nombre_perfil = findViewById(R.id.txt_nombre_perfil);
        boton_continuar = findViewById(R.id.btn_continuar);
        db = FirebaseFirestore.getInstance();

        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDatos();
                Intent intent = new Intent(CrearPerfilActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void crearDatos(){
        String nom_per = nombre_perfil.getText().toString();

        Map<String, Object> map = new HashMap();
        map.put("nombre_perfil", nom_per);
        map.put("email-perfil",  email = currentUser.getEmail());
        db.collection("Perfiles").document().set(map);
        Toast.makeText(CrearPerfilActivity.this,"Guardado Correctamente! :D", Toast.LENGTH_SHORT).show();
    }
}
