package com.example.proyecto_final;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MihogarActivity extends AppCompatActivity {

    Button btnEditar;
    ImageView btnMascota;

    ImageButton btnAtras;


    FirebaseAuth auth = FirebaseAuth.getInstance();

    // Obtiene el usuario actualmente conectado
    FirebaseUser currentUser = auth.getCurrentUser();



    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DatabaseReference mDatabase;



    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mihogar);



        mDatabase = FirebaseDatabase.getInstance().getReference();


        // Si el usuario está conectado, muestra su información
        if (currentUser != null) {
            String email = currentUser.getEmail();


            // Actualiza la interfaz de usuario con la información del usuario
            // ...


            db.collection("Perfiles")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("Seccessful:", document.getId() + " => " + document.getData());

                                    if (email.equals(document.getData().get("email-perfil"))) {
                                        TextView textView = findViewById(R.id.user_name);

                                        textView.setText((CharSequence) document.getData().get("nombre_perfil"));
                                    }
                                }
                            } else {
                                Log.w("Error getting documents.", task.getException());
                            }
                        }
                    });
        }







        btnAtras = findViewById(R.id.backButton);

        btnAtras.setOnClickListener(new View.OnClickListener() { // Boton Editar
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MihogarActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnEditar = findViewById(R.id.botonEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() { // Boton Editar
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MihogarActivity.this, CrearPerfilActivity.class);
                startActivity(intent);
            }
        });

        btnMascota = findViewById(R.id.mascotaAddBoton);

        btnMascota.setOnClickListener(new View.OnClickListener() { // Boton Agregar Mascota
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MihogarActivity.this, MascotasActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
