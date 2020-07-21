package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.app1.R;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.model.Jogos;

import java.util.ArrayList;
import java.util.Objects;

public class ListaDeJogosActivity extends AppCompatActivity implements AdapterJogos.JogoListener {
    ArrayList<Jogos> listaDeJogosDeUmaCompeticao = new ArrayList<>();
    RecyclerView rvJogosDeUmaCompeticao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_jogos);
        //referenciação
        rvJogosDeUmaCompeticao = findViewById(R.id.rvJogosDeUmaCompeticao);

        //btn back action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //recuperar dados de competiçoesFragment
        listaDeJogosDeUmaCompeticao = getIntent().getParcelableArrayListExtra("listaDeJogosDeUmaCompeticao");


        /** RecyclerView */
        //criar/configurar adapter
        AdapterJogos adapterJogos = new AdapterJogos(listaDeJogosDeUmaCompeticao, this);
        //configurar recyler
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
