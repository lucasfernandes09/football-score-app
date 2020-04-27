package com.app.app1.fragments.jogo;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.app.app1.AcoesDoJogo;
import com.app.app1.R;
import com.app.app1.activities.EquipeActivity;
import com.app.app1.adapters.AdapterAcoesDeJogo;
import com.app.app1.helper.AtkDef;
import com.app.app1.model.Cartoes;
import com.app.app1.model.Jogos;
import com.app.app1.model.Marcadores;
import com.app.app1.model.Predicoes;
import com.app.app1.model.lineup.LineupJogador;
import com.app.app1.model.substituicoes.Substituicao;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResumoFragment extends Fragment {

    private Jogos jogo = new Jogos();
    private ArrayList<Marcadores> listaMarcadores;
    private ArrayList<Cartoes> listaCartoes;
    private ArrayList<Substituicao> listaSubsHome, listaSubsAway;
    private List<AcoesDoJogo> listaAcoesDeJogo = new ArrayList<>();
    private ImageView ivBadgeCasa, ivBadgeVis, ivCampo;
    private TextView tvNomeLiga, tvHoraPartida, getTvNomeLiga, tvScoreCasa, tvScoreVis, tvNomeCasa, tvNomeVis, tvFormacaoCasa, tvFormacaoVis;
    private TextView tvTitularesCasa, tvTitularesVis, tvSubstitutosCasa, tvSubstitutosVis, tvForaDoJogoCasa, tvForaDoJogoVis, tvDataR, tvRodada, tvArbitro, tvEstadio;
    private RecyclerView rvAcoesDeJogo;
    private View lAcoesDeJogo, lFormacoes;
    private ConstraintLayout lAtkDef;
    private Boolean home;
    private List<Jogos> listaDeJogosSalvos = new ArrayList<>();
    private TextView tvAtk1, tvAtk2, tvDef1, tvDef2, tvPc, tvPe, tvPv, tvP3, tvPmais3, tvPs, tvPn;
    private RoundCornerProgressBar pbAtk1, pbAtk2, pbDef1, pbDef2;
    private List<Predicoes> listaDePredicao;


    public ResumoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_resumo, container, false);
        //referenciação
        ivBadgeCasa = view.findViewById(R.id.ivBadgeCasa); ivBadgeVis = view.findViewById(R.id.ivBadgeVis);
        tvNomeLiga = view.findViewById(R.id.tvNomeLiga); tvHoraPartida = view.findViewById(R.id.tvHoraPartida);
        tvScoreCasa = view.findViewById(R.id.tvScoreCasa); tvScoreVis = view.findViewById(R.id.tvScoreVis);
        tvNomeCasa = view.findViewById(R.id.tvNomeCasa); tvNomeVis = view.findViewById(R.id.tvNomeVis);
        tvFormacaoCasa = view.findViewById(R.id.tvFormacaoCasa); tvFormacaoVis = view.findViewById(R.id.tvFormacaoVis);
        rvAcoesDeJogo = view.findViewById(R.id.rvAcoesDeJogo);
        ivCampo = view.findViewById(R.id.ivCampo);
        tvTitularesCasa = view.findViewById(R.id.tvTitularesCasa); tvTitularesVis = view.findViewById(R.id.tvTitularesVis);
        tvSubstitutosCasa = view.findViewById(R.id.tvSubstitutosCasa); tvSubstitutosVis = view.findViewById(R.id.tvSubstitutosVis);
        tvForaDoJogoCasa = view.findViewById(R.id.tvForaDoJogoCasa); tvForaDoJogoVis = view.findViewById(R.id.tvForaDoJogoVis);
        lAcoesDeJogo = view.findViewById(R.id.lAcoesDeJogo);
        lFormacoes = view.findViewById(R.id.lFormacoes);
        tvAtk1 = view.findViewById(R.id.tvAtk1); tvAtk2 = view.findViewById(R.id.tvAtk2); tvDef1 = view.findViewById(R.id.tvDef1); tvDef2 = view.findViewById(R.id.tvDef2);
        pbAtk1 = view.findViewById(R.id.pbAtk1); pbAtk2 = view.findViewById(R.id.pbAtk2); pbDef1 = view.findViewById(R.id.pbDef1); pbDef2 = view.findViewById(R.id.pbDef2);
        lAtkDef = view.findViewById(R.id.lAtkDef);
        tvDataR = view.findViewById(R.id.tvDataR); tvRodada = view.findViewById(R.id.tvRodada); tvArbitro = view.findViewById(R.id.tvArbitro); tvEstadio = view.findViewById(R.id.tvEstadio);
        tvPc = view.findViewById(R.id.tvPc); tvPe = view.findViewById(R.id.tvPe); tvPv = view.findViewById(R.id.tvPv); tvP3 = view.findViewById(R.id.tvP3); tvPmais3 = view.findViewById(R.id.tvPmais3);
        tvPs = view.findViewById(R.id.tvPs); tvPn = view.findViewById(R.id.tvPn);

        //objetos recebidos de JogoActivity
        jogo = getArguments().getParcelable("jogo");

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

        //infos da partida
        tvDataR.setText(jogo.getMatch_date());
        tvRodada.setText(jogo.getMatch_round().replace("Round", ""));
        tvArbitro.setText(jogo.getMatch_referee());
        tvEstadio.setText(jogo.getMatch_stadium());

        //probs pré-jogo
        ProbPreJogoAsyncTask probPreJogo = new ProbPreJogoAsyncTask();
        probPreJogo.execute();

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
        if(jogo.getMatch_status().equals("") || jogo.getMatch_status().equals("Postponed")) {  //ainda não realizado
            lAcoesDeJogo.setVisibility(View.GONE);
            lAtkDef.setVisibility(View.GONE);
        }else {
            lAcoesDeJogo.setVisibility(View.VISIBLE);
            lAtkDef.setVisibility(View.VISIBLE);
            exibirAtkDef();
        }
    }

    public void exibirAtkDef() {
        //progressBars
        ArrayList<Float> listaDeTermos = AtkDef.getAtkDef(jogo);
        Float atk1 = listaDeTermos.get(0);
        Float atk2 = listaDeTermos.get(1);
        Float def1 = listaDeTermos.get(2);
        Float def2 = listaDeTermos.get(3);

        pbAtk1.setMax(100); pbAtk1.setProgress(atk1);
        pbAtk2.setMax(100); pbAtk2.setProgress(atk2);
        pbDef1.setMax(100); pbDef1.setProgress(def1);
        pbDef2.setMax(100); pbDef2.setProgress(def2);

        //textViews
        DecimalFormat df = new DecimalFormat("#00");
        String a1 = df.format(atk1) + "%";
        String a2 = df.format(atk2) + "%";
        String d1 = df.format(def1) + "%";
        String d2 = df.format(def2) + "%";

        tvAtk1.setText(a1);
        tvAtk2.setText(a2);
        tvDef1.setText(d1);
        tvDef2.setText(d2);
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

        //recycler
        AdapterAcoesDeJogo adapterAcoes = new AdapterAcoesDeJogo(listaAcoesDeJogo);
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

    public class ProbPreJogoAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            //receber lista e verificar até chegar
            while (c<1) {
                listaDePredicao = getArguments().getParcelableArrayList("listaDePredicao");
                if(listaDePredicao != null) {
                    c =1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvPc.setText(listaDePredicao.get(0).getProb_HW() + "%");
            tvPe.setText(listaDePredicao.get(0).getProb_D() + "%");
            tvPv.setText(listaDePredicao.get(0).getProb_AW() + "%");
            tvP3.setText(listaDePredicao.get(0).getProb_U_3() + "%");
            tvPmais3.setText(listaDePredicao.get(0).getProb_O_3() + "%");
            tvPs.setText(listaDePredicao.get(0).getBts() + "%");
            tvPn.setText(listaDePredicao.get(0).getOts() + "%");
        }
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
