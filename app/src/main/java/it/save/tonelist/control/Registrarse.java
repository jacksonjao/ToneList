package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.save.tonelist.R;

public class Registrarse extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText et_usuario;
    EditText et_contrasena;
    EditText et_confirmar_contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAuth = FirebaseAuth.getInstance();
        et_usuario=(EditText)findViewById(R.id.et_correo);
        et_contrasena=(EditText) findViewById(R.id.et_contrasena);
        et_confirmar_contrasena=(EditText) findViewById(R.id.et_confirmar_contrasena);
    }


    public void registrarse(View v){
        try{
        System.out.println(et_usuario.getText().toString()+" y "+et_contrasena.getText().toString());
        mAuth.createUserWithEmailAndPassword(et_usuario.getText().toString(),et_contrasena.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("onComplete", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Registrarse.this, "Usuario o contraseña inválidos, la contraseña debe tener mínimo 6 caracteres",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(getApplicationContext(),Rol.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                        }

                        // ...
                    }
                });}catch (Exception e){
            Toast.makeText(Registrarse.this, "Usuario o contraseña inválidos",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
