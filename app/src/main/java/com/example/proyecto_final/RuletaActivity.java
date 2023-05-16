package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

import java.util.Random;

public class RuletaActivity extends AppCompatActivity {

    private Button randomButton;
    ImageButton btn_atras;
    private TextView resultTextView;
    private String[] tareas = {"Ordenar Habitacion", "Pasear a los perros", "Lavar la Ropa", "Lavar los platos", "Cambiar la caja de arena"};


    FirebaseAuth auth = FirebaseAuth.getInstance();

    // Obtiene el usuario actualmente conectado
    FirebaseUser currentUser = auth.getCurrentUser();



    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);



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







        randomButton = findViewById(R.id.button);
        resultTextView = findViewById(R.id.text_nombre_tarea);

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int longitudArreglo = tareas.length;
                Random random = new Random();
                int indiceAleatorio = random.nextInt(longitudArreglo);
                String numeroAleatorio = tareas[indiceAleatorio];
                resultTextView.setText(numeroAleatorio);
            }
        });

        btn_atras = findViewById(R.id.backButton);

        btn_atras.setOnClickListener(new View.OnClickListener() { // Boton Atras
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RuletaActivity.this, DetalleTareaActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

}
