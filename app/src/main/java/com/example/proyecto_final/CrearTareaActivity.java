package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CrearTareaActivity extends AppCompatActivity {

    Button boton_hecho;
    EditText nombre_tarea, fecha_tarea, hora_tarea;
    //FirebaseFirestore db;
    ImageButton btnAtras;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    // Obtiene el usuario actualmente conectado
    FirebaseUser currentUser = auth.getCurrentUser();



    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DatabaseReference mDatabase;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        nombre_tarea = findViewById(R.id.txt_nombre_tarea);
        fecha_tarea = findViewById(R.id.txt_fecha_tarea);
        hora_tarea = findViewById(R.id.txt_hora_tarea);
        boton_hecho = findViewById(R.id.guardarTareaButton);



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







        boton_hecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDatos();
                Intent intent = new Intent(CrearTareaActivity.this, TareasActivity.class);
                startActivity(intent);
            }
        });

        btnAtras = findViewById(R.id.btn_atras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Realiza la navegación a la otra pantalla
                Intent intent = new Intent(CrearTareaActivity.this, TareasActivity.class);
                startActivity(intent);
            }
        });


    }

    private void crearDatos(){
        String nom_tar = nombre_tarea.getText().toString();
        String fec_tar = fecha_tarea.getText().toString();
        String hor_tar = hora_tarea.getText().toString();


        Map<String, Object> map = new HashMap();
        map.put("nombre_tarea", nom_tar);
        map.put("fecha_tarea", fec_tar);
        map.put("hora_tarea", hor_tar);


        db.collection("Tareas").document().set(map);
        Toast.makeText(CrearTareaActivity.this,"Guardado Correctamente! :D", Toast.LENGTH_SHORT).show();
    }

}
