package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.save.tonelist.R;

public class VerVotadas extends AppCompatActivity {
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    List<TrackSimple> trackList;
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    VotadasAdapter votadasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_votadas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //controlo el menu desplegable
        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230, 0, 0, 0));

        recyclerView = (RecyclerView) findViewById(R.id.rv_lista);
        trackList = new ArrayList<>();
        votadasAdapter = new VotadasAdapter(trackList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(votadasAdapter);
        databaseReference = firebase.getReference();

        validarMenu();
        cargarKeys(getIntent().getStringExtra("code"));
    }


    public void menu(View v) {
        drawerLayout.openDrawer(menu);

    }

    public void validarMenu() {
        tv_listaPrincipal.setText(getIntent().getStringExtra("name"));
    }

    public void misFiestas(View v) {


        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();

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


    public void nuestrosAliados(View v){
        Toast.makeText(getApplicationContext(),"Actividad en desarrollo",Toast.LENGTH_SHORT).show();
    }

    public void contacto(View v){
        Toast.makeText(getApplicationContext(),"Actividad en desarrollo",Toast.LENGTH_SHORT).show();
    }

    public void nosotros(View v){
        Toast.makeText(getApplicationContext(),"Actividad en desarrollo",Toast.LENGTH_SHORT).show();
    }

    public void cargarKeys(String code) {
        databaseReference.child("lists").child(code).child("songs").addListenerForSingleValueEvent(new ValueEventListener() {
            HashMap<String, TrackSimple> tracksHash = new HashMap<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    TrackSimple temp = new TrackSimple();
                    temp.trackId = child.getKey();
                    temp.likes = (long) child.getValue();
                    tracksHash.put(child.getKey(), temp);
                }
                cargarCanciones(tracksHash);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("cargarKeys", "Error al cargar las keys");
            }
        });

    }

    public void cargarCanciones(final HashMap<String, TrackSimple> tracksHash) {
        databaseReference.child("songs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (tracksHash.containsKey(child.getKey())) {
                        TrackSimple ts = tracksHash.get(child.getKey());
                        long likes = ts.likes;
                        ts = child.getValue(TrackSimple.class);
                        ts.likes = likes;
                        Log.d("cargaImagen", ts.name + " " + ts.imgURL);
                        trackList.add(ts);
                    }
                }

                votadasAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
