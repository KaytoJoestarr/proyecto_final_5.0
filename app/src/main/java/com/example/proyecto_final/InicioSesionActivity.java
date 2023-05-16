package com.example.proyecto_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class InicioSesionActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    SignInButton signInGoogle;
    Button signIn;
    TextView signUp;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseAuth firebaseAuth2 = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    GoogleSignInClient mGoogleSignClient;
    ProgressDialog pogressDialog;
    int RC_SIGN_IN = 40;
    private GoogleSignInClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        signInGoogle = findViewById(R.id.sign_in_google);


        signUp.setOnClickListener(new View.OnClickListener() { // Boton Sign Up
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioSesionActivity.this, CrearCuentaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() { // Boton Sign In
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(InicioSesionActivity.this, "Ingresa tu Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(InicioSesionActivity.this, "Ingresa tu Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth1.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(InicioSesionActivity.this, "Logeado exitosamente! :D", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InicioSesionActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(InicioSesionActivity.this, "Error al logearse :(", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        pogressDialog = new ProgressDialog(InicioSesionActivity.this);
        pogressDialog.setTitle("Creando su cuenta...:D");
        pogressDialog.setTitle("Estamos creando su cuenta..:D");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignClient = GoogleSignIn.getClient(this, gso);

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });
    }

    private void signInGoogle() {

        Intent intent = mGoogleSignClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }catch (ApiException e){
                throw new RuntimeException(e);

            }

        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth2.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth2.getCurrentUser();
                            Intent intent = new Intent(InicioSesionActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        else{
                            Toast.makeText(InicioSesionActivity.this, "Error de Autenticacion :(", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    }

