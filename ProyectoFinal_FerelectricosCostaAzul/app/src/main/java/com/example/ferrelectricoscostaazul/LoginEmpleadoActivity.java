package com.example.ferrelectricoscostaazul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Patterns;
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

public class LoginEmpleadoActivity extends AppCompatActivity {

    TextView emple_bienvenidoLabel, emple_continuarLabel;
    ImageView emple_loginImageView;
    Button emple_inicioSesion, emple_signInButton;
    EditText emple_emailEditText, emple_contrasenaEditText;
    private FirebaseAuth mAuth;

    //
    GoogleSignInClient mGoogleSingInCLient;
    public static final int RC_SING_IN = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginEmpleadoActivity.this, TipoUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empleado);

        emple_loginImageView = findViewById(R.id.emple_loginImageView);
        emple_bienvenidoLabel = findViewById(R.id.emple_bienvenidolabel);
        emple_continuarLabel = findViewById(R.id.emple_continuarLabel);
        emple_inicioSesion = findViewById(R.id.emple_inicioSesion);
        emple_emailEditText = findViewById(R.id.emple_emailEditText);
        emple_contrasenaEditText = findViewById(R.id.emple_contrasenaEditText);

        mAuth = FirebaseAuth.getInstance();

        emple_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
            }
        });



        //Google SingIn

        emple_signInButton = findViewById(R.id.emple_loginGoogle);
        emple_signInButton.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(LoginEmpleadoActivity.this, "Fallo Google", Toast.LENGTH_SHORT).show();

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
                            Intent intent = new Intent(LoginEmpleadoActivity.this, MenuEmpleadoActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginEmpleadoActivity.this, "Fallo en iniciar sesion",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
    }

    public void  validate(){

        String email = emple_emailEditText.getEditableText().toString().trim();
        String password = emple_contrasenaEditText.getEditableText().toString().trim();


        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            emple_emailEditText.setError("Correo No Valido");
            return;
        }else{
            emple_emailEditText.setError(null);
        }

        if(password.isEmpty() || password.length() < 8){
            emple_contrasenaEditText.setError("Minimo 8 caracteres");
            return;
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {

            emple_contrasenaEditText.setError("Usa al menos un numero");
            return;
        }else{
            emple_contrasenaEditText.setError(null);
        }
        iniciarSesion(email,password);


    }

    public void iniciarSesion(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginEmpleadoActivity.this, MenuEmpleadoActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginEmpleadoActivity.this,"Credenciales Equivocadas: Intenta De Nuevo", Toast.LENGTH_LONG);

                        }

                    }
                });

    }

}