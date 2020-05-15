package com.app.app1.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.app.app1.R;
import com.app.app1.RetrofitService;
import com.app.app1.activities.user.LoginActivity;
import com.app.app1.activities.user.UsuarioLogadoActivity;
import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.fragments.main.CompeticoesFragment;
import com.app.app1.fragments.main.JogosAoVivoFragment;
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
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private List<Jogos> listaDeJogos = new ArrayList<>();
    private String dataSelecionada = "";
    private String dataAtual = dataHoje();
    private FirebaseAuth autenticacao;
    private DatabaseReference firebaseDatabase;
    private List<Jogos> listaDeJogosSalvos = new ArrayList<>();
    private Handler handler;
    private Bundle bundle;
    private FragmentStatePagerItemAdapter statePagerItemAdapter;
    private View.OnClickListener onClickAoVivo, onClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //actionBar
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        //referenciação
        progressBar = findViewById(R.id.progressBar);
        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);
        fab = findViewById(R.id.fab);

        //modo noturno
        SharedPreferences preferences = getSharedPreferences(ConfigActivity.ARQUIVO_PREFERENCIA, 0);
        if(preferences.contains("modoAtual")) {
            int modoAtual = preferences.getInt("modoAtual", 1);
            AppCompatDelegate.setDefaultNightMode(modoAtual);
        }

        configClicksAoVivo();
        fab.setOnClickListener(onClickAoVivo);

        bundle = new Bundle();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();

        tabs();

        handler = new Handler();
        handler.post(new AtualizarDados());
    }

    private final class AtualizarDados implements Runnable {
        @Override
        public void run() {
            chamadaJogosAPI();
            handler.postDelayed(this, 60*1000); //60s'
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
                        dataAtual = dataSelecionada;
                        chamadaJogosAPI();
                    }
                }
            }, ano, mes, dia);
            datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
            datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
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

    public void chamadaJogosAPI() {
        RetrofitService service = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<Jogos>> request = service.listarJogos(dataAtual, dataAtual);

        request.enqueue(new Callback<List<Jogos>>() {
            @Override
            public void onResponse(Call<List<Jogos>> call, Response<List<Jogos>> response) {  //onResponse e onFailure são rodados na main thread
                if (!response.isSuccessful()) {
                    Log.i("info", "erro na resposta: " + response.message());
                } else {
                    listaDeJogos = response.body();
                    bundle.putParcelableArrayList("listaDeJogos", (ArrayList<Jogos>) listaDeJogos);
                    progressBar.setVisibility(View.GONE);

                    if(UsuarioFirebase.getUsuarioAtual() == null) {
                        tabs();
                    }else {
                        recuperarJogosSalvos();
                    }
                    //estudar método onResponse
                }
            }

            @Override
            public void onFailure(Call<List<Jogos>> call, Throwable t) {
                Log.i("info", "onFailure " + t.getMessage());
                progressBar.setVisibility(View.GONE);
                //tabsSemEvento();
                listaDeJogos.clear();
                bundle.putParcelableArrayList("listaDeJogos", (ArrayList<Jogos>) listaDeJogos); //lista vazia
                tabs();
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
                Log.i("info", "onDataChange MainActivity");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("info", databaseError.getMessage());
            }
        });
    }

    public void tabs() {
        FragmentPagerItems pages = FragmentPagerItems.with(this)
                .add("Jogos", JogosFragment.class, bundle)
                .add("Competições", CompeticoesFragment.class, bundle)
                .create();

        statePagerItemAdapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(statePagerItemAdapter);
        smartTabLayout.setViewPager(viewPager);
        Log.i("info", "abc");
    }

    public void tabsJogosAoVivo() {
        FragmentPagerItems pages = FragmentPagerItems.with(this)
                .add("Jogos", JogosAoVivoFragment.class, bundle)
                .add("Competições", CompeticoesFragment.class, bundle)
                .create();

        statePagerItemAdapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(statePagerItemAdapter);
        smartTabLayout.setViewPager(viewPager);
    }

    public void configClicksAoVivo() {
        onClickAoVivo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabsJogosAoVivo();
                fab.setImageResource(R.drawable.ic_fechar);
                fab.setOnClickListener(onClick);
            }
        };

        onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabs();
                fab.setImageResource(R.drawable.ic_relogio_24dp);
                fab.setOnClickListener(onClickAoVivo);
            }
        };
    }

    public String dataHoje() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);
        String dataHoje = ano + "-" + (mes+1) + "-" + dia;
        return  dataHoje;
    }

}

