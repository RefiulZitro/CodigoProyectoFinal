package com.example.ferrelectricoscostaazul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Producto;

public class CarritoActivity extends AppCompatActivity {
    private List<Producto> productoList = new ArrayList<Producto>();
    ArrayAdapter<Producto> productoArrayAdapter;
    ListView listaProducto;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        Bundle datoRecibido = getIntent().getExtras();
        String uidProducto = datoRecibido.getString("KeyDatos");
        iniciarFireBase();
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nombre = snapshot.child(uidProducto).child("nombre").getValue().toString();
                String tipo = snapshot.child(uidProducto).child("tipo").getValue().toString();
                String precio = snapshot.child(uidProducto).child("precio").getValue().toString();
                String stock = snapshot.child(uidProducto).child("stock").getValue().toString();
                String descricion = snapshot.child(uidProducto).child("descripcion").getValue().toString();

                Producto p = new Producto();
                p.setUid(uidProducto);
                p.setNombre(nombre);
                p.setTipo(tipo);
                p.setPrecio(precio);
                p.setStock(stock);
                p.setDescripcion(descricion);

                productoList.add(p);

                productoArrayAdapter = new ArrayAdapter<Producto>(CarritoActivity.this, android.R.layout.simple_list_item_1, productoList);
                listaProducto.setAdapter(productoArrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}