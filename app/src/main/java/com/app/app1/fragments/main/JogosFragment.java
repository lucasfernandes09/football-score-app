package com.app.app1.fragments.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.activities.JogoActivity;
import com.app.app1.activities.MainActivity;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class JogosFragment extends Fragment implements AdapterJogos.JogoListener {

    private RecyclerView rvJogos;
    private AdapterJogos adapterJogos;
    private ArrayList<Jogos> listaDeJogos;
    private Jogos jogo = new Jogos();
    private TextView tvSemEventos;
    private ProgressBar pbJogos;

    public JogosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_jogos, container, false);
        //referenciação
        rvJogos = view.findViewById(R.id.rvJogos);
        tvSemEventos = view.findViewById(R.id.tvSemEventos);
        pbJogos = view.findViewById(R.id.pbJogos);

        listaDeJogos = getArguments().getParcelableArrayList("listaDeJogos");

        verificarLista();

        return view;
    }

    public void verificarLista() {
        if(listaDeJogos != null) {
            if(listaDeJogos.isEmpty()) {
                tvSemEventos.setVisibility(View.VISIBLE);
            }else {
                comparator();
                exibirJogos();
            }
        }else {
            ReceberJogosAsyncTask receberJogos = new ReceberJogosAsyncTask();
            receberJogos.execute();
        }
    }

    public class ReceberJogosAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            while(c < 1) {
                listaDeJogos = getArguments().getParcelableArrayList("listaDeJogos");
                if(listaDeJogos != null) {
                    c = 1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            comparator();
            exibirJogos();
        }
    }

    public void comparator() {
        Comparator<Jogos> comparator = new Comparator<Jogos>() {
            @Override
            public int compare(Jogos j1, Jogos j2) {
                return j1.getMatch_time().compareTo(j2.getMatch_time());
            }
        };
        Collections.sort(listaDeJogos, comparator);
    }

    public void exibirJogos() {
        pbJogos.setVisibility(View.GONE);
        adapterJogos = new AdapterJogos(listaDeJogos, JogosFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvJogos.setLayoutManager(layoutManager);
        //rvJogos.addItemDecoration(new DividerItemDecoration(rvJogos.getContext(), LinearLayoutManager.VERTICAL));
        rvJogos.setHasFixedSize(true);
        rvJogos.setAdapter(adapterJogos);
    }

    @Override
    public void jogoClick(int position) {
        jogo = listaDeJogos.get(position);
        Intent intent = new Intent(getContext(), JogoActivity.class);
        intent.putExtra("jogo", jogo);
        startActivity(intent);

    }
}
