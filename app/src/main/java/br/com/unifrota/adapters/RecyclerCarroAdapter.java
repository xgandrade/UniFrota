package br.com.unifrota.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.unifrota.R;
import br.com.unifrota.pojo.Carro;

public class RecyclerCarroAdapter extends RecyclerView.Adapter<RecyclerCarroAdapter.ViewHolder> {

    Context contexto;
    ArrayList<Carro> carros;

    public RecyclerCarroAdapter(Context contexto, ArrayList<Carro> carros) {
        this.carros = carros;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvModelo.setText(carros.get(position).getModelo());
        holder.tvCor.setText(carros.get(position).getCor());
        holder.checkAlugado.setChecked(carros.get(position).isAlugado());
        holder.tvPlaca.setText(carros.get(position).getPlaca());

        //seta a imagem do fabricante
        String marca = carros.get(position).getFabricante().toLowerCase();
        int resourceID = contexto.getResources().getIdentifier(marca, "drawable", contexto.getPackageName());
        if (resourceID == 0) {
            holder.imgLogo.setImageResource(R.drawable.desconhecido);
        } else {
            holder.imgLogo.setImageResource(resourceID);
        }
    }


    @Override
    public int getItemCount() {
        return carros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLogo;
        TextView tvModelo, tvPlaca, tvCor;
        CheckBox checkAlugado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvPlaca = itemView.findViewById(R.id.tvPlaca);
            tvCor = itemView.findViewById(R.id.tvCor);
            checkAlugado = itemView.findViewById(R.id.checkAlugado);
        }
    }
}
