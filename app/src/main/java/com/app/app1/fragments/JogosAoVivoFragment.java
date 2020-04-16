package com.app.app1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class JogosAoVivoFragment extends Fragment implements AdapterJogos.JogoListener {

    private List<Jogos> listaDeJogos;
    private List<Jogos> listaDeJogosAoVivo = new ArrayList<>();
    private TextView tvJogosAoVivo;
    private RecyclerView rvJogosAoVivo;

    public JogosAoVivoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_jogos_ao_vivo, container, false);
        //referenciação
        tvJogosAoVivo = view.findViewById(R.id.tvJogosAoVivo);
        rvJogosAoVivo = view.findViewById(R.id.rvJogosAoVivo);

        listaDeJogos = getArguments().getParcelableArrayList("listaDeJogos");

        if(listaDeJogos == null) {
            tvJogosAoVivo.setVisibility(View.VISIBLE);
        }else {
            listarJogosAoVivo();
            if(listaDeJogosAoVivo.isEmpty()) {
                tvJogosAoVivo.setVisibility(View.VISIBLE);
            }else {
                tvJogosAoVivo.setVisibility(View.GONE);
                exibirPartidasAoVivo();
            }
        }

        return view;
    }

    public void listarJogosAoVivo() {
        //popular lista de jogos ao vivo
        listaDeJogosAoVivo.clear();
        for(Jogos jogo : listaDeJogos) {
            if(jogo.getMatch_live().equals("1")) {
                listaDeJogosAoVivo.add(jogo);
            }
        }
    }

    public void exibirPartidasAoVivo() {
        AdapterJogos adapter = new AdapterJogos(listaDeJogosAoVivo, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvJogosAoVivo.setLayoutManager(layoutManager);
        rvJogosAoVivo.setHasFixedSize(true);
        rvJogosAoVivo.setAdapter(adapter);
    }

    @Override
    public void jogoClick(int position) {

    }
}
