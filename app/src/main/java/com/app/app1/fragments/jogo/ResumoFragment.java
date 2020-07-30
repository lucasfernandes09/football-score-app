package com.app.app1.fragments.jogo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.app.app1.helper.AcoesDoJogo;
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
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
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
    private ImageView ivBadgeCasa, ivBadgeVis, ivCampo, ivCompeticaoResumo;
    private TextView tvNomeLiga, tvHoraPartida, getTvNomeLiga, tvScoreCasa, tvScoreVis, tvNomeCasa, tvNomeVis, tvFormacaoCasa, tvFormacaoVis;
    private TextView tvTitularesCasa, tvTitularesVis, tvSubstitutosCasa, tvSubstitutosVis, tvForaDoJogoCasa, tvForaDoJogoVis, tvDataR, tvRodada, tvArbitro, tvEstadio;
    private TextView tvGoleiroCasa, tvGoleiroVis, tvFimDeJogo, tvProb;
    private RecyclerView rvAcoesDeJogo;
    private View lAcoesDeJogo, lFormacoes;
    private ConstraintLayout lAtkDef;
    private Boolean home;
    private List<Jogos> listaDeJogosSalvos = new ArrayList<>();
    private TextView tvAtk1, tvAtk2, tvDef1, tvDef2;
    private RoundCornerProgressBar pbAtk1, pbAtk2, pbDef1, pbDef2;
    private List<Predicoes> listaDePredicao;
    private ArcProgress pC, pE, pV, p3, pMais3, pS, pN;

    public ResumoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_resumo, container, false);
        //referenciação
        ivBadgeCasa = view.findViewById(R.id.ivBadgeCasa); ivBadgeVis = view.findViewById(R.id.ivBadgeVis); ivCompeticaoResumo = view.findViewById(R.id.ivCompeticaoResumo);
        tvNomeLiga = view.findViewById(R.id.tvNomeLiga); tvHoraPartida = view.findViewById(R.id.tvHoraPartida);
        tvScoreCasa = view.findViewById(R.id.tvScoreCasa); tvScoreVis = view.findViewById(R.id.tvScoreVis);
        tvNomeCasa = view.findViewById(R.id.tvNomeCasa); tvNomeVis = view.findViewById(R.id.tvNomeVis);
        tvFormacaoCasa = view.findViewById(R.id.tvFormacaoCasa); tvFormacaoVis = view.findViewById(R.id.tvFormacaoVis);
        tvGoleiroCasa = view.findViewById(R.id.tvGoleiroCasa); tvGoleiroVis = view.findViewById(R.id.tvGoleiroVis);
        tvFimDeJogo = view.findViewById(R.id.tvFimDeJogo);
        rvAcoesDeJogo = view.findViewById(R.id.rvAcoesDeJogo);
        ivCampo = view.findViewById(R.id.ivCampo);
        tvTitularesCasa = view.findViewById(R.id.tvTitularesCasa); tvTitularesVis = view.findViewById(R.id.tvTitularesVis);
        //tvSubstitutosCasa = view.findViewById(R.id.tvSubstitutosCasa); tvSubstitutosVis = view.findViewById(R.id.tvSubstitutosVis);
        //tvForaDoJogoCasa = view.findViewById(R.id.tvForaDoJogoCasa); tvForaDoJogoVis = view.findViewById(R.id.tvForaDoJogoVis);
        lAcoesDeJogo = view.findViewById(R.id.lAcoesDeJogo);
        lFormacoes = view.findViewById(R.id.lFormacoes);
        tvAtk1 = view.findViewById(R.id.tvAtk1); tvAtk2 = view.findViewById(R.id.tvAtk2); tvDef1 = view.findViewById(R.id.tvDef1); tvDef2 = view.findViewById(R.id.tvDef2);
        pbAtk1 = view.findViewById(R.id.pbAtk1); pbAtk2 = view.findViewById(R.id.pbAtk2); pbDef1 = view.findViewById(R.id.pbDef1); pbDef2 = view.findViewById(R.id.pbDef2);
        lAtkDef = view.findViewById(R.id.lAtkDef);
        tvDataR = view.findViewById(R.id.tvDataR); tvRodada = view.findViewById(R.id.tvRodada); tvArbitro = view.findViewById(R.id.tvArbitro); tvEstadio = view.findViewById(R.id.tvEstadio);
        pC = view.findViewById(R.id.pC); pE = view.findViewById(R.id.pE); pV = view.findViewById(R.id.pV);
        p3 = view.findViewById(R.id.p3); pMais3 = view.findViewById(R.id.pMais3); pS = view.findViewById(R.id.pS); pN = view.findViewById(R.id.pN);
        tvProb = view.findViewById(R.id.tvProb);

        //objetos recebidos de JogoActivity
        jogo = getArguments().getParcelable("jogo");

        //badges (Picasso)
        Picasso.get().load(jogo.getTeam_home_badge()).into(ivBadgeCasa);
        Picasso.get().load(jogo.getTeam_away_badge()).into(ivBadgeVis);
        Picasso.get().load(jogo.getLeague_logo()).into(ivCompeticaoResumo);

        seAoVivo();

        tvNomeLiga.setText(jogo.getLeague_name());
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
        //ProbPreJogoAsyncTask probPreJogo = new ProbPreJogoAsyncTask();
        //probPreJogo.execute();

        return view;
    }

    public void seAoVivo() {
        if(jogo.getMatch_live().equals("1")) {
            configLabelTempo();
            tvHoraPartida.setTextColor(Color.GREEN);
            tvScoreCasa.setText(jogo.getMatch_hometeam_score());
            tvScoreVis.setText(jogo.getMatch_awayteam_score());
            tvFimDeJogo.setVisibility(View.GONE);
            exibirAtkDef();
        }else{
            tvHoraPartida.setText(jogo.getMatch_time());
            tvFimDeJogo.setVisibility(View.VISIBLE);
            eventoRealizado();
        }
    }

    public void configLabelTempo() {
        if(jogo.getMatch_status().contains("Extra")) {
            tvHoraPartida.setText(jogo.getMatch_status().replace("Extra time", "") + "'");
        }else if (jogo.getMatch_status().contains("Break")) {
            tvHoraPartida.setText("Prorrog.");
        }else if (jogo.getMatch_status().contains("Penal")) {
            tvHoraPartida.setText("Penais");
        } else {
            tvHoraPartida.setText(jogo.getMatch_status() + "'");
        }
    }

    public void eventoRealizado() {
        if(jogo.getMatch_status().equals("") || jogo.getMatch_status().equals("Postponed")) {  //ainda não realizado
            lAcoesDeJogo.setVisibility(View.GONE);
            lAtkDef.setVisibility(View.GONE);
            tvScoreCasa.setText("V");
            tvScoreVis.setText("S");
        }else {
            lAcoesDeJogo.setVisibility(View.VISIBLE);
            lAtkDef.setVisibility(View.VISIBLE);
            exibirAtkDef();
            tvScoreCasa.setText(jogo.getMatch_hometeam_score());
            tvScoreVis.setText(jogo.getMatch_awayteam_score());
        }
    }

    public void exibirAtkDef() {
        ArrayList<Float> listaDeTermos = new AtkDef().getAtkDef(jogo);

        if(listaDeTermos != null) {
            //progressBars
            Float atk1 = listaDeTermos.get(0);
            Float atk2 = listaDeTermos.get(1);
            Float def1 = listaDeTermos.get(2);
            Float def2 = listaDeTermos.get(3);

            pbAtk1.setMax(100); pbAtk1.setProgress(atk1);
            pbAtk2.setMax(100); pbAtk2.setProgress(atk2);
            pbDef1.setMax(100); pbDef1.setProgress(def1);
            pbDef2.setMax(100); pbDef2.setProgress(def2);

            //textViews
            DecimalFormat df = new DecimalFormat("###");
            String a1 = df.format(atk1) + "%";
            String a2 = df.format(atk2) + "%";
            String d1 = df.format(def1) + "%";
            String d2 = df.format(def2) + "%";

            tvAtk1.setText(a1);
            tvAtk2.setText(a2);
            tvDef1.setText(d1);
            tvDef2.setText(d2);
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

        //exebir ações de jogo
        AdapterAcoesDeJogo adapterAcoes = new AdapterAcoesDeJogo(listaAcoesDeJogo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAcoesDeJogo.setLayoutManager(layoutManager);
        rvAcoesDeJogo.addItemDecoration(new DividerItemDecoration(rvAcoesDeJogo.getContext(), LinearLayoutManager.VERTICAL));
        rvAcoesDeJogo.setHasFixedSize(true);
        rvAcoesDeJogo.setAdapter(adapterAcoes);
    }

    public void marcadores() {
        if(jogo.getGoalscorer() != null) {
            listaMarcadores = (ArrayList<Marcadores>) jogo.getGoalscorer();
            for(Marcadores marcador : listaMarcadores) {
                if(marcador.getHome_scorer().equals("")) {
                    listaAcoesDeJogo.add(new AcoesDoJogo(marcador.getAway_scorer(), marcador.getTime(), false, "marcadores"));
                }else {
                    listaAcoesDeJogo.add(new AcoesDoJogo(marcador.getHome_scorer(), marcador.getTime(), true, "marcadores"));
                }
            }
        }
    }

    public void cartoesVermelhos() {
        if(jogo.getCards() != null) {
            listaCartoes = (ArrayList<Cartoes>) jogo.getCards();
            for (int i = 0; i < listaCartoes.size(); i++) {
                Cartoes cartao = listaCartoes.get(i);
                if (cartao.getCard().equals("red card") && !cartao.getHome_fault().equals("")) {
                    listaAcoesDeJogo.add(new AcoesDoJogo(cartao.getHome_fault(), cartao.getTime(),
                            true, "cartaoVermelho"));
                } else {
                    if (cartao.getCard().equals("red card") && !cartao.getAway_fault().equals("")) {
                        listaAcoesDeJogo.add(new AcoesDoJogo(cartao.getAway_fault(), cartao.getTime(),
                                false, "cartaoVermelho"));
                    }
                }
            }
        }
    }

    public void substituicoes() {
        if(jogo.getSubstitutions() != null) {
            listaSubsHome = (ArrayList<Substituicao>) jogo.getSubstitutions().getHome();
            listaSubsAway = (ArrayList<Substituicao>) jogo.getSubstitutions().getAway();

            for (int i=0; i<listaSubsHome.size(); i++) {
                listaAcoesDeJogo.add(new AcoesDoJogo(listaSubsHome.get(i).getSubstitution(), listaSubsHome.get(i).getTime(),
                        true, "substituição"));
            }
            for (int i=0; i<listaSubsAway.size(); i++) {
                listaAcoesDeJogo.add(new AcoesDoJogo(listaSubsAway.get(i).getSubstitution(), listaSubsAway.get(i).getTime(),
                        false, "substituição"));
            }
        }
    }

    public void formacoes() {
        if(jogo.getMatch_hometeam_system().isEmpty() || jogo.getMatch_awayteam_system().isEmpty()
                || jogo.getLineup() == null) {
            lFormacoes.setVisibility(View.GONE);
        }else {
            //goleiros
            for(LineupJogador jogador : jogo.getLineup().getHome().getStarting_lineups()) {
                if(jogador.getLineup_position().equals("1")) {
                    tvGoleiroCasa.setText(jogador.getLineup_player());
                }
            }
            for(LineupJogador jogador : jogo.getLineup().getAway().getStarting_lineups()) {
                if(jogador.getLineup_position().equals("1")) {
                    tvGoleiroVis.setText(jogador.getLineup_player());
                }
            }

            //titulares
            tvTitularesCasa.setText(titulares(true, jogo.getMatch_hometeam_system()));//cas
            tvTitularesVis.setText(titulares(false, jogo.getMatch_awayteam_system()));//vis
            //talvez seja melhor dividir em duas funçoes distintas

            //subs e fora do jogo
            /*for(LineupJogador substitutosCasa : jogo.getLineup().getHome().getSubstitutes()) {
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
            }*/
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
            escalacaoFinal.append("\n");
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
            escalacaoFinal.append("\n");
        }
            return escalacaoFinal;
    }

    /*public class ProbPreJogoAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            int c = 0;
            while (c<1) {
                listaDePredicao = getArguments().getParcelableArrayList("listaDePredicao");
                if(listaDePredicao != null) {
                    c = 1;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pC.setProgress(listaDePredicao.get(0).getProb_HW());
            pE.setProgress(listaDePredicao.get(0).getProb_D());
            pV.setProgress(listaDePredicao.get(0).getProb_AW());
            p3.setProgress(listaDePredicao.get(0).getProb_U_3());
            pMais3.setProgress(listaDePredicao.get(0).getProb_O_3());
            pS.setProgress(listaDePredicao.get(0).getProb_bts());
            pN.setProgress(listaDePredicao.get(0).getProb_ots());
        }
    }*/

    public void probPreJogo(List<Predicoes> listaDePredicao) {
        tvProb.setText("Probabilidades Pré-Jogo");

        pC.setProgress(listaDePredicao.get(0).getProb_HW());
        pE.setProgress(listaDePredicao.get(0).getProb_D());
        pV.setProgress(listaDePredicao.get(0).getProb_AW());
        p3.setProgress(listaDePredicao.get(0).getProb_U_3());
        pMais3.setProgress(listaDePredicao.get(0).getProb_O_3());
        pS.setProgress(listaDePredicao.get(0).getProb_bts());
        pN.setProgress(listaDePredicao.get(0).getProb_ots());
    }

    private View.OnClickListener listenerCasa = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            home = true;
            Intent intent = new Intent(getActivity(), EquipeActivity.class);
            intent.putExtra("jogo", jogo);
            intent.putExtra("home", home);
            startActivity(intent);
        }
    };

    private View.OnClickListener listenerVis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            home = false;
            Intent intent = new Intent(getActivity(), EquipeActivity.class);
            intent.putExtra("jogo", jogo);
            intent.putExtra("home", home);
            startActivity(intent);
        }
    };







}
