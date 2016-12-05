package it.save.tonelist.control;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.save.tonelist.R;

/**
 * Created by Andres Villegas on 2016-11-25.
 */

public class VotadasAdapter extends RecyclerView.Adapter<VotadasAdapter.VotadasViewHolder> {

    public static final int LIKE = 0;
    public static final int PLUS = 1;


    private List<TrackSimple> trackList;


    public VotadasAdapter(List<TrackSimple> trackList) {
        this.trackList = trackList;


    }

    @Override
    public VotadasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_ver_votadas, parent, false);


        return new VotadasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VotadasViewHolder holder, final int i) {
        final TrackSimple ts = trackList.get(i);
        holder.vName.setText(ts.name);
        holder.vArtirst.setText(ts.artist);
        holder.vAlbum.setText(ts.album);
        holder.image.setImageBitmap(ts.image);
        holder.vLikes.setText(ts.likes + "");


    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }


    public class VotadasViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vArtirst;
        protected TextView vAlbum;
        protected ImageView image;
        protected TextView vLikes;

        public VotadasViewHolder(View itemView) {
            super(itemView);
            vName = (TextView) itemView.findViewById(R.id.tv_cancion);
            vArtirst = (TextView) itemView.findViewById(R.id.tv_artista);
            vAlbum = (TextView) itemView.findViewById(R.id.tv_album);
            image = (ImageView) itemView.findViewById(R.id.im_cover);
            vLikes = (TextView) itemView.findViewById(R.id.tv_likes);
        }
    }


}
