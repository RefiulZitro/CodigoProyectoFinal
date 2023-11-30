package com.example.ferrelectricoscostaazul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import model.Cliente;
import model.Producto;

public class SingUpActivity extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView singUpImageView;
    EditText usuarioSingUpTextField, emailEditTex, passwordEditTex, confirmPasswordEditTex;
    Button inicioSesion;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SingUpActivity.this, TipoUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        singUpImageView = findViewById(R.id.singUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidolabel);
        continuarLabel = findViewById(R.id.continuarLabel);

        usuarioSingUpTextField = findViewById(R.id.usuarioSingUpTextField);
        emailEditTex = findViewById(R.id.emailEditText);
        passwordEditTex = findViewById(R.id.passwordEditTex);
        confirmPasswordEditTex = findViewById(R.id.confirmPasswordEditTex);
        
        inicioSesion = findViewById(R.id.inicioSesion);

        mAuth = FirebaseAuth.getInstance();
        iniciarFireBase();


        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
            }
        });

    }
    private void iniciarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
    public void  validate(){

        String email = emailEditTex.getText().toString().trim();
        String password = passwordEditTex.getText().toString().trim();
        String confirmPassword = confirmPasswordEditTex.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            emailEditTex.setError("Correo No Valido");
            return;
        }else{
            emailEditTex.setError(null);
        }
        
        if(password.isEmpty() || password.length() < 8){
            passwordEditTex.setError("Minimo 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {

            passwordEditTex.setError("Usa al menos un numero");
            return;
        }else{
            passwordEditTex.setError(null);
        }

        if(!confirmPassword.equals(password)){
            confirmPasswordEditTex.setError("Las contraseñas deben ser iguales");
            return;
        }else{
            registrar(email,password);
        }

    }

    public void registrar(String email, String password){

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            agregar();

                        }else{

                            Toast.makeText(SingUpActivity.this,"Fallo en Registrase",Toast.LENGTH_LONG);
                        }
                    }
                });

    }

    private void agregar(){
        String nombre = usuarioSingUpTextField.getText().toString();
        String email = emailEditTex.getText().toString();
        String contrasena = passwordEditTex.getText().toString();


            Cliente c = new Cliente();
            String id = mAuth.getCurrentUser().getUid();
            c.setUid(id);
            c.setNombre(nombre);
            c.setEmail(email);
            c.setContraseña(contrasena);
            databaseReference.child("Usuarios").child("Clientes").child(id).setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Intent intent = new Intent(SingUpActivity.this, ComprasClienteActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(SingUpActivity.this,"No se pudo registrar el Cliente en la base de datos", Toast.LENGTH_SHORT);
                    }
                }
            });
    }
    public void onBackPresesed(){
        transitionBack();
    }
    private void limpiarCajas(){
        usuarioSingUpTextField.setText("");
        emailEditTex.setText("");
        passwordEditTex.setText("");
        confirmPasswordEditTex.setText("");
    }
    public void transitionBack(){
        Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}