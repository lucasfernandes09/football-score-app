package com.app.app1.fragments.equipe;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.app1.R;
import com.app.app1.activities.JogoActivity;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;

import java.util.ArrayList;
import java.util.List;


public class PartidasFragment extends Fragment implements AdapterJogos.JogoListener {
    private List<Jogos> listaDeJogos, listaDeUltimosJogos, listaDeProximosJogos;
    private RecyclerView rvPartidas;
    private AdapterJogos adapterJogos;
    private Button btnUltimas, btnProximas;
    private Jogos jogo;
    private Boolean home;
    private String nomeEquipe;

    public PartidasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partidas, container, false);
        //referenciação
        rvPartidas = view.findViewById(R.id.rvPartidas);
        btnUltimas = view.findViewById(R.id.btnUltimas);
        btnProximas = view.findViewById(R.id.btnProximas);

        //objetos recebidos de equipeActivity
        nomeEquipe = getArguments().getString("nomeEquipe");
        home = getArguments().getBoolean("home");

        //receber Ultimos Jogos
        PartidasAsyncTask receberPartidas = new PartidasAsyncTask();
        receberPartidas.execute();

        return view;
    }

    public class PartidasAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            while(c < 1) {
                listaDeUltimosJogos = getArguments().getParcelableArrayList("listaDeUltimosJogos");
                listaDeProximosJogos = getArguments().getParcelableArrayList("listaDeProximosJogos");
                if(listaDeUltimosJogos != null && listaDeProximosJogos != null) {
                    c = 1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listaDeJogos = listaDeUltimosJogos;
            exibirPartidas();

            //clique últimas partidas
            btnUltimas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listaDeJogos = listaDeUltimosJogos;
                    rvPartidas.setAdapter(new AdapterJogos(listaDeUltimosJogos, PartidasFragment.this, nomeEquipe));
                    btnUltimas.setBackgroundResource(R.drawable.background_2);
                    btnProximas.setBackgroundResource(R.drawable.background_click);
                }
            });

            //clique próximas partidas
            btnProximas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listaDeJogos = listaDeProximosJogos;
                    rvPartidas.setAdapter(new AdapterJogos(listaDeProximosJogos, PartidasFragment.this, nomeEquipe));
                    btnProximas.setBackgroundResource(R.drawable.background_2);
                    btnUltimas.setBackgroundResource(R.drawable.background_click);
                }
            });
        }
    }

    public void exibirPartidas() {
        adapterJogos = new AdapterJogos(listaDeUltimosJogos, this, nomeEquipe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPartidas.setLayoutManager(layoutManager);
        rvPartidas.setNestedScrollingEnabled(false);
        rvPartidas.setHasFixedSize(true);
        rvPartidas.setAdapter(adapterJogos);
    }

    @Override
    public void jogoClick(int position) {
        jogo = listaDeJogos.get(position);
        Intent intent = new Intent(getContext(), JogoActivity.class);
        intent.putExtra("jogo", jogo);
        startActivity(intent);
    }

}
