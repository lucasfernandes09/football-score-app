package com.app.app1.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.app1.R;
import com.app.app1.helper.UsuarioFirebase;
import com.app.app1.model.Jogos;

import java.util.List;

public class AdapterJogos extends RecyclerView.Adapter<AdapterJogos.MyViewHolder> {

    private List<Jogos> listaDeJogos;
    private JogoListener jogoListener;
    private String nomeEquipe;
    private Typeface bold = Typeface.defaultFromStyle(Typeface.BOLD);
    private Boolean retrospecto = false;
    private String hora;


    public AdapterJogos(List<Jogos> listaDeJogos, JogoListener jogoListener, String nomeEquipe) {
        this.listaDeJogos = listaDeJogos;
        this.jogoListener = jogoListener;
        this.nomeEquipe = nomeEquipe;
        this.retrospecto = true;
    }

    public AdapterJogos(List<Jogos> listaDeJogos, JogoListener jogoListener) {
        this.listaDeJogos = listaDeJogos;
        this.jogoListener = jogoListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemDaLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_jogos, parent, false);
        return new MyViewHolder(itemDaLista, jogoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Jogos jogo = this.listaDeJogos.get(position);

        holder.match_hometeam_name.setText(jogo.getMatch_hometeam_name());
        holder.match_awayteam_name.setText(jogo.getMatch_awayteam_name());

        seAoVivo(holder, jogo);

        checkBox(holder, jogo);
    }

    @Override
    public int getItemCount() {
        return this.listaDeJogos.size();
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
        TextView match_time, match_hometeam_name, match_awayteam_name, match_hometeam_score, match_awayteam_score;
        CheckBox cbSalvarJogo;
        JogoListener jogoListener;

        public MyViewHolder(@NonNull View itemView, JogoListener jogoListener) {
            super(itemView);
            match_time = itemView.findViewById(R.id.tvHora);
            match_hometeam_name = itemView.findViewById(R.id.tvCasa);
            match_awayteam_name = itemView.findViewById(R.id.tvVisitante);
            match_hometeam_score = itemView.findViewById(R.id.tv1);
            match_awayteam_score = itemView.findViewById(R.id.tv2);
            cbSalvarJogo = itemView.findViewById(R.id.cbSalvarJogo);
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

    public void checkBox(MyViewHolder holder, Jogos jogo) {
        holder.cbSalvarJogo.setOnCheckedChangeListener(null);
        holder.cbSalvarJogo.setChecked(jogo.getSelecionado());
        holder.cbSalvarJogo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(UsuarioFirebase.getUsuarioAtual() == null) {
                    Toast.makeText(holder.cbSalvarJogo.getContext(), "Faça login para salvar partidas.", Toast.LENGTH_SHORT).show();
                    holder.cbSalvarJogo.setChecked(false);
                }else {
                    jogo.setSelecionado(checked);
                    //atualiza lista de jogos salvos
                    if(jogo.getSelecionado()) {
                        jogo.salvarJogo();
                    }else {
                        jogo.removerJogoSalvo();
                    }
                }
            }
        });
    }

    public void seAoVivo(MyViewHolder holder, Jogos jogo) {
        if(jogo.getMatch_live().equals("1")) {
            configLabelTempo(holder, jogo);
            holder.match_hometeam_score.setText(jogo.getMatch_hometeam_score());
            holder.match_hometeam_score.setTypeface(bold);
            holder.match_awayteam_score.setText(jogo.getMatch_awayteam_score());
            holder.match_awayteam_score.setTypeface(bold);
        }else {
            holder.match_time.setText(jogo.getMatch_time());
            holder.match_hometeam_score.setText(jogo.getMatch_hometeam_score());
            holder.match_awayteam_score.setText(jogo.getMatch_awayteam_score());

            switch (jogo.getMatch_status()) {
                case "Finished":
                    //faz alterações caso seja uma lista de retrospecto
                    if(retrospecto) {
                        holder.cbSalvarJogo.setClickable(false);
                        setarDrawables(holder, jogo);
                    }else {
                        holder.cbSalvarJogo.setClickable(true);
                        holder.match_hometeam_score.setText(jogo.getMatch_hometeam_score());
                        holder.match_awayteam_score.setText(jogo.getMatch_awayteam_score());
                    }
                    break;

                case "Postponed":
                    holder.match_awayteam_score.setText("Adiado");
                    holder.match_hometeam_score.setText("");
                    break;

                default:
                    holder.match_hometeam_score.setText(jogo.getMatch_hometeam_score());
                    holder.match_awayteam_score.setText(jogo.getMatch_awayteam_score());
            }
        }
    }

    public void configLabelTempo(MyViewHolder holder, Jogos jogo) {
        if(jogo.getMatch_status().equals("Half Time")) {
            holder.match_time.setText("Intervalo");
            holder.match_time.setTextColor(Color.GREEN);
        }else{
            if(jogo.getMatch_status().equals("Finished")) {
                holder.match_time.setText("Final");
            }else if (jogo.getMatch_status().contains("Extra")) {
                holder.match_time.setText(jogo.getMatch_status().replace("Extra time", "") + "'");
            }else if (jogo.getMatch_status().contains("Break")) {
                holder.match_time.setText("Prorrog.");
            }else if(jogo.getMatch_status().contains("Penal")) {
                holder.match_time.setText("Penais");
            } else{
                holder.match_time.setText(jogo.getMatch_status() + "'");
                holder.match_time.setTextColor(Color.GREEN);
            }
        }
    }

    public void configRetrospecto(MyViewHolder holder, Jogos jogo) {
        if(jogo.getMatch_status().equals("Finished")) {
            holder.cbSalvarJogo.setClickable(false);
            setarDrawables(holder, jogo);
        }

        if(jogo.getMatch_status().equals("Postponed")) {
            holder.cbSalvarJogo.setVisibility(View.GONE);
            holder.match_time.setText("Adiado");
        }
    }

    public void setarDrawables(MyViewHolder holder, Jogos jogo) {
        if(jogo.getMatch_hometeam_name().equals(nomeEquipe)) {
            //se ganhou
            if (Integer.parseInt(jogo.getMatch_hometeam_score()) > Integer.parseInt(jogo.getMatch_awayteam_score())) {
                holder.cbSalvarJogo.setButtonDrawable(R.drawable.ic_vitoria);
            }else {
                //se enpatou
                if(Integer.parseInt(jogo.getMatch_hometeam_score()) == Integer.parseInt(jogo.getMatch_awayteam_score())) {
                    holder.cbSalvarJogo.setButtonDrawable(R.drawable.ic_empate);
                }else {
                    //se perdeu
                    holder.cbSalvarJogo.setButtonDrawable(R.drawable.ic_derrota);
                }
            }
        }else {
            //se ganhou
            if (Integer.parseInt(jogo.getMatch_awayteam_score()) > Integer.parseInt(jogo.getMatch_hometeam_score())) {
                holder.cbSalvarJogo.setButtonDrawable(R.drawable.ic_vitoria);
            }else {
                //se enpatou
                if(Integer.parseInt(jogo.getMatch_awayteam_score()) == Integer.parseInt(jogo.getMatch_hometeam_score())) {
                    holder.cbSalvarJogo.setButtonDrawable(R.drawable.ic_empate);
                }else {
                    //se perdeu
                    holder.cbSalvarJogo.setButtonDrawable(R.drawable.ic_derrota);
                }
            }
        }
    }


}
