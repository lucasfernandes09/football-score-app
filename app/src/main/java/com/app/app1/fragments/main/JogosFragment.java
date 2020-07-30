package com.app.app1.fragments.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.util.Log;
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
import java.util.List;


public class JogosFragment extends Fragment implements AdapterJogos.JogoListener {

    private RecyclerView rvJogos;
    private AdapterJogos adapterJogos;
    private ArrayList<Jogos> listaDeJogos = new ArrayList<>();
    private ArrayList<Jogos> listaDeJogosFinal = new ArrayList<>();
    private Jogos jogo = new Jogos();
    private TextView tvInfoJogos;
    private Handler handler = new Handler();

    public JogosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_jogos, container, false);
        //referenciação
        rvJogos = view.findViewById(R.id.rvJogos);
        tvInfoJogos = view.findViewById(R.id.tvInfoJogos);

        listaDeJogos = getArguments().getParcelableArrayList("listaDeJogos");

        verificarLista();

        return view;
    }

    public void verificarLista() {
        if(listaDeJogos != null) {
            listaDeJogosFinal = listaDeJogos;
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
        Collections.sort(listaDeJogosFinal, comparator);
    }

    public void exibirJogos() {
        adapterJogos = new AdapterJogos(listaDeJogosFinal, JogosFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvJogos.setLayoutManager(layoutManager);
        rvJogos.setHasFixedSize(true);
        rvJogos.setAdapter(adapterJogos);
    }

    public void atualizarLista(List<Jogos> listaDeJogos) {
        listaDeJogosFinal = (ArrayList<Jogos>) listaDeJogos;
        comparator();
        rvJogos.setAdapter(new AdapterJogos(listaDeJogosFinal, JogosFragment.this));

        /*for(int i=0; i<listaDeJogos.size(); i++) {
            if(listaDeJogos.get(i).getMatch_live().equals("1")) {
                Log.i("info", listaDeJogos.get(i).getMatch_status());
                //adapterJogos.notifyItemRemoved(i);
                //adapterJogos.notifyItemInserted(i);
                //listaDeJogosFinal.set(i, listaDeJogos.get(i));
                adapterJogos.notifyItemChanged(i);
                //adapterJogos.notifyDataSetChanged();
            }
        }*/
    }

    @Override
    public void jogoClick(int position) {
        jogo = listaDeJogosFinal.get(position);
        Intent intent = new Intent(getContext(), JogoActivity.class);
        intent.putExtra("jogo", jogo);
        startActivity(intent);
    }

    public class JogosAoVivo extends AsyncTask<Void, Void, Void> {
        private ArrayList<Jogos> listaDeJogosAoVivo = new ArrayList<>();
        private boolean aoVivo;

        public JogosAoVivo(boolean aoVivo) {
            this.aoVivo = aoVivo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(aoVivo) {
                for(Jogos jogo : listaDeJogos) {
                    if(jogo.getMatch_live().equals("1")) {
                        listaDeJogosAoVivo.add(jogo);
                    }
                }
                listaDeJogosFinal = listaDeJogosAoVivo;
            }else {
                listaDeJogosFinal = listaDeJogos;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(aoVivo) {
                if(listaDeJogosAoVivo.isEmpty()) {
                    tvInfoJogos.setVisibility(View.VISIBLE);
                }else {
                    tvInfoJogos.setVisibility(View.GONE);
                    rvJogos.setAdapter(new AdapterJogos(listaDeJogosFinal, JogosFragment.this));
                }
            }else {
                rvJogos.setAdapter(new AdapterJogos(listaDeJogosFinal, JogosFragment.this));
            }
        }
    }

}
