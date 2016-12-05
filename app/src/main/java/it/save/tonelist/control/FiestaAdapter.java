package it.save.tonelist.control;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import it.save.tonelist.R;

/**
 * Created by Andres Villegas on 2016-12-04.
 */

public class FiestaAdapter extends RecyclerView.Adapter<FiestaAdapter.FiestaViewHolder> {

    List<FiestaSimple> listaFiestas;

    public FiestaAdapter(List<FiestaSimple> listaFiestas) {
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
        holder.imagen.setImageBitmap(fs.img);
        holder.codigo.setText(fs.code);
        holder.direccion.setText("Avenida siempre viva 123");
        holder.fecha.setText(new Date(fs.creationDate).toString());
        holder.evento.setText(fs.name);


    }

    @Override
    public int getItemCount() {
        return listaFiestas.size();
    }

    public class FiestaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView evento;
        TextView fecha;
        TextView direccion;
        TextView codigo;


        public FiestaViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.im_cover);
            evento = (TextView) itemView.findViewById(R.id.tv_evento);
            fecha = (TextView) itemView.findViewById(R.id.tv_fecha);
            direccion = (TextView) itemView.findViewById(R.id.tv_direccion);
            codigo = (TextView) itemView.findViewById(R.id.tv_codigo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Fiesta.class);
                    intent.putExtra("name", evento.getText().toString());
                    intent.putExtra("code", codigo.getText().toString());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
