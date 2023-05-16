package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CrearCuentaActivity  extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button signUp;
    ImageButton btn_atras;
    FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        firebaseAuth = FirebaseAuth.getInstance();


        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        btn_atras = findViewById(R.id.btn_atras);
        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(CrearCuentaActivity.this, "Ingrese un Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(CrearCuentaActivity.this, "Ingrese una Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(CrearCuentaActivity.this, "Registro Exitoso! :D", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CrearCuentaActivity.this, CrearPerfilActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(CrearCuentaActivity.this, "Error de Registro :(", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }

}
