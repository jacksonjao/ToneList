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
 * Created by Andres Villegas on 2016-12-04.
 */

public class FiestaAdapter extends RecyclerView.Adapter<FiestaAdapter.FiestaViewHolder>  {

    List<FiestaSimple> listaFiestas;

    public FiestaAdapter(List<FiestaSimple> listaFiestas){
        this.listaFiestas = listaFiestas;
    }

    @Override
    public FiestaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View fiestaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_fiesta, parent, false);
        return new FiestaViewHolder(fiestaView);
    }

    @Override
    public void onBindViewHolder(FiestaViewHolder holder, int position) {
        FiestaSimple fs = listaFiestas.get(position);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FiestaViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView evento;
        TextView fecha;
        TextView direccion;
        TextView codigo;


        public FiestaViewHolder(View itemView) {
            super(itemView);
        }
    }
}
