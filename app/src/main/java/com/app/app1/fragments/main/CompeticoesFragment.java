package com.app.app1.fragments.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.activities.JogosCompeticaoActivity;
import com.app.app1.adapters.AdapterCompeticoes;
import com.app.app1.model.Jogos;

import java.util.ArrayList;
import java.util.List;


public class CompeticoesFragment extends Fragment implements AdapterCompeticoes.JogoListener {

    private RecyclerView rvCompeticoes;
    private ArrayList<Jogos> listaDeCompeticoes = new ArrayList<>();
    private List<Jogos> listaDeCompeticoesFinal = new ArrayList<>();
    private TextView tvSemCompeticao;

    public CompeticoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_competicoes, container, false);
        //referenciação
        rvCompeticoes = view.findViewById(R.id.rvCompeticoes);
        tvSemCompeticao = view.findViewById(R.id.tvSemCompeticao);

        //lista de competições é uma lista de jogos
        listaDeCompeticoes = getArguments().getParcelableArrayList("listaDeJogos");

        verificarLista();

        return view;
    }

    public void verificarLista() {
        if(listaDeCompeticoes.isEmpty()){
            tvSemCompeticao.setVisibility(View.VISIBLE);
        }else {
            organizarListagem();
            exibirCompeticoes();
        }
    }

    public void organizarListagem() {
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
    }

    public void exibirCompeticoes() {
        AdapterCompeticoes adapterCompeticoes = new AdapterCompeticoes(listaDeCompeticoesFinal, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCompeticoes.setLayoutManager(layoutManager);
        rvCompeticoes.setHasFixedSize(true);
        rvCompeticoes.setAdapter(adapterCompeticoes);
    }

    public void atualizarLista(List<Jogos> listaDeJogos) {
        listaDeCompeticoes = (ArrayList<Jogos>) listaDeJogos;
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
}
