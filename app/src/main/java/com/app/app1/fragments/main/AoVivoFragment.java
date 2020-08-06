package com.app.app1.fragments.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.app1.R;
import com.app.app1.adapters.AdapterCompeticoes;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.fragments.BottomSheetFragment;
import com.app.app1.model.Jogos;

import java.util.ArrayList;
import java.util.List;

public class AoVivoFragment extends Fragment implements AdapterJogos.JogoListener {
    private List<Jogos> listaDeJogosAoVivo = new ArrayList<>();
    private List<Jogos> listaDeJogos = new ArrayList<>();
    private RecyclerView rvAoVivo;

    public AoVivoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ao_vivo, container, false);
        //ref
        rvAoVivo = view.findViewById(R.id.rvAoVivo);

        configClick(view);

        listaDeJogos = getArguments().getParcelableArrayList("listaDeJogos");
        listaDeJogosAoVivo = getJogosAovivo(listaDeJogos);

        exibirAoVivo();

        return view;
    }

    private void configClick(View view) {
        Button btnAoVivo = view.findViewById(R.id.btnAoVivo);
        btnAoVivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetFragment().show(getChildFragmentManager(), "bottomSheet");
            }
        });
    }

    private void exibirAoVivo() {
        AdapterJogos adapterJogos = new AdapterJogos(listaDeJogosAoVivo, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAoVivo.setLayoutManager(layoutManager);
        rvAoVivo.setHasFixedSize(true);
        rvAoVivo.setAdapter(adapterJogos);
    }

    private List<Jogos> getJogosAovivo(List<Jogos> listaDeJogos) {
        List<Jogos> listaDeJogosAoVivo = new ArrayList<>();

        for(Jogos jogo : listaDeJogos) {
            if(jogo.getMatch_live().equals("1")) {
                listaDeJogosAoVivo.add(jogo);
            }
        }

        return listaDeJogosAoVivo;
    }

    @Override
    public void jogoClick(int position) {

    }
}