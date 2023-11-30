package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GestionEmpleado extends AppCompatActivity {
    ImageView empleadoButton2,empleadosImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_empleado);
        empleadoButton2 = findViewById(R.id.empleadoButton2);
        empleadosImageView = findViewById(R.id.empleadosImageView);

        empleadoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionEmpleado.this, AgregarEmpleado.class);
                startActivity(intent);
                finish();
            }
        });
        empleadosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionEmpleado.this, ListadoEmpleados.class);
                startActivity(intent);
                finish();
            }
        });
    }
}