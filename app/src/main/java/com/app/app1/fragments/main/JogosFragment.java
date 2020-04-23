package com.app.app1.fragments.main;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.app1.R;
import com.app.app1.RecyclerItemClickListener;
import com.app.app1.RetrofitService;
import com.app.app1.activities.JogoActivity;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Cartoes;
import com.app.app1.model.Jogos;
import com.app.app1.model.Marcadores;
import com.app.app1.model.substituicoes.Substituicao;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JogosFragment extends Fragment implements AdapterJogos.JogoListener {

    private RecyclerView rvJogos;
    private AdapterJogos adapterJogos;
    private ArrayList<Jogos> listaDeJogos;
    private Jogos jogo = new Jogos();
    private TextView tvSemEventos;
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
        adapterJogos = new AdapterJogos(listaDeJogos, JogosFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvJogos.setLayoutManager(layoutManager);
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
