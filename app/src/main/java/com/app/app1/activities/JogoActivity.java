package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.app.app1.R;
import com.app.app1.RetrofitService;
import com.app.app1.fragments.jogo.EstatisticasFragment;
import com.app.app1.fragments.jogo.ResumoFragment;
import com.app.app1.fragments.jogo.RetrospectoFragment;
import com.app.app1.fragments.jogo.TabelaFragment;
import com.app.app1.model.Jogos;

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
    private ViewPager vpJogo;
    private SmartTabLayout tabJogo;
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        //referenciação
        tabJogo = findViewById(R.id.tabJogo);
        vpJogo = findViewById(R.id.vpJogo);

        //action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        //objetos recebidos
        jogo = getIntent().getExtras().getParcelable("jogo");

        //chamada retrospecto(ultimos jogos)
        RetrospectoCasaAsyncTask retrospectoCasaAsyncTask = new RetrospectoCasaAsyncTask();
        retrospectoCasaAsyncTask.execute();
        RetrospectoVisAsyncTask retrospectoVisAsyncTask = new RetrospectoVisAsyncTask();
        retrospectoVisAsyncTask.execute();

        //chamada tabela
        TabelaAsyncTask tabelaAsyncTask = new TabelaAsyncTask();
        tabelaAsyncTask.execute();

        JogosDaCompeticaoAsyncTask jogosDaCompeticaoAsyncTask = new JogosDaCompeticaoAsyncTask();
        jogosDaCompeticaoAsyncTask.execute();

        tabs();
    }


    private void tabs() {
        //enviar para os fragments
        bundle.putParcelable("jogo", jogo);

        FragmentPagerItems pages = FragmentPagerItems.with(getApplicationContext())
                .add("resumo", ResumoFragment.class, bundle)
                .add("estatisticas", EstatisticasFragment.class, bundle)
                .add("retrospecto", RetrospectoFragment.class, bundle)
                .add("tabela", TabelaFragment.class, bundle)
                .create();

        FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);

        vpJogo.setAdapter(adapter);
        tabJogo.setViewPager(vpJogo);
    }


    public class RetrospectoCasaAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
            Call<List<Jogos>> requestUltimosJogos = service.listarJogosDaEquipe(data1MesAtras(), dataHoje(),
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
                }
            });
            return null;
        }
    }

    public class RetrospectoVisAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
            Call<List<Jogos>> requestUltimosJogos = service.listarJogosDaEquipe(data1MesAtras(), dataHoje(),
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
                }
            });
            return null;
        }
    }


    public class TabelaAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
            Call<List<Tabela>> requestTabela = service.listarTabela(jogo.getLeague_id());

            requestTabela.enqueue(new Callback<List<Tabela>>() {
                @Override
                public void onResponse(Call<List<Tabela>> call, Response<List<Tabela>> response) {
                    if(!response.isSuccessful()) {
                        Log.i("info", "erro na resposta: " + response.message());
                    }else {
                        listaTabela = response.body();
                        Log.i("info", "lalla" + listaTabela.get(0).getTeam_name());
                        bundle.putParcelableArrayList("tabela",(ArrayList<Tabela>) listaTabela);
                    }
                }

                @Override
                public void onFailure(Call<List<Tabela>> call, Throwable t) {
                    Log.i("info", "deu merda: " + t.getMessage());
                }
            });
            return null;
        }
    }

    public class JogosDaCompeticaoAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
            Call<List<Jogos>> requestJogosDaCompeticao = service.listarJogosDaCompeticao(dataHoje(), dataHoje(),
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
                }
            });
            return null;
        }
    }


    public String dataHoje() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);
        String dataHoje = ano + "-" + (mes+1) + "-" + dia;
        return  dataHoje;
    }

    public String data1MesAtras() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);
        if(mes==0) { //janeiro
            ano -=1;
        }
        String dataHoje = ano + "-" + (mes) + "-" + dia;
        return  dataHoje;
    }


    //finalizar a activity ao pressionar btn back
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
