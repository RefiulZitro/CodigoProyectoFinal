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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Empleado;
import model.Producto;

public class ListadoEmpleados extends AppCompatActivity {
    private List<Empleado> empleadoList = new ArrayList<Empleado>();
    ArrayAdapter<Empleado> empleadoArrayAdapter;

    ListView listaEmpleado;
    TextView mostrarTextView;
    EditText buscarEditText;
    Button modificarButton,eliminarButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Empleado empleadoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_empleados);

        listaEmpleado = findViewById(R.id.listaEmpleado);
        modificarButton = findViewById(R.id.modificarButton);
        eliminarButton = findViewById(R.id.eliminarButton);
        mostrarTextView = findViewById(R.id.mostrarTextView);
        buscarEditText = findViewById(R.id.buscarEditText);

        iniciarFireBase();
        listarEmpleado();

        listaEmpleado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                empleadoSelecionado = (Empleado) parent.getItemAtPosition(position);
                mostrarTextView.setText(empleadoSelecionado.getNombre().toString());
            }
        });

        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datoEnviado = new Bundle();
                datoEnviado.putString("KeyDatos", empleadoSelecionado.getUid().toString());
                Intent intent = new Intent(ListadoEmpleados.this, ModificarEmpleado.class);
                intent.putExtras(datoEnviado);
                startActivity(intent);
                finish();
            }
        });

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListadoEmpleados.this);
                alerta.setMessage("¿Desea eliminar el empleado: " + empleadoSelecionado.getNombre() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Empleado e = new Empleado();
                                e.setUid(empleadoSelecionado.getUid());
                                databaseReference.child("Producto").child(e.getUid()).removeValue();
                                limpiarCajas();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                mostrarTextView.setText(empleadoSelecionado.getNombre().toString());
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar Empleado");
                titulo.show();
            }
        });
    }

        private void listarEmpleado() {
            databaseReference.child("Usuarios").child("Empleado").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    empleadoList.clear();
                    for (DataSnapshot objDataSnapshot: snapshot.getChildren()){
                        Empleado e = objDataSnapshot.getValue(Empleado.class);
                        empleadoList.add(e);

                        empleadoArrayAdapter = new ArrayAdapter<Empleado>(ListadoEmpleados.this, android.R.layout.simple_list_item_1, empleadoList);
                        listaEmpleado.setAdapter(empleadoArrayAdapter);
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
            Intent intent = new Intent(ListadoEmpleados.this, MenuAdminActivity.class);
            startActivity(intent);
            finish();
        }
    }