package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.save.tonelist.R;

public class IniciarSesion extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    EditText et_usuario;
    EditText et_contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
et_usuario=(EditText)findViewById(R.id.et_correo);
        et_contrasena=(EditText) findViewById(R.id.et_contrasena);
    }

    public void entrar(View v){
        startActivity(new Intent(getApplicationContext(),Rol.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void registrarse(View v){
        startActivity(new Intent(getApplicationContext(),Registrarse.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void olvideContrasena(View v){
        Toast.makeText(this,"Te hemos enviado la contraseña a tu correo electrónico",Toast.LENGTH_SHORT).show();

    }





}
