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

    private List<Times> listaDeElenco;
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

        ElencoAsyncTask elenco = new ElencoAsyncTask();
        elenco.execute();

        return view;
    }

    public class ElencoAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            while (c<1) {
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
            listaDeJogadores = listaDeElenco.get(0).getPlayers();
            exibirJogadores();
        }
    }

    public void exibirJogadores() {
        AdapterJogadores adapterJogadores = new AdapterJogadores(listaDeJogadores);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvElenco.setLayoutManager(layoutManager);
        rvElenco.setNestedScrollingEnabled(false);
        rvElenco.setHasFixedSize(true);
        rvElenco.setAdapter(adapterJogadores);
    }


}
