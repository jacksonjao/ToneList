package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import it.save.tonelist.R;

public class Rol extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void dj(View v){
        Toast.makeText(this,"Aún no se crea esta actividad",Toast.LENGTH_SHORT).show();    }

    public void espectador(View v){
        startActivity(new Intent(getApplicationContext(),ListaPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
