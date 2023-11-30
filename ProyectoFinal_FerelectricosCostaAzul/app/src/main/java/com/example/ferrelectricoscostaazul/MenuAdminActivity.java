package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdminActivity extends AppCompatActivity {

    TextView administradornombreTextView;
    ImageView administradorImageView,nuevo_pedidoImageView,reporteImageView,facturasImageView,empleadosImageView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MenuAdminActivity.this, TipoUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        empleadosImageView = findViewById(R.id.empleadosImageView);

        empleadosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdminActivity.this, GestionEmpleado.class);
                startActivity(intent);
                finish();
            }
        });

    }
}