package com.app.app1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.app1.R;
import com.app.app1.model.Jogos;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCompeticoes extends RecyclerView.Adapter<AdapterCompeticoes.MyViewHolder>{
    private List<Jogos> listaDeCompeticoes;
    private JogoListener jogoListener;

    public AdapterCompeticoes(List<Jogos> listaDeCompeticoes, JogoListener jogoListener) {
        this.listaDeCompeticoes = listaDeCompeticoes;
        this.jogoListener = jogoListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemDalista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_competicoes, parent, false);
        return new MyViewHolder(itemDalista, jogoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Jogos competicao = this.listaDeCompeticoes.get(position);
        holder.tvNomeCompeticao.setText(competicao.getLeague_name());
        holder.tvQtdCompeticao.setText(String.valueOf(competicao.getQtdCompeticao()));
        if(!competicao.getCountry_logo().isEmpty()) {
            Picasso.get().load(competicao.getCountry_logo()).into(holder.ivCountry);
        }
    }

    @Override
    public int getItemCount() {
        return this.listaDeCompeticoes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNomeCompeticao;
        TextView tvQtdCompeticao;
        ImageView ivCountry;
        JogoListener jogoListener;

        public MyViewHolder(@NonNull View itemView, JogoListener jogoListener) {
            super(itemView);
            tvNomeCompeticao = itemView.findViewById(R.id.tvNomeCompeticao);
            tvQtdCompeticao = itemView.findViewById(R.id.tvQtdCompeticao);
            ivCountry = itemView.findViewById(R.id.ivCountry);
            this.jogoListener = jogoListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            jogoListener.jogoClick(getAdapterPosition());
        }
    }

    public interface JogoListener {
        void jogoClick(int position);
    }

}
