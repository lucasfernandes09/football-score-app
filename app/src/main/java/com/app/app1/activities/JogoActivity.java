package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.app.app1.R;
import com.app.app1.fragments.main.CompeticoesFragment;
import com.app.app1.helper.DatasUtil;
import com.app.app1.services.RetrofitService;
import com.app.app1.fragments.jogo.EstatisticasFragment;
import com.app.app1.fragments.jogo.ResumoFragment;
import com.app.app1.fragments.jogo.RetrospectoFragment;
import com.app.app1.fragments.jogo.TabelaFragment;
import com.app.app1.model.Jogos;

import com.app.app1.model.Predicoes;
import com.app.app1.model.Tabela;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JogoActivity extends AppCompatActivity {

    private Jogos jogo = new Jogos();
    private List<Jogos> listaDeJogosDaCompeticao = new ArrayList<>();
    private List<Jogos> listaDeUltimosJogosCasa = new ArrayList<>();
    private List<Jogos> listaDeUltimosJogosVis = new ArrayList<>();
    private List<Tabela> listaTabela = new ArrayList<>();
    private List<Predicoes> listaDePredicao = new ArrayList<>();
    private ViewPager vpJogo;
    private SmartTabLayout tabJogo;
    private Bundle bundle = new Bundle();
    private FragmentStatePagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        //referenciação
        vpJogo = findViewById(R.id.vpJogo);
        tabJogo = findViewById(R.id.tabJogo);

        //objetos recebidos
        jogo = getIntent().getExtras().getParcelable("jogo");
        bundle.putParcelable("jogo", jogo);

        //action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(jogo.getMatch_hometeam_name() + " x " + jogo.getMatch_awayteam_name());

        retrospectoCasa();

        retrospectoVis();

        tabela();

        jogosDaCompeticao();

        if(jogo.getMatch_status().equals("") || jogo.getMatch_status().equals("Postponed")) {  //ainda não realizado
            tabsSemEstatisticas();
        }else {
            tabs();
        }

        predicoes();
    }

    private void tabsSemEstatisticas() {
        FragmentPagerItems pages = FragmentPagerItems.with(getApplicationContext())
                .add("Resumo", ResumoFragment.class, bundle)
                .add("Retrospecto", RetrospectoFragment.class, bundle)
                .add("Tabela", TabelaFragment.class, bundle)
                .create();

        adapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);

        vpJogo.setAdapter(adapter);
        tabJogo.setViewPager(vpJogo);
    }

    private void tabs() {
        FragmentPagerItems pages = FragmentPagerItems.with(getApplicationContext())
                .add("Resumo", ResumoFragment.class, bundle)
                .add("Estatísticas", EstatisticasFragment.class, bundle)
                .add("Retrospecto", RetrospectoFragment.class, bundle)
                .add("Tabela", TabelaFragment.class, bundle)
                .create();

        adapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);

        vpJogo.setAdapter(adapter);
        tabJogo.setDistributeEvenly(false);
        tabJogo.setViewPager(vpJogo);
    }

    public void retrospectoCasa() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> requestUltimosJogos = service.listarJogosDaEquipe(DatasUtil.data2MesesAtras(), DatasUtil.dataHoje(),
                jogo.getMatch_hometeam_id());

        requestUltimosJogos.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeUltimosJogosCasa = response.body();
                    Collections.reverse(listaDeUltimosJogosCasa);
                    bundle.putParcelableArrayList("listaDeUltimosJogosCasa", (ArrayList<Jogos>) listaDeUltimosJogosCasa);
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "falha RetrospCasa: " + t.getMessage());
            }
        });
    }

    public void retrospectoVis() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> requestUltimosJogos = service.listarJogosDaEquipe(DatasUtil.data2MesesAtras(), DatasUtil.dataHoje(),
                jogo.getMatch_awayteam_id());

        requestUltimosJogos.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeUltimosJogosVis = response.body();
                    Collections.reverse(listaDeUltimosJogosVis);
                    bundle.putParcelableArrayList("listaDeUltimosJogosVis", (ArrayList<Jogos>) listaDeUltimosJogosVis);
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "falha RetrospVis: " + t.getMessage());
            }
        });
    }

    public void tabela() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Tabela>> requestTabela = service.listarTabela(jogo.getLeague_id());

        requestTabela.enqueue(new Callback<List<Tabela>>() {
            @Override
            public void onResponse(Call<List<Tabela>> call, Response<List<Tabela>> response) {
                if(!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                }else {
                    listaTabela = response.body();
                    bundle.putParcelableArrayList("tabela",(ArrayList<Tabela>) listaTabela);
                }
            }

            @Override
            public void onFailure(Call<List<Tabela>> call, Throwable t) {
                Log.i("info", "falha tabela: " + t.getMessage());
            }
        });
    }

    public void jogosDaCompeticao() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> requestJogosDaCompeticao = service.listarJogosDaCompeticao(DatasUtil.dataHoje(), DatasUtil.dataHoje(),
                jogo.getLeague_id());

        requestJogosDaCompeticao.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeJogosDaCompeticao = response.body();
                    bundle.putParcelableArrayList("listaDeJogosDaCompeticao", (ArrayList<Jogos>) listaDeJogosDaCompeticao);
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "falha jogosDaCompetição: " + t.getMessage());
            }
        });
    }

    public void predicoes() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Predicoes>> chamadaPredicoes = service.listarPredicoes(jogo.getMatch_id());

        chamadaPredicoes.enqueue(new Callback<List<Predicoes>>() {
            @Override
            public void onResponse(Call<List<Predicoes>> call, Response<List<Predicoes>> response) {
                if(!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                }else {
                    listaDePredicao = response.body();

                    Fragment f1 = adapter.getPage(0);
                    if (f1 instanceof ResumoFragment) {
                        ((ResumoFragment)f1).probPreJogo(listaDePredicao);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Predicoes>> call, Throwable t) {
                Log.i("info", "falha predições: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
