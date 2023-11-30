package com.example.ferrelectricoscostaazul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Pattern;

import model.Empleado;

public class AgregarEmpleado extends AppCompatActivity {
    EditText emailEdit, contrasenaEdit,nombreEditText,cedulaEditText,telefonoEditText,cargoEditText,dlaboralesEditText,hentradaEditText, hsalidaEditText, salarioEditText;
    Button registrarButton;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_empleado);
        emailEdit = findViewById(R.id.emailEdit);
        contrasenaEdit = findViewById(R.id.contrasenaEdit);
        nombreEditText = findViewById(R.id.nombreEditText);
        cedulaEditText = findViewById(R.id.cedulaEditText);
        cargoEditText = findViewById(R.id.cargoEditText);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        dlaboralesEditText = findViewById(R.id.dlaboralesEditText);
        hentradaEditText = findViewById(R.id.hentradaEditText);
        hsalidaEditText = findViewById(R.id.hsalidaEditText);
        salarioEditText = findViewById(R.id.salarioEditText);

        registrarButton = findViewById(R.id.registrarButton);

        mAuth = FirebaseAuth.getInstance();
        iniciarFireBase();

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregar();
            }
        });



    }
    private void iniciarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
    public void  validate(){

        String email = emailEdit.getText().toString().trim();
        String password = contrasenaEdit.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            emailEdit.setError("Correo No Valido");
            return;
        }else{
            emailEdit.setError(null);
        }

        if(password.isEmpty() || password.length() < 8){
            contrasenaEdit.setError("Minimo 8 caracteres");
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

                            Toast.makeText(AgregarEmpleado.this,"Fallo en Registrar correo y contraseña",Toast.LENGTH_LONG);
                        }
                    }
                });

    }
    private void agregar(){

        String nombre = nombreEditText.getText().toString();
        String cedula = cedulaEditText.getText().toString();
        String telefono = telefonoEditText.getText().toString();
        String cargo = cargoEditText.getText().toString();
        String dlaborales = dlaboralesEditText.getText().toString();
        String hentrada = hentradaEditText.getText().toString();
        String hsalida = hsalidaEditText.getText().toString();
        String salario = salarioEditText.getText().toString();
        String email = emailEdit.getText().toString();
        String contrasena = contrasenaEdit.getText().toString();

        if(nombre.equals("")||email.equals("")||contrasena.equals("")|| cedula.equals("")|| telefono.equals("")||dlaborales.equals("")||hentrada.equals("")||hsalida.equals("")||salario.equals("")||cargo.equals("")){
            validacion();

        }else{
            Empleado e = new Empleado();
            String id = mAuth.getCurrentUser().getUid();
            e.setUid(id);
            e.setNombre(nombre);
            e.setCedula(cedula);
            e.setCargo(cargo);
            e.setDlaborales(dlaborales);
            e.setHentrada(hentrada);
            e.setHsalida(hsalida);
            e.setSalario(salario);
            e.setTelefono(telefono);
            e.setEmail(email);
            e.setContrasena(contrasena);

            databaseReference.child("Usuarios").child("Empleado").child(id).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Intent intent = new Intent(AgregarEmpleado.this, GestionEmpleado.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(AgregarEmpleado.this,"No se pudo registrar el Empleado en la base de datos", Toast.LENGTH_SHORT);
                    }
                }
            });


        }

    }

    private void validacion() {

        String nombre = nombreEditText.getText().toString();
        String cedula = cedulaEditText.getText().toString();
        String telefono = telefonoEditText.getText().toString();
        String cargo = cargoEditText.getText().toString();
        String dlaborales = dlaboralesEditText.getText().toString();
        String hentrada = hentradaEditText.getText().toString();
        String hsalida = hsalidaEditText.getText().toString();
        String salario = salarioEditText.getText().toString();
        String email = emailEdit.getText().toString();
        String contrasena = contrasenaEdit.getText().toString();

        if (email.equals("")) {
            emailEdit.setError("Dato Requerido");
        } else if (contrasena.equals("")) {
            contrasenaEdit.setError("Dato Requerido");
        } else if (dlaborales.equals("")) {
            dlaboralesEditText.setError("Dato Requerido");
        } else if (nombre.equals("")) {
            nombreEditText.setError("Dato Requerido");
        } else if (cedula.equals("")) {
            cedulaEditText.setError("Dato Requerido");
        } else if (cargo.equals("")) {
            cargoEditText.setError("Dato Requerido");
        } else if (telefono.equals("")) {
            telefonoEditText.setError("Dato Requerido");
        } else if (hentrada.equals("")) {
            hentradaEditText.setError("Dato Requerido");
        } else if (hsalida.equals("")) {
            hsalidaEditText.setError("Dato Requerido");
        } else if (salario.equals("")) {
            salarioEditText.setError("Dato Requerido");


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AgregarEmpleado.this, MenuEmpleadoActivity.class);
        startActivity(intent);
        finish();
    }
}
