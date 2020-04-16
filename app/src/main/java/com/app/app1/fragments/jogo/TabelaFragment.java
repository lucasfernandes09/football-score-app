package com.app.app1.fragments.jogo;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.app1.R;
import com.app.app1.adapters.AdapterTabela;
import com.app.app1.model.Jogos;
import com.app.app1.model.Tabela;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabelaFragment extends Fragment {
    private List<Tabela> listaTabela = new ArrayList<>();
    private List<Jogos> listaDeJogosDaCompeticao = new ArrayList<>();
    private RecyclerView rvTabela;


    public TabelaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabela, container, false);
        //referenciação
        rvTabela = view.findViewById(R.id.rvTabela);

        recuperarTabela();

        return view;
    }

    private void recuperarTabela() {
        RecuperarTabelaAsyncTask recuperarTabelaAsyncTask = new RecuperarTabelaAsyncTask();
        recuperarTabelaAsyncTask.execute();
    }

    public class RecuperarTabelaAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<600000; i++) {
                if (getArguments().getParcelableArrayList("tabela") != null) {
                    listaTabela = getArguments().getParcelableArrayList("tabela");
                    Log.i("info", listaTabela.get(0).getTeam_name());
                    i = 600000;
                }
                //Log.i("info", "nao chegou tabela");
            }

            for(int i=0; i<600000; i++) {
                if (getArguments().getParcelableArrayList("listaDeJogosDaCompeticao") != null) {
                    listaDeJogosDaCompeticao = getArguments().getParcelableArrayList("listaDeJogosDaCompeticao");
                    Log.i("info", listaDeJogosDaCompeticao.get(0).getMatch_time());
                    i = 600000;
                }
                //Log.i("info", "nao chegou jogos da competiçao");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tabelaAoVivo();
        }
    }



    public void tabelaAoVivo() {
        /** RecyclerView */
        //criar/configurar adapter
        AdapterTabela adapterTabela = new AdapterTabela(listaTabela, listaDeJogosDaCompeticao);
        //configurar recyler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTabela.setLayoutManager(layoutManager);
        rvTabela.setHasFixedSize(true);
        rvTabela.setAdapter(adapterTabela);
    }

}
