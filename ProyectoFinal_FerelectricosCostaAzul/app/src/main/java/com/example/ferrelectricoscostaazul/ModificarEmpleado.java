package com.example.ferrelectricoscostaazul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import model.Empleado;
import model.Producto;

public class ModificarEmpleado extends AppCompatActivity {
    EditText emaileditText,contrasenaeditText, nombreEditText1, cedulaEditText1, telefonoEditText1, cargoEditText1, dlaboralesEditText1, hentradaEditText1, hsalidaEditText1, salarioEditText1;
    Button modificarButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_empleado);

        Bundle datoRecibido = getIntent().getExtras();

        String uidEmpleado = datoRecibido.getString("KeyDatos");

        emaileditText = findViewById(R.id.emaileditText);
        contrasenaeditText = findViewById(R.id.contrasenaeditText);
        nombreEditText1 = findViewById(R.id.nombreEditText1);
        cedulaEditText1 = findViewById(R.id.cedulaEditText1);
        cargoEditText1 = findViewById(R.id.cargoEditText1);
        telefonoEditText1 = findViewById(R.id.telefonoEditText1);
        dlaboralesEditText1 = findViewById(R.id.dlaboralesEditText1);
        hentradaEditText1 = findViewById(R.id.hentradaEditText1);
        hsalidaEditText1 = findViewById(R.id.hsalidaEditText1);
        salarioEditText1 = findViewById(R.id.salarioEditText1);

        modificarButton = findViewById(R.id.modificarButton1);

        iniciarFireBase();
        databaseReference.child("Usuarios").child("Empleado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String nombre = snapshot.child(uidEmpleado).child("nombre").getValue().toString();
                    nombreEditText1.setText(nombre);
                    String cargo = snapshot.child(uidEmpleado).child("cargo").getValue().toString();
                    cargoEditText1.setText(cargo);
                    String telefono = snapshot.child(uidEmpleado).child("telefono").getValue().toString();
                    telefonoEditText1.setText(telefono);
                    String dlaborales = snapshot.child(uidEmpleado).child("dlaborales").getValue().toString();
                    dlaboralesEditText1.setText(dlaborales);
                    String hentrada = snapshot.child(uidEmpleado).child("hentrada").getValue().toString();
                    hentradaEditText1.setText(hentrada);
                    String hsalida = snapshot.child(uidEmpleado).child("hsalida").getValue().toString();
                    hsalidaEditText1.setText(hsalida);
                    String salario = snapshot.child(uidEmpleado).child("salario").getValue().toString();
                    salarioEditText1.setText(salario);
                    String cedula = snapshot.child(uidEmpleado).child("cedula").getValue().toString();
                    cedulaEditText1.setText(cedula);
                    String email = snapshot.child(uidEmpleado).child("email").getValue().toString();
                    salarioEditText1.setText(email);
                    String contrasena = snapshot.child(uidEmpleado).child("contrasena").getValue().toString();
                    cedulaEditText1.setText(contrasena);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado e = new Empleado();
                e.setUid(uidEmpleado);
                e.setNombre(nombreEditText1.getText().toString().trim());
                e.setCedula(cedulaEditText1.getText().toString().trim());
                e.setCargo(cargoEditText1.getText().toString().trim());
                e.setDlaborales(dlaboralesEditText1.getText().toString().trim());
                e.setHentrada(hentradaEditText1.getText().toString().trim());
                e.setHsalida(hsalidaEditText1.getText().toString().trim());
                e.setSalario(salarioEditText1.getText().toString().trim());
                e.setTelefono(telefonoEditText1.getText().toString().trim());
                e.setEmail(emaileditText.getText().toString().trim());
                e.setContrasena(contrasenaeditText.getText().toString().trim());
                databaseReference.child("Usuarios").child("Empleado").child(e.getUid()).setValue(e);

                Intent intent = new Intent(ModificarEmpleado.this, ListadoEmpleados.class);
                startActivity(intent);
                finish();

            }
        });

    }


    private void iniciarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ModificarEmpleado.this, ListadoEmpleados.class);
        startActivity(intent);
        finish();
    }
}