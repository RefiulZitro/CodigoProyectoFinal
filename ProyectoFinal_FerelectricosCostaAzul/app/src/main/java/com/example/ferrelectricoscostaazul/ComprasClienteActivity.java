package com.example.ferrelectricoscostaazul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Producto;

public class ComprasClienteActivity extends AppCompatActivity {

    private List<Producto> productoList = new ArrayList<Producto>();
    ArrayAdapter<Producto> productoArrayAdapter;

    ListView listaProducto;
    TextView mostrarTextView;
    EditText buscarEditText;
    Button modificarButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Producto productoSelecionado;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ComprasClienteActivity.this, TipoUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_cliente);

        listaProducto = findViewById(R.id.listaProducto);
        modificarButton = findViewById(R.id.modificarButton);
        mostrarTextView = findViewById(R.id.mostrarTextView);
        buscarEditText = findViewById(R.id.buscarEditText);

        iniciarFireBase();
        listarProductos();

        listaProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelecionado=(Producto) parent.getItemAtPosition(position);
                mostrarTextView.setText(productoSelecionado.getNombre().toString());
            }
        });
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productoSelecionado==null){
                    mostrarTextView.setText("Producto no selecionado");
                }else{
                    Bundle datoEnviado = new Bundle();
                    datoEnviado.putString("KeyDatos",productoSelecionado.getUid().toString());
                    Intent intent = new Intent(ComprasClienteActivity.this, CarritoActivity.class);
                    intent.putExtras(datoEnviado);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
    private void listarProductos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoList.clear();
                for (DataSnapshot objDataSnapshot: snapshot.getChildren()){
                    Producto p = objDataSnapshot.getValue(Producto.class);
                    productoList.add(p);

                    productoArrayAdapter = new ArrayAdapter<Producto>(ComprasClienteActivity.this, android.R.layout.simple_list_item_1, productoList);
                    listaProducto.setAdapter(productoArrayAdapter);
                }
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
    private void limpiarCajas(){
        mostrarTextView.setText("_______________");
    }
}