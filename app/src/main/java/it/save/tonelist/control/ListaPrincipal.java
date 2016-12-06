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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.save.tonelist.R;
import kaaes.spotify.webapi.android.models.Track;

public class ListaPrincipal extends AppCompatActivity {
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    SearchView searchView;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    List<TrackSimple> trackList;
    ItemAdapter itemAdapter;
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        //controlo el menu desplegable
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230, 0, 0, 0));
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista);


        if (user != null) {
            // Name, email address, and profile photo Url



            System.out.println(user.getEmail());
        }

        //Busqueda en spotify
        trackList = new ArrayList<>();
        itemAdapter = new ItemAdapter(trackList, ItemAdapter.LIKE, "00000");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        databaseReference = firebase.getReference();

        cargarKeys(getIntent().getStringExtra("code"));
        validarMenu();
    }


    public void menu(View v) {

        if(!user.getEmail().toString().equals("anon@anon.com")){
        drawerLayout.openDrawer(menu);}else{
            Toast.makeText(getApplicationContext(),"Para mas opciones, debes registrarte",Toast.LENGTH_SHORT).show();
        }

    }


    public List<TrackSimple> convertToSimple(List<Track> tracks) {
        List<TrackSimple> tsList = new ArrayList<>();
        TrackSimple temp;
        for (Track t : tracks) {
            temp = new TrackSimple();
            temp.name = t.name;
            temp.album = t.album.name;
            temp.artist = t.artists.get(0).name;
            temp.imgURL = t.album.images.get(0).url;
            tsList.add(temp);
        }
        return tsList;
    }


    public void showItems() {
        for (TrackSimple t : trackList) {
            System.out.println(t.name + "-" + t.album + "-" + t.imgURL);
        }
    }


    public void validarMenu() {
        tv_listaPrincipal.setText(getResources().getString(R.string.lista_principal));
        TextView textView = (TextView) drawerLayout.findViewById(R.id.tv_votar);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
    }

    public void votar(View v) {
        drawerLayout.closeDrawers();
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
        startActivity(new Intent(getApplicationContext(), CancionesRecomendadas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
                        ts.trackId = child.getKey();
                        ts.likes = likes;
                        Log.d("cargaImagen", ts.name + " " + ts.imgURL);
                        trackList.add(ts);
                    }
                }

                itemAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}


