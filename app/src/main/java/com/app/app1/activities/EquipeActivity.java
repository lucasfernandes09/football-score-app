package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.app.app1.R;
import com.app.app1.RetrofitService;
import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.fragments.equipe.EquipeFragment;
import com.app.app1.fragments.equipe.ElencoFragment;
import com.app.app1.fragments.equipe.PartidasFragment;
import com.app.app1.helper.JogosSalvos;
import com.app.app1.helper.UsuarioFirebase;
import com.app.app1.model.Jogos;
import com.app.app1.model.Times;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipeActivity extends AppCompatActivity {

    private ViewPager vpEquipe;
    private SmartTabLayout tabEquipe;
    private List<Jogos> listaDeUltimosJogos, listaDeProximosJogos, listaDeJogosSalvos;
    private Jogos jogo = new Jogos();
    private Boolean home;
    private String team_id;
    private String nomeEquipe;
    private List<Times> listaDeElenco;
    private Bundle bundle = new Bundle();
    private DatabaseReference jogosRef;
    private ValueEventListener eventListener;
    private FragmentStatePagerItemAdapter statePagerItemAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe);
        //referenciação
        vpEquipe = findViewById(R.id.vpEquipe);
        tabEquipe = findViewById(R.id.tabEquipe);

        //recuperar objetos enviados de ResumoFragment(JogoActivity)
        jogo = getIntent().getExtras().getParcelable("jogo");
        home = getIntent().getExtras().getBoolean("home");
        if(home) {
            nomeEquipe = jogo.getMatch_hometeam_name();
            team_id = jogo.getMatch_hometeam_id();
        }else {
            nomeEquipe = jogo.getMatch_awayteam_name();
            team_id = jogo.getMatch_awayteam_id();
        }

        //action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(nomeEquipe);

        chamadaUtimosJogos();
        chamadaProximosJogos();
        chamadaElenco();

        tabs();
    }

    public void tabs() {
        bundle.putParcelable("jogo", jogo);
        bundle.putBoolean("home", home);
        bundle.putString("nomeEquipe", nomeEquipe);

        //tabs
        FragmentPagerItems pages = FragmentPagerItems.with(getApplicationContext())
                .add("Equipe", EquipeFragment.class, bundle)
                .add("Partidas", PartidasFragment.class, bundle)
                .add("Elenco", ElencoFragment.class, bundle)
                .create();

        statePagerItemAdapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);

        vpEquipe.setAdapter(statePagerItemAdapter);
        tabEquipe.setViewPager(vpEquipe);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void chamadaUtimosJogos() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> requestUltimosJogos = service.listarJogosDaEquipe(data2MesesAtras(), dataHoje(), team_id);

        requestUltimosJogos.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeUltimosJogos = response.body();
                    Collections.reverse(listaDeUltimosJogos);
                    bundle.putParcelableArrayList("listaDeUltimosJogos", (ArrayList<Jogos>) listaDeUltimosJogos);
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "falha últimos jogos: " + t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void chamadaProximosJogos() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> requestProximosJogos = service.listarJogosDaEquipe(dataHoje(), dataEm2Meses(), team_id);

        requestProximosJogos.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeProximosJogos = response.body();
                    bundle.putParcelableArrayList("listaDeProximosJogos", (ArrayList<Jogos>) listaDeProximosJogos);
                    if(UsuarioFirebase.getUsuarioAtual() != null) {
                        recuperarJogosSalvos();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "falha próximos jogos: " + t.getMessage());
            }
        });
    }

    public void chamadaElenco() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Times>> requestElenco = service.listarElenco(team_id);

        requestElenco.enqueue(new Callback<List<Times>>() {
            @Override
            public void onResponse(Call<List<Times>> call, Response<List<Times>> response) {
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeElenco = response.body();
                    bundle.putParcelableArrayList("listaDeElenco", (ArrayList<Times>) listaDeElenco);
                    //listaDeMelhoresJogadores = response.body();
                    //bundle.putParcelableArrayList("listaDeMelhoresJogadores", (ArrayList<Times>) listaDeMelhoresJogadores);
                }
            }

            @Override
            public void onFailure(Call<List<Times>> call, Throwable t) {
                Log.i("info", "falha elenco: " + t.getMessage());
            }
        });
    }

    public void recuperarJogosSalvos() {
        String idUsuario = UsuarioFirebase.getIdUsuario();
        jogosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios").child(idUsuario).child("listaDeJogos");
        eventListener = jogosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeJogosSalvos = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    listaDeJogosSalvos.add(ds.getValue(Jogos.class));
                }
                JogosSalvos.setarJogosSalvos(listaDeProximosJogos, listaDeJogosSalvos);
                statePagerItemAdapter.notifyDataSetChanged();
                Log.i("info", "onDataChange EquipeActivity");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("info", databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //para o listener dessa activity não ficar executando
        if(UsuarioFirebase.getUsuarioAtual() != null) {
            jogosRef.removeEventListener(eventListener);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O) //se api min não for 26 vai crashar
    public String dataHoje() {
        LocalDate ld = LocalDate.now();
        return  ld.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String dataEm2Meses() {
        LocalDate ld = LocalDate.now().plusMonths(2);
        return  ld.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String data2MesesAtras() {
        LocalDate ld = LocalDate.now().minusMonths(2);
        return  ld.toString();
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
