package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.save.tonelist.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void iniciarSesion(View v){

startActivity(new Intent(getApplicationContext(),IniciarSesion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public void votar(View v){
        startActivity(new Intent(this,LeerQr.class));
    }
}
