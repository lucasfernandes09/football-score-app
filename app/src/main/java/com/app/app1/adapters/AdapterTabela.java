package com.app.app1.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.app1.R;
import com.app.app1.model.Jogos;
import com.app.app1.model.Tabela;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTabela extends RecyclerView.Adapter<AdapterTabela.MyViewHolder> {
    private List<Tabela> listaTabela;
    //private List<Jogos> listaDeJogos;

    public AdapterTabela(List<Tabela> listaTabela/*, List<Jogos> listaDeJogos*/) {
        this.listaTabela = listaTabela;
        //this.listaDeJogos = listaDeJogos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemDaLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tabela, parent, false);
        return new AdapterTabela.MyViewHolder(itemDaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tabela tabela = this.listaTabela.get(position);

        //tabelaAoVivo(holder, tabela);
        zonas(holder, tabela);
        holder.overall_league_position.setText(tabela.getOverall_league_position());
        holder.overall_league_payed.setText(tabela.getOverall_league_payed());
        holder.team_name.setText(tabela.getTeam_name());
        holder.overall_league_PTS.setText(tabela.getOverall_league_PTS());
        holder.overall_league_W.setText(tabela.getOverall_league_W());
        holder.overall_league_D.setText(tabela.getOverall_league_D());
        holder.overall_league_L.setText(tabela.getOverall_league_L());
        holder.overall_league_Gols.setText(tabela.getOverall_league_GF() + "-" +
                tabela.getOverall_league_GA());
        Picasso.get().load(tabela.getTeam_badge()).into(holder.ivBadgeTabela);

    }

    @Override
    public int getItemCount() {
        return this.listaTabela.size();
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
        TextView overall_league_position;
        TextView overall_league_payed;
        TextView team_name;
        TextView overall_league_PTS;
        TextView overall_league_W;
        TextView overall_league_D;
        TextView overall_league_L;
        TextView overall_league_Gols;
        ImageView ivBadgeTabela;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            overall_league_position = itemView.findViewById(R.id.tvPosicao);
            overall_league_payed = itemView.findViewById(R.id.tvQtdJogos);
            team_name = itemView.findViewById(R.id.tvNomeTabela);
            overall_league_PTS = itemView.findViewById(R.id.tvPTS);
            overall_league_W = itemView.findViewById(R.id.tvV);
            overall_league_D = itemView.findViewById(R.id.tvE);
            overall_league_L = itemView.findViewById(R.id.tvD);
            overall_league_Gols = itemView.findViewById(R.id.tvSaldoGols);
            ivBadgeTabela = itemView.findViewById(R.id.ivBadgeTabela);
        }
    }

    /*private void tabelaAoVivo(MyViewHolder holder, Tabela tabela) {
        for(int i=0; i<listaDeJogos.size(); i++){
            if(listaDeJogos.get(i).getMatch_live().equals("1")) { //verificar partidas ao vivo

                if(listaDeJogos.get(i).getMatch_hometeam_name().equals(tabela.getTeam_name()) || //achar os times dessas partidas na tabela
                        listaDeJogos.get(i).getMatch_awayteam_name().equals(tabela.getTeam_name())){

                            holder.team_name.setTextColor(Color.GREEN);
                }
            }else {
                holder.team_name.setTextColor(Color.BLACK);
            }
        }
    }*/

    public void zonas(MyViewHolder holder, Tabela tabela) {
        String promocao = tabela.getOverall_promotion();
        if(promocao.contains("Promotion") && !promocao.contains("(")) {    //classificação
            holder.overall_league_position.setBackgroundColor(Color.GREEN);
        }else {
            if(promocao.contains("Promotion") && promocao.contains("(")) {  //disputa de classificação
                holder.overall_league_position.setBackgroundColor(Color.YELLOW);
            }
        }

        if(promocao.contains("(Relegation)")) {
            Log.i("info", "disputa de rebaixamento");
            holder.overall_league_position.setBackgroundColor(Color.BLUE);  //disputa de rebaixamento
        }else {
            if(promocao.contains("Relegation") && !promocao.contains("(")) {   //rebaixamento
                Log.i("info", "rebaixamento");
                holder.overall_league_position.setBackgroundColor(Color.RED);
            }
        }

    }
}
