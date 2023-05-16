package com.example.proyecto_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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


public class HomeActivity extends AppCompatActivity {

    ImageButton btn_mihogar, mis_tareas_button, notas_button;
    Button btnLogout;

    TextView tv_usuario;

    // Inicializa Firebase Auth
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // Obtiene el usuario actualmente conectado
    FirebaseUser currentUser = auth.getCurrentUser();



    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DatabaseReference mDatabase;




    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



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
                                        TextView textView = findViewById(R.id.tv_nombre_user);

                                        textView.setText((CharSequence) document.getData().get("nombre_perfil"));
                                    }
                                }
                            } else {
                                Log.w("Error getting documents.", task.getException());
                            }
                        }
                    });
        }










        btn_mihogar = findViewById(R.id.btn_mihogar);

        btn_mihogar.setOnClickListener(new View.OnClickListener() { // Boton Mi hogar
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MihogarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mis_tareas_button = findViewById(R.id.mis_tareas_button);

        mis_tareas_button.setOnClickListener(new View.OnClickListener() { // Boton Mis Tareas
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TareasActivity.class);
                startActivity(intent);
            }
        });

        notas_button = findViewById(R.id.notas_button);

        notas_button.setOnClickListener(new View.OnClickListener() { // Boton Mis Notas
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, IntercambiarTareaActivity.class);
                startActivity(intent);
            }
        });

        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, InicioSesionActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }








}
