package com.app.app1.fragments.equipe;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.adapters.AdapterJogadores;
import com.app.app1.model.Jogador;
import com.app.app1.model.Jogos;
import com.app.app1.model.Times;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EquipeFragment extends Fragment {
    private List<Times> listaDeElenco;
    private List<Jogador> listaDeJogadores;
    private Jogos jogo = new Jogos();
    private Boolean home;
    private ImageView ivBageEquipeFrag, ivBadgePaisEquipe;
    private TextView tvNomeEquipe;
    private RecyclerView rvGoleadores;


    public EquipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipe, container, false);
        //referenciação
        ivBageEquipeFrag = view.findViewById(R.id.ivBadgeEquipeFrag); ivBadgePaisEquipe = view.findViewById(R.id.ivBadgePaisEquipe);
        tvNomeEquipe = view.findViewById(R.id.tvNomeEquipe);
        rvGoleadores = view.findViewById(R.id.rvGoleadores);

        //recuperar objetos enviados de EquipeActivity
        jogo = getArguments().getParcelable("jogo");
        home = getArguments().getBoolean("home");

        configBasicas(home);

        //receber Elenco
        ElencoAsyncTask elenco = new ElencoAsyncTask();
        elenco.execute();

        return view;
    }

    public class ElencoAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            //verificar lista até chegar
            while(c<1) {
                listaDeElenco = getArguments().getParcelableArrayList("listaDeElenco");
                if (listaDeElenco != null) {
                    c = 1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            artilheiros();
            exibirArtilheiros();
        }
    }

    public void artilheiros() {
        listaDeJogadores = listaDeElenco.get(0).getPlayers();

        //comparar gols
        Comparator<Jogador> comparator = new Comparator<Jogador>() {
            @Override
            public int compare(Jogador j1, Jogador j2) {
                Integer g1 = Integer.parseInt(j1.getPlayer_goals());
                Integer g2 = Integer.parseInt(j2.getPlayer_goals());
                return g1.compareTo(g2);     //compareTo é método das WrapperClasses
            }
        };
        Collections.sort(listaDeJogadores, comparator);
        Collections.reverse(listaDeJogadores);
    }

    public void exibirArtilheiros() {
        AdapterJogadores adapterJogadores = new AdapterJogadores(listaDeJogadores);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGoleadores.setLayoutManager(layoutManager);
        rvGoleadores.setHasFixedSize(true);
        rvGoleadores.setNestedScrollingEnabled(false);
        rvGoleadores.setAdapter(adapterJogadores);
    }

    public void configBasicas(Boolean home) {
        Picasso.get().load(jogo.getCountry_logo()).into(ivBadgePaisEquipe); //só funciona para camps nacionais

        if(home) {
            Picasso.get().load(jogo.getTeam_home_badge()).into(ivBageEquipeFrag);
            tvNomeEquipe.setText(jogo.getMatch_hometeam_name());
        }else {
            Picasso.get().load(jogo.getTeam_away_badge()).into(ivBageEquipeFrag);
            tvNomeEquipe.setText(jogo.getMatch_awayteam_name());
        }
    }

}
