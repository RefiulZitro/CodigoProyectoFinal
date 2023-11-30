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

import model.Producto;

public class ModifcarProductoActivity extends AppCompatActivity {

    EditText nombreEditText1,tipoEditText,precioEditText,stockEditText,descripcionEditText;
    Button modificarButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifcar_producto);

        Bundle datoRecibido = getIntent().getExtras();

        String uidProducto = datoRecibido.getString("KeyDatos");


        nombreEditText1 = findViewById(R.id.nombreEditTextm);
        tipoEditText = findViewById(R.id.tipoEditText1);
        precioEditText = findViewById(R.id.precioEditText1);
        stockEditText = findViewById(R.id.stockEditText1);
        descripcionEditText = findViewById(R.id.descripcionEditText1);

        modificarButton = findViewById(R.id.modificarButton1);

        iniciarFireBase();
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child(uidProducto).child("nombre").getValue().toString();
                    nombreEditText1.setText(nombre);
                    String tipo = snapshot.child(uidProducto).child("tipo").getValue().toString();
                    tipoEditText.setText(tipo);
                    String precio = snapshot.child(uidProducto).child("precio").getValue().toString();
                    precioEditText.setText(precio);
                    String stock = snapshot.child(uidProducto).child("stock").getValue().toString();
                    stockEditText.setText(stock);
                    String descripcion = snapshot.child(uidProducto).child("descripcion").getValue().toString();
                    descripcionEditText.setText(descripcion);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto p = new Producto();
                p.setUid(uidProducto);
                p.setNombre(nombreEditText1.getText().toString().trim());
                p.setTipo(tipoEditText.getText().toString().trim());
                p.setPrecio(precioEditText.getText().toString().trim());
                p.setStock(stockEditText.getText().toString().trim());
                p.setDescripcion(descripcionEditText.getText().toString().trim());
                databaseReference.child("Producto").child(p.getUid()).setValue(p);

                Intent intent = new Intent(ModifcarProductoActivity.this, VerProductoActivity.class);
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


    private void limpiarCajas(){
        nombreEditText1.setText("");
        tipoEditText.setText("");
        precioEditText.setText("");
        stockEditText.setText("");
        descripcionEditText.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ModifcarProductoActivity.this, VerProductoActivity.class);
        startActivity(intent);
        finish();
    }
}