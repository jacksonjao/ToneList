package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.save.tonelist.R;

public class MisFiestas extends AppCompatActivity {
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    List<FiestaSimple> fiestas;
    FiestaAdapter fiestasAdapter;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference listsReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_fiestas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        //controlo el menu desplegable
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230,0,0,0));

        recyclerView = (RecyclerView) findViewById(R.id.rv_lista);
        fiestas = new ArrayList<>();
        fiestasAdapter = new FiestaAdapter(fiestas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(fiestasAdapter);
        validarMenu();

        database = FirebaseDatabase.getInstance();
        listsReference = database.getReference().child("lists");
        cargarUsuario();
    }


    public void cargarUsuario() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d("cargarUsuario", "Va a cargar las canciones");
            cargarCanciones();

        }
    }

    public void cargarCanciones() {
        Query cancionesUser = listsReference.orderByChild("creator").equalTo(user.getEmail());
        cancionesUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    FiestaSimple temp = child.getValue(FiestaSimple.class);
                    temp.code = child.getKey();
                    Log.d("cargarListas", temp.name);
                    Log.d("cargarListas", temp.creationDate + "");
                    Log.d("cargarListas", temp.songs.size() + "");
                    fiestas.add(temp);


                }
                fiestasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("cargarCanciones", "error cargando las canciones");
                Log.e("cargarCanciones", databaseError.getMessage());

            }
        });


    }


    public void addFiesta(View v){
        startActivity(new Intent(getApplicationContext(), FiestaAdd.class));

    }


    public void menu(View v) {
        drawerLayout.openDrawer(menu);
    }



    public void validarMenu(){
        tv_listaPrincipal.setText(getResources().getString(R.string.recomendar_canciones));
        TextView textView=(TextView) drawerLayout.findViewById(R.id.mis_fiestas);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
    }

    public void misFiestas(View v){
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

    public void cambiarRol(View v) {
        startActivity(new Intent(getApplicationContext(), Rol.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }



}
