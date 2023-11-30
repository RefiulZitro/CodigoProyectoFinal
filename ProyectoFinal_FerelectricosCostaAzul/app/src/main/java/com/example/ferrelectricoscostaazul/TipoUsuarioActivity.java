package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TipoUsuarioActivity extends AppCompatActivity {

    ImageButton clienteButton, empleadoButton, administradorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_usuario);

        clienteButton = findViewById(R.id.clienteButton);
        empleadoButton = findViewById(R.id.empleadoButton);
        administradorButton = findViewById(R.id.administradorButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(TipoUsuarioActivity.this);

        clienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user!= null && account!= null ){
                            Intent intent = new Intent(TipoUsuarioActivity.this, ComprasClienteActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(TipoUsuarioActivity.this, SingUpActivity.class);
                            startActivity(intent);
                            finish();
                    }
                }
            },1000);
            }
        });
        empleadoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user!= null && account!= null ){
                            Intent intent = new Intent(TipoUsuarioActivity.this, MenuEmpleadoActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(TipoUsuarioActivity.this, LoginEmpleadoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },1000);
            }
        });
        administradorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user!= null && account!= null ){
                            Intent intent = new Intent(TipoUsuarioActivity.this, MenuAdminActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(TipoUsuarioActivity.this, LoginAdministradorActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },1000);
            }
        });
    }
}