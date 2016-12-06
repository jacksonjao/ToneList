package it.save.tonelist.control;

import android.animation.LayoutTransition;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.save.tonelist.R;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.client.Response;

public class EditarLista extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView tv_listaPrincipal;
    SearchView searchView;
    ImageButton btn_menu;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    List<TrackSimple> tracks;
    ItemAdapter itemAdapter;
    SpotifyApi api;
    String listaCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //controlo el menu desplegable
        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230,0,0,0));

        searchView = (SearchView) findViewById(R.id.sv_buscar);
        int searchBarId = searchView.getContext().getResources().getIdentifier("android:id/search_bar", null, null);
        final LinearLayout searchBar = (LinearLayout) searchView.findViewById(searchBarId);
        searchBar.setLayoutTransition(new LayoutTransition());
        search();

        listaCode = getIntent().getStringExtra("code");
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista);
        tracks = new ArrayList<>();
        itemAdapter = new ItemAdapter(tracks, ItemAdapter.PLUS, listaCode);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        api = new SpotifyApi();
        searchView.setOnQueryTextListener(this);




        validarMenu();
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        SpotifyService spotify = api.getService();
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, 0);
        options.put(SpotifyService.LIMIT, 20);

        spotify.searchTracks(s, options, new SpotifyCallback<TracksPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("SpotifyErr", spotifyError.getMessage());
            }

            @Override
            public void success(TracksPager tracksPager, Response response) {
                tracks.clear();
                tracks.addAll(convertToSimple(tracksPager.tracks.items));
                itemAdapter.notifyDataSetChanged();
                showItems();
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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


    private List<TrackSimple> convertToSimple(List<Track> tracks) {
        List<TrackSimple> tsList = new ArrayList<>();
        TrackSimple temp;
        for (Track t : tracks) {
            temp = new TrackSimple();
            temp.trackId = t.id;
            temp.name = t.name;
            temp.album = t.album.name;
            temp.artist = t.artists.get(0).name;
            temp.imgURL = t.album.images.get(0).url;
            temp.liked = false;

            tsList.add(temp);
        }
        return tsList;
    }

    private void showItems() {
        for (TrackSimple t : tracks) {
            System.out.println(t.trackId + " - " + t.name + " - " + t.album + " - " + t.imgURL);
        }
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
}
