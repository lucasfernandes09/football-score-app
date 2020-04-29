package com.app.app1.fragments.jogo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.activities.JogoActivity;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RetrospectoFragment extends Fragment implements AdapterJogos.JogoListener {
    private List<Jogos> listaDeJogos, listaDeUltimosJogosCasa, listaDeUltimosJogosVis;
    private Jogos jogo = new Jogos();
    private TextView tvUJCasa, tvUJVis;
    private ImageView ivUJBadgeCasa, ivUJBadgeVis;
    private RecyclerView rvRetrospecto;
    private AdapterJogos adapterJogos;
    private ConstraintLayout clRestrospectoCasa, clRestrospectoVis;

    public RetrospectoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrospecto, container, false);
        //referenciação
        tvUJCasa = view.findViewById(R.id.tvUJCasa); tvUJVis = view.findViewById(R.id.tvUJVis);
        ivUJBadgeCasa = view.findViewById(R.id.ivUJBadgeCasa); ivUJBadgeVis = view.findViewById(R.id.ivUJBadgeVis);
        rvRetrospecto = view.findViewById(R.id.rvRetrospecto);
        clRestrospectoCasa = view.findViewById(R.id.clRestrospectoCasa); clRestrospectoVis = view.findViewById(R.id.clRestrospectoVis);

        //objeto recebido de JogoActivity
        jogo = getArguments().getParcelable("jogo");

        tvUJCasa.setText(jogo.getMatch_hometeam_name());
        tvUJVis.setText(jogo.getMatch_awayteam_name());

        //receber retrospecto
        RetrospectoAsyncTask retrospecto = new RetrospectoAsyncTask();
        retrospecto.execute();

        //badges
        Picasso.get().load(jogo.getTeam_home_badge()).into(ivUJBadgeCasa);
        Picasso.get().load(jogo.getTeam_away_badge()).into(ivUJBadgeVis);

        return view;
    }

    public class RetrospectoAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            //receber lista e verificar até chegar
            while(c < 1) {
                listaDeUltimosJogosCasa = getArguments().getParcelableArrayList("listaDeUltimosJogosCasa");
                listaDeUltimosJogosVis = getArguments().getParcelableArrayList("listaDeUltimosJogosVis");
                if(listaDeUltimosJogosCasa != null && listaDeUltimosJogosVis != null) {
                    c =1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {  //esse método executa na UI Thread
            super.onPostExecute(aVoid);
            listaDeJogos = listaDeUltimosJogosCasa;
            exibirJogos();

            //click casa
            clRestrospectoCasa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listaDeUltimosJogosCasa != null) {
                        listaDeJogos = listaDeUltimosJogosCasa;
                        adapterJogos = new AdapterJogos(listaDeUltimosJogosCasa, RetrospectoFragment.this, jogo.getMatch_hometeam_name());
                        rvRetrospecto.setAdapter(adapterJogos);
                    }
                }
            });

            //click visitante
            clRestrospectoVis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listaDeUltimosJogosVis != null) {
                        listaDeJogos = listaDeUltimosJogosVis;
                        adapterJogos = new AdapterJogos(listaDeUltimosJogosVis, RetrospectoFragment.this, jogo.getMatch_awayteam_name());
                        rvRetrospecto.setAdapter(adapterJogos);
                    }
                }
            });
        }
    }

    public void exibirJogos() {
        adapterJogos = new AdapterJogos(listaDeUltimosJogosCasa, this, jogo.getMatch_hometeam_name());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRetrospecto.setLayoutManager(layoutManager);
        rvRetrospecto.setHasFixedSize(true);
        rvRetrospecto.setNestedScrollingEnabled(false);
        rvRetrospecto.setAdapter(adapterJogos);
    }

    @Override
    public void jogoClick(int position) {
        jogo = listaDeJogos.get(position);
        Intent intent = new Intent(getContext(), JogoActivity.class);
        intent.putExtra("jogo", jogo);
        startActivity(intent);
    }

}
