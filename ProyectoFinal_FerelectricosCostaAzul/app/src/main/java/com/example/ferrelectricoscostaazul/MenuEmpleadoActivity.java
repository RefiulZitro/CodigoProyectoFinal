package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuEmpleadoActivity extends AppCompatActivity {

    ImageView surtirproductoImageView, inventarioImageView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MenuEmpleadoActivity.this, TipoUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_empleado);

        surtirproductoImageView = findViewById(R.id.surtirproductoImageView);
        inventarioImageView = findViewById(R.id.inventarioImageView);

        surtirproductoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuEmpleadoActivity.this, AgregarProductoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        inventarioImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuEmpleadoActivity.this, VerProductoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}