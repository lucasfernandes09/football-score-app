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

        //tabela
        RecuperarTabelaAsyncTask recuperarTabelaAsyncTask = new RecuperarTabelaAsyncTask();
        recuperarTabelaAsyncTask.execute();

        return view;
    }


    public class RecuperarTabelaAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            //receber lista e verificar até chegar
            while (c<1) {
                listaTabela = getArguments().getParcelableArrayList("tabela");
                listaDeJogosDaCompeticao = getArguments().getParcelableArrayList("listaDeJogosDaCompeticao");
                if(listaTabela != null && listaDeJogosDaCompeticao != null) {
                    c=1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("info", "tabela chegou");
            tabelaAoVivo();
        }
    }


    public void tabelaAoVivo() {
        AdapterTabela adapterTabela = new AdapterTabela(listaTabela, listaDeJogosDaCompeticao);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTabela.setLayoutManager(layoutManager);
        rvTabela.setHasFixedSize(true);
        rvTabela.setAdapter(adapterTabela);
    }

}
