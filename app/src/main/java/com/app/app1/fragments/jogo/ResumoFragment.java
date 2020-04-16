package com.app.app1.fragments.jogo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.app1.AcoesDoJogo;
import com.app.app1.R;
import com.app.app1.activities.EquipeActivity;
import com.app.app1.adapters.AdapterAcoesDeJogo;
import com.app.app1.model.Cartoes;
import com.app.app1.model.Jogos;
import com.app.app1.model.Marcadores;
import com.app.app1.model.lineup.LineupJogador;
import com.app.app1.model.substituicoes.Substituicao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumoFragment extends Fragment {

    private Jogos jogo = new Jogos();
    private ArrayList<Marcadores> listaMarcadores;
    private ArrayList<Cartoes> listaCartoes;
    private ArrayList<Substituicao> listaSubsHome;
    private ArrayList<Substituicao> listaSubsAway;
    private List<AcoesDoJogo> listaAcoesDeJogo = new ArrayList<>();
    private ImageView ivBadgeCasa;
    private ImageView ivBadgeVis;
    private TextView tvNomeLiga;
    private TextView tvHoraPartida;
    private TextView tvX;
    private TextView tvScoreCasa;
    private TextView tvScoreVis;
    private TextView tvNomeCasa;
    private TextView tvNomeVis;
    private View divider;
    private TextView tvFormacaoCasa;
    private TextView tvFormacaoVis;
    private ImageView ivCampo;
    private RecyclerView rvAcoesDeJogo;
    private TextView tvTitularesCasa;
    private TextView tvTitularesVis;
    private TextView tvSubstitutosCasa;
    private TextView tvSubstitutosVis;
    private TextView tvForaDoJogoCasa;
    private TextView tvForaDoJogoVis;
    private View lAcoesDeJogo;
    private View lFormacoes;
    private Boolean home;
    Button button2;
    private List<Jogos> listaDeJogosSalvos = new ArrayList<>();


    public ResumoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_resumo, container, false);

        //referenciação
        ivBadgeCasa = view.findViewById(R.id.ivBadgeCasa);
        ivBadgeVis = view.findViewById(R.id.ivBadgeVis);
        tvNomeLiga = view.findViewById(R.id.tvNomeLiga);
        tvHoraPartida = view.findViewById(R.id.tvHoraPartida);
        tvX = view.findViewById(R.id.tvX);
        tvScoreCasa = view.findViewById(R.id.tvScoreCasa);
        tvScoreVis = view.findViewById(R.id.tvScoreVis);
        tvNomeCasa = view.findViewById(R.id.tvNomeCasa);
        tvNomeVis = view.findViewById(R.id.tvNomeVis);
        divider = view.findViewById(R.id.divider);
        tvFormacaoCasa = view.findViewById(R.id.tvFormacaoCasa);
        tvFormacaoVis = view.findViewById(R.id.tvFormacaoVis);
        ivCampo = view.findViewById(R.id.ivCampo);
        rvAcoesDeJogo = view.findViewById(R.id.rvAcoesDeJogo);
        tvTitularesCasa = view.findViewById(R.id.tvTitularesCasa);
        tvTitularesVis = view.findViewById(R.id.tvTitularesVis);
        tvSubstitutosCasa = view.findViewById(R.id.tvSubstitutosCasa);
        tvSubstitutosVis = view.findViewById(R.id.tvSubstitutosVis);
        tvForaDoJogoCasa = view.findViewById(R.id.tvForaDoJogoCasa);
        tvForaDoJogoVis = view.findViewById(R.id.tvForaDoJogoVis);
        lAcoesDeJogo = view.findViewById(R.id.lAcoesDeJogo);
        lFormacoes = view.findViewById(R.id.lFormacoes);
        button2 = view.findViewById(R.id.button2);

        //objetos recebidos de JogoActivity
        jogo = getArguments().getParcelable("jogo");


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jogo.salvarJogo();
            }
        });


        //badges (Picasso)
        Picasso.get().load(jogo.getTeam_home_badge()).into(ivBadgeCasa);
        Picasso.get().load(jogo.getTeam_away_badge()).into(ivBadgeVis);

        seAoVivo();

        tvNomeLiga.setText(jogo.getLeague_name());
        tvScoreCasa.setText(jogo.getMatch_hometeam_score());
        tvScoreVis.setText(jogo.getMatch_awayteam_score());
        tvNomeCasa.setText(jogo.getMatch_hometeam_name());
        tvNomeVis.setText(jogo.getMatch_awayteam_name());
        tvFormacaoCasa.setText(jogo.getMatch_hometeam_system());
        tvFormacaoVis.setText(jogo.getMatch_awayteam_system());

        acoesDeJogo();
        formacoes();

        //cliques
        ivBadgeCasa.setOnClickListener(listenerCasa);
        ivBadgeVis.setOnClickListener(listenerVis);
        tvNomeCasa.setOnClickListener(listenerCasa);
        tvNomeVis.setOnClickListener(listenerVis);

        return view;
    }



    public void marcadores() {
        if(jogo.getGoalscorer() == null) {
            return;
        }else {
            listaMarcadores = (ArrayList<Marcadores>) jogo.getGoalscorer();
        }

        for(int i=0; i<listaMarcadores.size(); i++) {
            if(listaMarcadores.get(i).getHome_scorer().equals("")) {
                listaAcoesDeJogo.add(new AcoesDoJogo(listaMarcadores.get(i).getAway_scorer(),
                        listaMarcadores.get(i).getTime(), false, "marcadores"));

            }else {
                listaAcoesDeJogo.add(new AcoesDoJogo(listaMarcadores.get(i).getHome_scorer(),
                        listaMarcadores.get(i).getTime(), true, "marcadores"));
            }
        }
    }

    public void cartoesVermelhos() {
        if(jogo.getCards() == null) {
            return;
        }else {
            listaCartoes = (ArrayList<Cartoes>) jogo.getCards();
        }

        for(int i=0; i<listaCartoes.size(); i++) {
            Cartoes cartao = listaCartoes.get(i);
            if(cartao.getCard().equals("red card") && !cartao.getHome_fault().equals("")) {
                listaAcoesDeJogo.add(new AcoesDoJogo(cartao.getHome_fault(), cartao.getTime(),
                        true, "cartaoVermelho"));
            }else {
                if(cartao.getCard().equals("red card") && !cartao.getAway_fault().equals("")) {
                    listaAcoesDeJogo.add(new AcoesDoJogo(cartao.getAway_fault(), cartao.getTime(),
                            false, "cartaoVermelho"));
                }
            }
        }
    }

    public void substituicoes() {
        if(jogo.getSubstitutions() == null) {
            return;
        }else {
        listaSubsHome = (ArrayList<Substituicao>) jogo.getSubstitutions().getHome();
        listaSubsAway = (ArrayList<Substituicao>) jogo.getSubstitutions().getAway();
        }

        for(int i=0; i<listaSubsHome.size(); i++) {
            listaAcoesDeJogo.add(new AcoesDoJogo(listaSubsHome.get(i).getSubstitution(), listaSubsHome.get(i).getTime(),
                    true, "substituição"));
        }
        for(int i=0; i<listaSubsAway.size(); i++) {
            listaAcoesDeJogo.add(new AcoesDoJogo(listaSubsAway.get(i).getSubstitution(), listaSubsAway.get(i).getTime(),
                    false, "substituição"));
        }
    }

    public void seAoVivo() {
        if(jogo.getMatch_live().equals("1")) {
            tvHoraPartida.setText(jogo.getMatch_status() + "'");
            tvHoraPartida.setTextColor(Color.GREEN);
        }else{
            tvHoraPartida.setText(jogo.getMatch_time());
            eventoRealizado();

        }
    }

    public void eventoRealizado() {
        if(jogo.getMatch_status().equals("")) {  //ainda nao realizado
            lAcoesDeJogo.setVisibility(View.GONE);
        }else {
            lAcoesDeJogo.setVisibility(View.VISIBLE);
        }
    }

    public void acoesDeJogo() {
        marcadores();
        cartoesVermelhos();
        substituicoes();

        //ordena por min'
        Comparator<AcoesDoJogo> comparator = new Comparator<AcoesDoJogo>() {
            @Override
            public int compare(AcoesDoJogo a1, AcoesDoJogo a2) {
                return a1.getTime().compareTo(a2.getTime());
            }
        };
        Collections.sort(listaAcoesDeJogo, comparator);

        /** RecyclerView */
        //        //criar/configurar adapter
        AdapterAcoesDeJogo adapterAcoes = new AdapterAcoesDeJogo(listaAcoesDeJogo);
        //configurar recyler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAcoesDeJogo.setLayoutManager(layoutManager);
        rvAcoesDeJogo.setHasFixedSize(true);
        rvAcoesDeJogo.setAdapter(adapterAcoes);

    }


    public void formacoes() {
        if(jogo.getMatch_hometeam_system().isEmpty() || jogo.getMatch_awayteam_system().isEmpty()
                || jogo.getLineup() == null) {
            // escalaçao ou formaçao nao disponivel
            lFormacoes.setVisibility(View.GONE);

        }else {
            tvTitularesCasa.setText(titulares(true, jogo.getMatch_hometeam_system()));//cas
            tvTitularesVis.setText(titulares(false, jogo.getMatch_awayteam_system()));//vis
            //talvez seja melhor dividir em duas funçoes distintas

            //subs e fora do jogo
            for(LineupJogador substitutosCasa : jogo.getLineup().getHome().getSubstitutes()) {
                tvSubstitutosCasa.append(substitutosCasa.getLineup_number() + "  "
                + substitutosCasa.getLineup_player() + "\n");
            }
            for(LineupJogador foraDoJogoCasa : jogo.getLineup().getHome().getMissing_players()) {
                tvForaDoJogoCasa.append(foraDoJogoCasa.getLineup_player() + "\n");
            }

            for(LineupJogador substitutosVis : jogo.getLineup().getAway().getSubstitutes()) {
                tvSubstitutosVis.append(substitutosVis.getLineup_player() + "  "
                        + substitutosVis.getLineup_number() + "\n");
            }
            for(LineupJogador foraDoJogoVis : jogo.getLineup().getAway().getMissing_players()) {
                tvForaDoJogoVis.append(foraDoJogoVis.getLineup_player() + "\n");
            }
        }

    }

    public StringBuilder titulares(boolean casa, String sistema) {
        StringBuilder escalacaoFinal = new StringBuilder();

        //pegar os numeros da String sistema e adicionar na lista formacao
        List<String> formacao = new ArrayList<>();
        for (int i = 0; i <= sistema.length(); i += 4) {
            formacao.add(Character.toString(sistema.charAt(i)));
        }
        if(!casa) {
            Collections.reverse(formacao);
        }

        //pula x linhas para x comprimento da lista formacao
        //controla a variavel c de acordo com o valor contido nos indices de formacao
        //varre a lista lineup verificando a igualdade com a variavel c
        int c = 2;
        int v = 11;
        for (int k = 0; k < formacao.size(); k++) {
            escalacaoFinal.append("\n\n");
            for (int j = 0; j < Integer.valueOf(formacao.get(k)); j++) {
                for (int i = 0; i<11; i++) {
                    if(casa) {
                        if (jogo.getLineup().getHome().getStarting_lineups().get(i).getLineup_position().equals(String.valueOf(c))) {
                            escalacaoFinal.append(jogo.getLineup().getHome().getStarting_lineups().get(i).getLineup_player());
                            escalacaoFinal.append("   ");
                        }
                    }else {
                        if (jogo.getLineup().getAway().getStarting_lineups().get(i).getLineup_position().equals(String.valueOf(v))) {
                            escalacaoFinal.append(jogo.getLineup().getAway().getStarting_lineups().get(i).getLineup_player());
                            escalacaoFinal.append("   ");
                        }
                    }
                }
                c++;
                v--;
            }
        }
            return escalacaoFinal;
    }


    //listener casa
    private View.OnClickListener listenerCasa = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            home = true;
            Intent intent = new Intent(getActivity(), EquipeActivity.class);  //getActivity() é o contexto
            intent.putExtra("jogo", jogo);
            intent.putExtra("home", home);
            startActivity(intent);
        }
    };

    //listener vis
    private View.OnClickListener listenerVis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            home = false;
            Intent intent = new Intent(getActivity(), EquipeActivity.class);  //getActivity() é o contexto
            intent.putExtra("jogo", jogo);
            intent.putExtra("home", home);
            startActivity(intent);
        }
    };







}
