package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class LoginAdministradorActivity extends AppCompatActivity {

    TextView admin_bienvenidoLabel, admin_continuarLabel;
    ImageView admin_loginImageView;
    Button admin_inicioSesion, admin_signInButton;
    EditText admin_emailEditText, admin_contrasenaEditText;
    private FirebaseAuth mAuth;

    //
    GoogleSignInClient mGoogleSingInCLient;
    public static final int RC_SING_IN = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginAdministradorActivity.this, TipoUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder bluilder = new AlertDialog.Builder(this);
            bluilder.setMessage("Desea salir de")
                    .setPositiveButton("si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LoginAdministradorActivity.this, TipoUsuarioActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            bluilder.show();


        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_administrador);


        admin_loginImageView = findViewById(R.id.admin_loginImageView);
        admin_bienvenidoLabel = findViewById(R.id.admin_bienvenidolabel);
        admin_continuarLabel = findViewById(R.id.admin_continuarLabel);
        admin_inicioSesion = findViewById(R.id.admin_inicioSesion);
        admin_emailEditText = findViewById(R.id.admin_emailEditText);
        admin_contrasenaEditText = findViewById(R.id.admin_contrasenaEditText);

        mAuth = FirebaseAuth.getInstance();

        admin_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
            }
        });


        //Google SingIn

        admin_signInButton = findViewById(R.id.admin_loginGoogle);
        admin_signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singInWithGoogle();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSingInCLient = GoogleSignIn.getClient(this, gso);
    }
    private void singInWithGoogle(){
        Intent singInIntent = mGoogleSingInCLient.getSignInIntent();
        startActivityForResult(singInIntent, RC_SING_IN);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SING_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());

            }catch (ApiException e){
                Toast.makeText(LoginAdministradorActivity.this, "Fallo Google", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginAdministradorActivity.this, MenuAdminActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginAdministradorActivity.this, "Fallo en iniciar sesion",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
    }

    public void  validate(){

        String email = admin_emailEditText.getEditableText().toString().trim();
        String password = admin_contrasenaEditText.getEditableText().toString().trim();


        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            admin_emailEditText.setError("Correo No Valido");
            return;
        }else{
            admin_emailEditText.setError(null);
        }

        if(password.isEmpty() || password.length() < 8){
            admin_contrasenaEditText.setError("Minimo 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {

            admin_contrasenaEditText.setError("Usa al menos un numero");
            return;
        }else{
            admin_contrasenaEditText.setError(null);
        }
        iniciarSesion(email,password);


    }

    public void iniciarSesion(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginAdministradorActivity.this, MenuAdminActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginAdministradorActivity.this,"Credenciales Equivocadas: Intenta De Nuevo", Toast.LENGTH_LONG);

                        }

                    }
                });

    }

}