package it.save.tonelist.control;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import it.save.tonelist.R;


public class CancionesRecomendadas extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    SearchView searchView;
    RelativeLayout menu;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones_recomendadas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        searchView = (SearchView) findViewById(R.id.sv_buscar);
        int searchBarId = searchView.getContext().getResources().getIdentifier("android:id/search_bar", null, null);
        final LinearLayout searchBar = (LinearLayout) searchView.findViewById(searchBarId);
        searchBar.setLayoutTransition(new LayoutTransition());
        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        //controlo el menu desplegable
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(220, 0, 0, 0));
        search();
        searchView.setOnQueryTextListener(this);
        validarMenu();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    public void menu(View v) {
        drawerLayout.openDrawer(menu);

    }



    public void search() {
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.makeOutAnimation(getApplicationContext(), false);
                tv_listaPrincipal.setAnimation(animation);
                tv_listaPrincipal.setVisibility(View.INVISIBLE);
                btn_menu.setAnimation(animation);
                btn_menu.setVisibility(View.INVISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Animation animation = AnimationUtils.makeInAnimation(getApplicationContext(), true);
                tv_listaPrincipal.setAnimation(animation);
                tv_listaPrincipal.setVisibility(View.VISIBLE);
                btn_menu.setAnimation(animation);
                btn_menu.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }





    public void validarMenu(){
        tv_listaPrincipal.setText(getResources().getString(R.string.canciones_recomendadas_titulo));
        TextView textView=(TextView) drawerLayout.findViewById(R.id.tv_recomendaron);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
    }

    public void votar(View v) {
        startActivity(new Intent(getApplicationContext(), RecomendarCanciones.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void recomendarCanciones(View v) {
        startActivity(new Intent(getApplicationContext(), RecomendarCanciones.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void cambiarRol(View v) {
        startActivity(new Intent(getApplicationContext(), Rol.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void cancionesRecomendadas(View v) {

        drawerLayout.closeDrawers();
    }


    public void salir(View v) {
        startActivity(new Intent(getApplicationContext(), LeerQr.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void cerrarSesion(View v) {
        startActivity(new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

}
