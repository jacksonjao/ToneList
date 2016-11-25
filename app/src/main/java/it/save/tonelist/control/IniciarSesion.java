package it.save.tonelist.control;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        String usuario=et_usuario.getText().toString();
        String contrasena= et_contrasena.getText().toString();


    }
}
