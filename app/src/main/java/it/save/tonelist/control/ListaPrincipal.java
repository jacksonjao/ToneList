package it.save.tonelist.control;

import android.animation.LayoutTransition;
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

import static java.security.AccessController.getContext;

public class ListaPrincipal extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    SearchView searchView;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    List<TrackSimple> trackList;
    ItemAdapter itemAdapter;
    SpotifyApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_principal);
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
        drawerLayout.setScrimColor(Color.argb(230,0,0,0));
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista);
        search();

        //Busqueda en spotify
        trackList = new ArrayList<>();
        itemAdapter = new ItemAdapter(trackList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        api = new SpotifyApi();
        searchView.setOnQueryTextListener(this);
        validarMenu();
    }


    public void menu(View v) {
        drawerLayout.openDrawer(menu);

    }





    public void search() {
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.makeOutAnimation(ListaPrincipal.this, false);
                tv_listaPrincipal.setAnimation(animation);
                tv_listaPrincipal.setVisibility(View.INVISIBLE);
                btn_menu.setAnimation(animation);
                btn_menu.setVisibility(View.INVISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Animation animation = AnimationUtils.makeInAnimation(ListaPrincipal.this, true);
                tv_listaPrincipal.setAnimation(animation);
                tv_listaPrincipal.setVisibility(View.VISIBLE);
                btn_menu.setAnimation(animation);
                btn_menu.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        SpotifyService spotify = api.getService();
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, 0);
        options.put(SpotifyService.LIMIT, 50);

        spotify.searchTracks(s, options, new SpotifyCallback<TracksPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("SpotifyErr", spotifyError.getMessage());
            }

            @Override
            public void success(TracksPager tracksPager, Response response) {
                trackList.clear();
                trackList.addAll(convertToSimple(tracksPager.tracks.items));
                itemAdapter.notifyDataSetChanged();
                showItems();
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("OnQueryText", s);
        return false;
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



    public void validarMenu(){
        tv_listaPrincipal.setText(getResources().getString(R.string.lista_principal));
        TextView textView=(TextView) drawerLayout.findViewById(R.id.tv_votar);
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
}
