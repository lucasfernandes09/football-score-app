package com.app.app1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.app1.R;
import com.app.app1.model.Jogador;
import com.app.app1.model.Times;

import java.util.ArrayList;
import java.util.List;

public class AdapterJogadores extends RecyclerView.Adapter<AdapterJogadores.MyViewHolder> {

    private List<Jogador> listaDeJogadores;
    private String posicaoJogador;

    public AdapterJogadores(List<Jogador> listaDeJogadores) {
        this.listaDeJogadores = listaDeJogadores;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemDaLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_jogador, parent, false);
        return new AdapterJogadores.MyViewHolder(itemDaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Jogador jogador = listaDeJogadores.get(position);

        holder.player_name.setText(jogador.getPlayer_name());
        holder.player_number.setText("camisa " + jogador.getPlayer_number());

        posicaoJogador = traduzirPosicao(jogador.getPlayer_type());
        holder.player_type.setText(",  " + posicaoJogador);

        holder.player_age.setText(jogador.getPlayer_age() + " anos");
        holder.player_match_played.setText(jogador.getPlayer_match_played() + " jogos");
        holder.player_goals.setText(jogador.getPlayer_goals());
        holder.player_yellow_cards.setText(jogador.getPlayer_yellow_cards());
        holder.player_red_cards.setText(jogador.getPlayer_red_cards());
    }

    @Override
    public int getItemCount() {
        return listaDeJogadores.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView player_name, player_number, player_country, player_type, player_age;
        TextView player_match_played, player_goals, player_yellow_cards, player_red_cards;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            player_name = itemView.findViewById(R.id.tvNomeJogador);
            player_number = itemView.findViewById(R.id.tvNumeroJogador);
            player_type = itemView.findViewById(R.id.tvPosicaoJogador);
            player_age = itemView.findViewById(R.id.tvIdadeJogador);
            player_match_played = itemView.findViewById(R.id.tvPartidasJogadadas);
            player_goals = itemView.findViewById(R.id.tvGolsJogador);
            player_yellow_cards = itemView.findViewById(R.id.tvAmareloJogador);
            player_red_cards = itemView.findViewById(R.id.tvVermelhoJogador);
        }
    }

    public String traduzirPosicao(String posicaoJogador) {
        switch (posicaoJogador) {
            case "Goalkeepers":
                return "goleiro";
            case "Defenders":
                return "defensor";
            case "Midfielders":
                return "meia";
            case "Forwards":
                return "atacante";
            default:
                return "";
        }
    }
}
