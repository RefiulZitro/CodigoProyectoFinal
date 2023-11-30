package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import model.Producto;

public class AgregarProductoActivity extends AppCompatActivity {

    EditText nombreEditText,tipoEditText,precioEditText,stockEditText,descripcionEditText;
    Button registrarButton;

     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        nombreEditText = findViewById(R.id.nombreEditText);
        tipoEditText = findViewById(R.id.cedulaEditText);
        precioEditText = findViewById(R.id.cargoEditText);
        stockEditText = findViewById(R.id.telefonoEditText);
        descripcionEditText = findViewById(R.id.hentradaEditText);

        registrarButton = findViewById(R.id.registrarButton);

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
    private void agregar(){
        String nombre = nombreEditText.getText().toString();
        String tipo = tipoEditText.getText().toString();
        String precio = precioEditText.getText().toString();
        String stock = stockEditText.getText().toString();
        String descripcion = descripcionEditText.getText().toString();

        if(nombre.equals("")|| tipo.equals("")|| precio.equals("")|| stock.equals("")||descripcion.equals("")){
         validacion();

        }else{
            Producto p = new Producto();
            p.setUid(UUID.randomUUID().toString());
            p.setNombre(nombre);
            p.setTipo(tipo);
            p.setPrecio(precio);
            p.setStock(stock);
            p.setDescripcion(descripcion);
            databaseReference.child("Producto").child(p.getUid()).setValue(p);

            Toast.makeText(this,"Agregado",Toast.LENGTH_SHORT).show();
            limpiarCajas();
        }

    }
    private void limpiarCajas(){
      nombreEditText.setText("");
      tipoEditText.setText("");
      precioEditText.setText("");
      stockEditText.setText("");
      descripcionEditText.setText("");
    }
    private void validacion(){
        String nombre = nombreEditText.getText().toString();
        String tipo = tipoEditText.getText().toString();
        String precio = precioEditText.getText().toString();
        String stock = stockEditText.getText().toString();
        String descripcion = descripcionEditText.getText().toString();

        if(nombre.equals("")){
            nombreEditText.setError("Dato Requerido");
        }else if(tipo.equals("")){
            tipoEditText.setError("Dato Requerido");
        }else if(precio.equals("")){
            precioEditText.setError("Dato Requerido");
        }else if(stock.equals("")){
            stockEditText.setError("Dato Requerido");
        }else if(descripcion.equals("")){
            descripcionEditText.setError("Dato Requerido");
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AgregarProductoActivity.this, MenuEmpleadoActivity.class);
        startActivity(intent);
        finish();
    }
}