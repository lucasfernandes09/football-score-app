package com.app.app1.adapters;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.app1.helper.AcoesDoJogo;
import com.app.app1.R;

import java.util.List;

public class AdapterAcoesDeJogo extends RecyclerView.Adapter<AdapterAcoesDeJogo.MyViewHolder>{
    private List<AcoesDoJogo> listaAcoesDeJogo;
    private String nomeAcoes;
    private int icon = 0;


    public AdapterAcoesDeJogo(List<AcoesDoJogo> listaAcoesDeJogo) {
        this.listaAcoesDeJogo = listaAcoesDeJogo;
    }

    @NonNull
    @Override
    public AdapterAcoesDeJogo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemDaLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_acoesdejogo, parent, false);
        return new AdapterAcoesDeJogo.MyViewHolder(itemDaLista);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterAcoesDeJogo.MyViewHolder holder, int position) {
        AcoesDoJogo acoesDoJogo = this.listaAcoesDeJogo.get(position);

        //atribuir icone
        switch (acoesDoJogo.getType()) {
            case "marcadores":
                icon = R.drawable.ic_bola;
                break;
            case "cartaoVermelho":
                icon = R.drawable.ic_cartao_vermelho;
                holder.tvNomeAcoesDeJogo.setTextColor(Color.RED);
                break;
            /*case "substituição":
                icon = R.drawable.ic_menu_share;*/
        }

        //separar casa/vis e definir icone
        if(acoesDoJogo.isHome()) {
            holder.tvNomeAcoesDeJogo.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
            nomeAcoes = "   " + acoesDoJogo.getTime() + "'" + "  " + acoesDoJogo.getName();
        }else {
            holder.tvNomeAcoesDeJogo.setCompoundDrawablesWithIntrinsicBounds(0,0, icon,0);
            holder.tvNomeAcoesDeJogo.setGravity(Gravity.END);
            nomeAcoes = acoesDoJogo.getName() + "  " + acoesDoJogo.getTime() + "'   ";
        }
        holder.tvNomeAcoesDeJogo.setText(nomeAcoes);
    }

    @Override
    public int getItemCount() {
        return this.listaAcoesDeJogo.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeAcoesDeJogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeAcoesDeJogo = itemView.findViewById(R.id.tvNomeAcoesDeJogo);
        }
    }

}


