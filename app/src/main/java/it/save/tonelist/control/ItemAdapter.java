package it.save.tonelist.control;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import it.save.tonelist.R;

/**
 * Created by Andres Villegas on 2016-11-25.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public static final int LIKE = 0;
    public static final int PLUS = 1;


    private List<TrackSimple> trackList;
    private int tipo;
    private String codigoFiesta;
    private SubirFirebase firebase;

    public ItemAdapter(List<TrackSimple> trackList, int tipo, String codigoFiesta) {
        this.trackList = trackList;
        this.tipo = tipo;
        this.codigoFiesta = codigoFiesta;
        this.firebase = new SubirFirebase();

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (tipo == LIKE)
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_like, parent, false);
        else
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_add, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int i) {
        final TrackSimple ts = trackList.get(i);
        holder.vName.setText(ts.name);
        holder.vArtirst.setText(ts.artist);
        holder.vAlbum.setText(ts.album);
        holder.image.setImageBitmap(ts.image);
        holder.bLike.setChecked(ts.liked);

        if (tipo == LIKE) {
            holder.bLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToggleButton btn = (ToggleButton) view;
                    ts.liked = btn.isChecked();
                    if (btn.isChecked()) {
                        Log.d("firebaseVote", "Inicio de voto de firebase");
                        firebase.addVoto(codigoFiesta, ts.trackId);
                    }
                }
            });

        } else {
            holder.bLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToggleButton btn = (ToggleButton) view;
                    ts.liked = btn.isChecked();
                    if (btn.isChecked()) {
                        Log.d("firebaseAdd", "Inicio de agregar de firebase");
                        firebase.addCancion(codigoFiesta, ts);
                    }
                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vArtirst;
        protected TextView vAlbum;
        protected ImageView image;
        protected ToggleButton bLike;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vName = (TextView) itemView.findViewById(R.id.tv_cancion);
            vArtirst = (TextView) itemView.findViewById(R.id.tv_artista);
            vAlbum = (TextView) itemView.findViewById(R.id.tv_album);
            image = (ImageView) itemView.findViewById(R.id.im_cover);
            bLike = (ToggleButton) itemView.findViewById(R.id.tb_like);
        }
    }



}
