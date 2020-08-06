package com.app.app1.fragments.main;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.activities.JogosCompeticaoActivity;
import com.app.app1.adapters.AdapterCompeticoes;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CompeticoesFragment extends Fragment implements AdapterCompeticoes.JogoListener {

    private RecyclerView rvCompeticoes;
    private ArrayList<Jogos> listaDeCompeticoes = new ArrayList<>();
    private List<Jogos> listaDeCompeticoesFinal = new ArrayList<>();
    private TextView tvInfoCompet;
    private AdapterCompeticoes adapterCompeticoes;

    public CompeticoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_competicoes, container, false);
        //referenciação
        rvCompeticoes = view.findViewById(R.id.rvCompeticoes);
        tvInfoCompet = view.findViewById(R.id.tvInfoCompet);

        //lista de competições é uma lista de jogos
        listaDeCompeticoes = getArguments().getParcelableArrayList("listaDeJogos");

        verificarLista();

        return view;
    }

    public void verificarLista() {
        if(listaDeCompeticoes != null) {
            organizarListagem(listaDeCompeticoes);
            exibirCompeticoes();
        }
    }

    public void organizarListagem(List<Jogos> listaDeCompeticoes) {
        listaDeCompeticoesFinal = new ArrayList<>();

        for (int i = 0; i < listaDeCompeticoes.size(); i++) {
            listaDeCompeticoes.get(i).setQtdCompeticao(1);
            for (int j = i + 1; j < listaDeCompeticoes.size(); j++) {
                if (listaDeCompeticoes.get(i).getLeague_name().equals(listaDeCompeticoes.get(j).getLeague_name())) {
                    //qtd exibida no recycler
                    listaDeCompeticoes.get(j).setAuxiliar1(1);
                    //qtd de partidas das competições
                    listaDeCompeticoes.get(i).setQtdCompeticao(listaDeCompeticoes.get(i).getQtdCompeticao() + 1);
                }
            }
        }
        for (int i = 0; i < listaDeCompeticoes.size(); i++) {
            if (listaDeCompeticoes.get(i).getAuxiliar1() != 1) {
                listaDeCompeticoesFinal.add(listaDeCompeticoes.get(i));
            }
        }
        comparator();
    }

    public void comparator() {
        Comparator<Jogos> comparator = new Comparator<Jogos>() {
            @Override
            public int compare(Jogos j1, Jogos j2) {
                return j1.getLeague_name().compareTo(j2.getLeague_name());
            }
        };
        Collections.sort(listaDeCompeticoesFinal, comparator);
    }

    public void exibirCompeticoes() {
        adapterCompeticoes = new AdapterCompeticoes(listaDeCompeticoesFinal, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCompeticoes.setLayoutManager(layoutManager);
        rvCompeticoes.setHasFixedSize(true);
        rvCompeticoes.setAdapter(adapterCompeticoes);
    }

    public void atualizarLista(List<Jogos> listaDeJogos) {
        listaDeCompeticoes = (ArrayList<Jogos>) listaDeJogos;
        listaDeCompeticoesFinal.clear();
        organizarListagem(listaDeCompeticoes);
        adapterCompeticoes.notifyDataSetChanged();
    }

    @Override
    public void jogoClick(int position) {
        //enviar apenas jogos desta competição clicada
        int league_id = listaDeCompeticoesFinal.get(position).getLeague_id();
        ArrayList<Jogos> listaDeJogosDeUmaCompeticao = new ArrayList<>();
        for (int i = 0; i < listaDeCompeticoes.size(); i++) {
            if (listaDeCompeticoes.get(i).getLeague_id() == league_id) {
                listaDeJogosDeUmaCompeticao.add(listaDeCompeticoes.get(i));
            }
        }
        Intent intent = new Intent(getContext(), JogosCompeticaoActivity.class);
        intent.putParcelableArrayListExtra("listaDeJogosDeUmaCompeticao", listaDeJogosDeUmaCompeticao);
        startActivity(intent);
    }


    public class CompeticoesAoVivo extends AsyncTask<Void, Void, Void> {
        private List<Jogos> listaDeJogosAoVivo = new ArrayList<>();
        private boolean aoVivo;

        public CompeticoesAoVivo(boolean aoVivo) {
            this.aoVivo = aoVivo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listaDeCompeticoesFinal.clear();
            if(aoVivo) {
                //competições ao vivo
                for(Jogos jogo : listaDeCompeticoes) {
                    if(jogo.getMatch_live().equals("1")) {
                        listaDeJogosAoVivo.add(jogo);
                    }
                }
                organizarListagem(listaDeJogosAoVivo);
            }else {
                organizarListagem(listaDeCompeticoes);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            rvCompeticoes.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(aoVivo) {
                if(listaDeJogosAoVivo.isEmpty()) {
                    tvInfoCompet.setVisibility(View.VISIBLE);
                }else {
                    tvInfoCompet.setVisibility(View.GONE);
                    rvCompeticoes.setAdapter(new AdapterCompeticoes(listaDeCompeticoesFinal, CompeticoesFragment.this));
                }
            }else {
                rvCompeticoes.setAdapter(new AdapterCompeticoes(listaDeCompeticoesFinal, CompeticoesFragment.this));
            }

            rvCompeticoes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("info", "onPause CompeticoesFrag"  + " <<");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("info", "onStop CompeticoesFrag"  + " <<");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("info", "onDestroyView CompeticoesFrag"  + " <<");
    }
}
