package it.save.tonelist.control;

import android.support.v7.widget.RecyclerView;
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

    private List<TrackSimple> trackList;

    public ItemAdapter(List<TrackSimple> trackList) {
        this.trackList = trackList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_like, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int i) {
        TrackSimple ts = trackList.get(i);
        holder.vName.setText(ts.name);
        holder.vArtirst.setText(ts.artist);
        holder.vAlbum.setText(ts.album);
        new DownloadImageTask(holder.image).execute(ts.imgURL);
        holder.bLike.setChecked(false);
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
