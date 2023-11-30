package com.example.ferrelectricoscostaazul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Producto;

public class VerProductoActivity extends AppCompatActivity {

    private List<Producto> productoList = new ArrayList<Producto>();
    ArrayAdapter<Producto> productoArrayAdapter;

    ListView listaProducto;
    TextView mostrarTextView;
    EditText buscarEditText;
    Button modificarButton,eliminarButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Producto productoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        listaProducto = findViewById(R.id.listaProducto);
        modificarButton = findViewById(R.id.modificarButton);
        eliminarButton = findViewById(R.id.eliminarButton);
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
                    Intent intent = new Intent(VerProductoActivity.this, ModifcarProductoActivity.class);
                    intent.putExtras(datoEnviado);
                    startActivity(intent);
                    finish();
                }

            }
        });
        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productoSelecionado==null){
                    mostrarTextView.setText("Producto no selecionado");
                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(VerProductoActivity.this);
                    alerta.setMessage("¿Desea eliminar el producto: "+ productoSelecionado.getNombre()+"?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Producto p = new Producto();
                                    p.setUid(productoSelecionado.getUid());
                                    databaseReference.child("Producto").child(p.getUid()).removeValue();
                                    limpiarCajas();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    mostrarTextView.setText(productoSelecionado.getNombre().toString());
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Eliminar Producto");
                    titulo.show();
                }

            }
        });

    }
   /* private void buscar(){
            if(buscarEditText.getText().toString().trim().isEmpty()){
                listarProductos();
            }else{
                String nombreBuscado = buscarEditText.getText().toString();
                databaseReference.child("Productos").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot x: snapshot.getChildren()){
                            if(nombreBuscado.equalsIgnoreCase(x.child("nombre").getValue().toString())){


                                Producto p = x.getValue(Producto.class);
                                productoList.add(p);

                                productoArrayAdapter = new ArrayAdapter<Producto>(VerProductoActivity.this, android.R.layout.simple_list_item_1, productoList);
                                listaProducto.setAdapter(productoArrayAdapter);

                            }else{
                                Toast.makeText(VerProductoActivity.this,"No se encontro un Producto bajo esta referencia",Toast.LENGTH_SHORT);

                                listarProductos();

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

    }*/
    private void listarProductos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoList.clear();
                for (DataSnapshot objDataSnapshot: snapshot.getChildren()){
                    Producto p = objDataSnapshot.getValue(Producto.class);
                    productoList.add(p);

                    productoArrayAdapter = new ArrayAdapter<Producto>(VerProductoActivity.this, android.R.layout.simple_list_item_1, productoList);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VerProductoActivity.this, MenuEmpleadoActivity.class);
        startActivity(intent);
        finish();
    }
}