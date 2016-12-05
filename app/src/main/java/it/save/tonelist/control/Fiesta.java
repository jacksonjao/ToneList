package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.save.tonelist.R;

public class Fiesta extends AppCompatActivity {
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        //controlo el menu desplegable
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230,0,0,0));
        validarMenu();
    }



    public void cancionesVotadas(View v){
        startActivity(new Intent(getApplicationContext(), VerVotadas.class));

    }

    public void cancionesRecomendadas(View v){
        startActivity(new Intent(getApplicationContext(), VerRecomendadas.class));

    }




    public void modificarLista(View v){
        startActivity(new Intent(getApplicationContext(), EditarLista.class));
    }

    public void menu(View v) {
        drawerLayout.openDrawer(menu);

    }

    public void validarMenu(){
        tv_listaPrincipal.setText(getIntent().getStringExtra("name"));
    }

    public void misFiestas(View v){


        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();

    }


    public void salir(View v) {
        startActivity(new Intent(getApplicationContext(), LeerQr.class));

    }

    public void cerrarSesion(View v) {
        startActivity(new Intent(getApplicationContext(), Login.class));

    }

    public void cambiarRol(View v) {
        startActivity(new Intent(getApplicationContext(), Rol.class));

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

}
