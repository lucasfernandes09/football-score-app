package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class JogosCompeticaoActivity extends AppCompatActivity implements AdapterJogos.JogoListener {
    private ArrayList<Jogos> listaDeJogosDeUmaCompeticao = new ArrayList<>();
    private RecyclerView rvJogosDeUmaCompeticao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos_competicao);
        //referenciação
        rvJogosDeUmaCompeticao = findViewById(R.id.rvJogosDeUmaCompeticao);

        //recuperar dados de competiçoesFragment
        listaDeJogosDeUmaCompeticao = getIntent().getParcelableArrayListExtra("listaDeJogosDeUmaCompeticao");

        //actionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(listaDeJogosDeUmaCompeticao.get(0).getLeague_name());

        //configs iniciais
        ImageView ivJogosCompeticao = findViewById(R.id.ivJogosCompeticao);
        TextView tvJogosCompeticao = findViewById(R.id.tvJogosCompeticao);
        Picasso.get().load(listaDeJogosDeUmaCompeticao.get(0).getLeague_logo()).into(ivJogosCompeticao);
        String label = listaDeJogosDeUmaCompeticao.get(0).getLeague_name() + ", " + listaDeJogosDeUmaCompeticao.get(0).getMatch_round().replace("Round", "rodada");
        tvJogosCompeticao.setText(label);

        //exibir lista de competições
        AdapterJogos adapterJogos = new AdapterJogos(listaDeJogosDeUmaCompeticao, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvJogosDeUmaCompeticao.setLayoutManager(layoutManager);
        rvJogosDeUmaCompeticao.setHasFixedSize(true);
        rvJogosDeUmaCompeticao.setAdapter(adapterJogos);
    }

    @Override
    public void jogoClick(int position) {
        Jogos jogo = listaDeJogosDeUmaCompeticao.get(position);
        Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
        intent.putExtra("jogo", jogo);
        startActivity(intent);
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
