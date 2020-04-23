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
import android.widget.AdapterView;

import com.app.app1.R;
import com.app.app1.RecyclerItemClickListener;
import com.app.app1.RetrofitService;
import com.app.app1.activities.ListaDeJogosActivity;
import com.app.app1.adapters.AdapterCompeticoes;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;
import com.app.app1.model.Marcadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompeticoesFragment extends Fragment implements AdapterCompeticoes.JogoListener {
    private RecyclerView rvCompeticoes;
    private ArrayList<Jogos> listaDeCompeticoes = new ArrayList<>();
    private List<Jogos> listaDeCompeticoesFinal = new ArrayList<>();


    public CompeticoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_competicoes, container, false);
        //referenciação
        rvCompeticoes = view.findViewById(R.id.rvCompeticoes);

        //lista de competições é uma lista de jogos
        listaDeCompeticoes = getArguments().getParcelableArrayList("listaDeJogos");

        verificarLista();

        return view;
    }

    public void verificarLista() {
        if(listaDeCompeticoes != null) {
            organizarListagem();
            exibirCompeticoes();
        }else {
            ReceberJogosAsyncTask receberJogos = new ReceberJogosAsyncTask();
            receberJogos.execute();
        }
    }

    public class ReceberJogosAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            while(c < 1) {
                listaDeCompeticoes = getArguments().getParcelableArrayList("listaDeJogos");
                if(listaDeCompeticoes != null) {
                    c = 1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
        //intent
        Intent intent = new Intent(getContext(), ListaDeJogosActivity.class);
        intent.putParcelableArrayListExtra("listaDeJogosDeUmaCompeticao", listaDeJogosDeUmaCompeticao);
        startActivity(intent);
    }
}
