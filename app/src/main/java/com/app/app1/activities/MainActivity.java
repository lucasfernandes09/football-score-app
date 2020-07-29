package com.app.app1.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.app.app1.R;
import com.app.app1.helper.DatasUtil;
import com.app.app1.services.RetrofitService;
import com.app.app1.activities.user.LoginActivity;
import com.app.app1.activities.user.UsuarioLogadoActivity;
import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.fragments.main.CompeticoesFragment;
import com.app.app1.fragments.main.JogosFragment;
import com.app.app1.helper.JogosSalvos;
import com.app.app1.helper.UsuarioFirebase;
import com.app.app1.model.Jogos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;
    private FloatingActionButton fab;
    private List<Jogos> listaDeJogos = new ArrayList<>();
    private String dataSelecionada = "";
    private String dataAtual = DatasUtil.dataHoje();
    private TextView tvSemEventos;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebaseDatabase;
    private List<Jogos> listaDeJogosSalvos = new ArrayList<>();
    private Handler handler;
    private Bundle bundle;
    private FragmentPagerItemAdapter adapter;
    private View.OnClickListener onClickAoVivo, onClickPadrao;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //referenciação
        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);
        fab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progressBar);
        tvSemEventos = findViewById(R.id.tvSemEventos);

        //modo noturno
        SharedPreferences preferences = getSharedPreferences(ConfigActivity.ARQUIVO_PREFERENCIA, 0);
        if(preferences.contains("modoAtual")) {
            int modoAtual = preferences.getInt("modoAtual", 1);
            AppCompatDelegate.setDefaultNightMode(modoAtual);
        }

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();

        bundle = new Bundle();

        callJogosAPI();

        handler = new Handler();
        handler.postDelayed(new AtualizarDados(), 60*1000);

        configClicksAoVivo();
        fab.setOnClickListener(onClickAoVivo);
    }

    private final class AtualizarDados implements Runnable {
        @Override
        public void run() {
            handler.postDelayed(this, 60*1000); //60s
            updateJogosAPI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //calendário
        if (id == R.id.item_calendario) {
            //pegar calendario
            Calendar calendar = Calendar.getInstance();
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            //lib Material DateTimePicker Dialog
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    dataSelecionada = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                    if(!dataSelecionada.equals(dataAtual)) {
                        progressBar.setVisibility(View.VISIBLE);
                        tvSemEventos.setVisibility(View.GONE);
                        dataAtual = dataSelecionada;
                        callJogosAPI();
                        Log.i("infoData", "onDataSet calendario");
                    }
                }
            }, ano, mes, dia);
            datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
            datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
            //modo noturno calendário
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) { datePickerDialog.setThemeDark(true); }
            datePickerDialog.setTitle("Selecione um dia");
            datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
        }

        //usuário
        if(id == R.id.item_usuario) {
            if(autenticacao.getCurrentUser() != null) {
                startActivity(new Intent(this, UsuarioLogadoActivity.class));
            }else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }

        //configs
        if(id == R.id.item_config) {
            startActivity(new Intent(this, ConfigActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void callJogosAPI() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> request = service.listarJogos(dataAtual, dataAtual);

        request.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {  //onResponse e onFailure são rodados na main thread
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {

                    if(response.body() != null) {
                        listaDeJogos = DatasUtil.configData(response.body());
                    }

                    bundle.putParcelableArrayList("listaDeJogos", (ArrayList<Jogos>) listaDeJogos);
                    progressBar.setVisibility(View.GONE);


                    //verrifica se há user logado para recuperar jogos salvos
                    if(UsuarioFirebase.getUsuarioAtual() == null) {
                        tabs();
                    }else {
                        recuperarJogosSalvos();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "onFailure " + t.getMessage());
                configSemEventos();
            }
        });
    }

    public void updateJogosAPI() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> request = service.listarJogos(dataAtual, dataAtual);

        request.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {  //onResponse e onFailure são rodados na main thread
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {

                    if(response.body() != null) {
                        listaDeJogos = DatasUtil.configData(response.body());
                    }

                    //verrifica se há user logado para recuperar jogos salvos
                    if(UsuarioFirebase.getUsuarioAtual() == null) {
                        //recupera a instancia atual dos fragments(smartTabLayout)
                        Fragment f1 = adapter.getPage(0);
                        if (f1 instanceof CompeticoesFragment) {
                            ((CompeticoesFragment)f1).atualizarLista(listaDeJogos);
                        }
                        Fragment f2 = adapter.getPage(1);
                        if (f2 instanceof JogosFragment) {
                            ((JogosFragment)f2).atualizarLista(listaDeJogos);
                        }
                    }else {
                        recuperarJogosSalvos();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "onFailure " + t.getMessage());
            }
        });
    }

    public void recuperarJogosSalvos() {
        String idUsuario = UsuarioFirebase.getIdUsuario();
        DatabaseReference jogosRef = firebaseDatabase.child("usuarios").child(idUsuario).child("listaDeJogos");

        jogosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeJogosSalvos.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    listaDeJogosSalvos.add(ds.getValue(Jogos.class));
                }
                JogosSalvos.setarJogosSalvos(listaDeJogos, listaDeJogosSalvos);
                tabs();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("info", databaseError.getMessage());
            }
        });
    }

    public void tabs() {
        smartTabLayout.setVisibility(View.VISIBLE);

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Competições", CompeticoesFragment.class, bundle)
                .add("Jogos", JogosFragment.class, bundle)
                .create());

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    public void configClicksAoVivo() {

        onClickAoVivo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listaDeJogos != null) {

                    Fragment f1 = adapter.getPage(0);
                    if (f1 instanceof CompeticoesFragment) {
                        ((CompeticoesFragment)f1).new CompeticoesAoVivo(true).execute();
                    }

                    Fragment f2 = adapter.getPage(1);
                    if (f2 instanceof JogosFragment) {
                        ((JogosFragment)f2).new JogosAoVivo(true).execute();
                    }
                }

                fab.setImageResource(R.drawable.ic_fechar);
                fab.setOnClickListener(onClickPadrao);
            }
        };

        onClickPadrao = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listaDeJogos != null) {
                    /*Fragment f1 = adapter.getPage(0);
                if (f1 instanceof CompeticoesFragment) {
                    ((CompeticoesFragment)f1).new CompeticoesAoVivo(false).execute();
                }

                Fragment f2 = adapter.getPage(1);
                if (f2 instanceof JogosFragment) {
                    ((JogosFragment)f2).new JogosAoVivo(false).execute();
                }*/
                }

                fab.setImageResource(R.drawable.ic_relogio_24dp);
                fab.setOnClickListener(onClickAoVivo);
            }
        };
    }

    public void configSemEventos() {
        progressBar.setVisibility(View.GONE);
        tvSemEventos.setVisibility(View.VISIBLE);
        bundle.clear();
        listaDeJogos = null;
        tabs();
    }

}

