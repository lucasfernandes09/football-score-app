package com.app.app1.fragments.equipe;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.app1.R;
import com.app.app1.adapters.AdapterJogadores;
import com.app.app1.model.Jogador;
import com.app.app1.model.Times;

import java.util.ArrayList;
import java.util.List;


public class ElencoFragment extends Fragment {

    private List<Times> listaDeElenco = new ArrayList<>();
    private List<Jogador> listaDeJogadores = new ArrayList<>();
    private RecyclerView rvElenco;


    public ElencoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elenco, container, false);
        //referenciação
        rvElenco = view.findViewById(R.id.rvElenco);

        recebeElenco();

        return view;
    }

    private void recebeElenco() {
        ElencoAsyncTask elencoAsyncTask = new ElencoAsyncTask();
        elencoAsyncTask.execute();
    }

    public class ElencoAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<600000; i++) {
                if (getArguments().getParcelableArrayList("listaDeElenco") != null) {
                    listaDeElenco = getArguments().getParcelableArrayList("listaDeElenco");
                    i = 600000;
                }
                //Log.i("info", "nao chegou em ElencoFragment");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerJogadores();
        }
    }

    public void recyclerJogadores() {
        listaDeJogadores = listaDeElenco.get(0).getPlayers();

        AdapterJogadores adapterJogadores = new AdapterJogadores(listaDeJogadores);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvElenco.setLayoutManager(layoutManager);
        rvElenco.setNestedScrollingEnabled(false);
        rvElenco.setHasFixedSize(true);
        rvElenco.setAdapter(adapterJogadores);
    }


}
