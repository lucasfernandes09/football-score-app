package com.app.app1.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.app1.R;
import com.app.app1.activities.JogoActivity;
import com.app.app1.adapters.AdapterJogos;
import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.helper.UsuarioFirebase;
import com.app.app1.model.Jogos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioLogadoActivity extends AppCompatActivity implements AdapterJogos.JogoListener {

    private List<Jogos> listaDeJogosSalvos = new ArrayList<>();
    private DatabaseReference jogosRef;
    private FirebaseUser usuarioFirebase;
    private AdapterJogos adapterJogos;
    private RecyclerView rvJogosSalvosUser;
    private ValueEventListener eventListener;
    private TextView tvNomeUsuario, tvEmailUsuario;

    Retrofit retrofit;
    String baseUrl;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logado);
        //referenciação
        rvJogosSalvosUser = findViewById(R.id.rvJogosSalvosUser);
        String idUsuario = UsuarioFirebase.getIdUsuario();
        jogosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios").child(idUsuario).child("listaDeJogos");
        usuarioFirebase = UsuarioFirebase.getUsuarioAtual();
        tvEmailUsuario = findViewById(R.id.tvEmailUsuario);

        tvEmailUsuario.setText(usuarioFirebase.getEmail());

        //btn back action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recuperarJogosSalvos();
        recyclerJogosSalvos();

        baseUrl = "https://fcm.googleapis.com/fcm/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    protected void onStop() {
        super.onStop();
        jogosRef.removeEventListener(eventListener);
    }

    public void recuperarJogosSalvos() {
        eventListener = jogosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //faz a leitura de forma assíncrona
                listaDeJogosSalvos.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    listaDeJogosSalvos.add(ds.getValue(Jogos.class));
                }
                //(ordenar lista)
                adapterJogos.notifyDataSetChanged();
                Log.i("info", "onDataChange usuarioLogadoActivity");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("info", databaseError.getMessage());
            }
        });
    }

    public void recyclerJogosSalvos() {
        adapterJogos = new AdapterJogos(listaDeJogosSalvos, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvJogosSalvosUser.setLayoutManager(layoutManager);
        rvJogosSalvosUser.setHasFixedSize(true);
        rvJogosSalvosUser.setAdapter(adapterJogos);
    }

    public void deslogarUsuario(View view) {
        FirebaseAuth usuarioAutenticado = ConfiguracaoFirebase.getFirebaseAutenticacao();
        usuarioAutenticado.signOut();
        Toast.makeText(UsuarioLogadoActivity.this, "usuário deslogado", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void jogoClick(int position) {
        Jogos jogo = listaDeJogosSalvos.get(position);
        Intent intent = new Intent(UsuarioLogadoActivity.this, JogoActivity.class);
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

    /*public void enviarNot(View view) {
        recuperarToken();

        //monta objeto notificação
        Notificacao notificacao = new Notificacao("titulo", "aqui é o corpo");
        NotificacaoDados notificacaoDados = new NotificacaoDados(token, notificacao);

        NotificacaoService service = retrofit.create(NotificacaoService.class);
        Call<NotificacaoDados> chamada = service.enviarNotificacao(notificacaoDados);

        chamada.enqueue(new Callback<NotificacaoDados>() {
            @Override
            public void onResponse(Call<NotificacaoDados> call, Response<NotificacaoDados> response) {
                Log.i("infoNotificação", "" + response.code());
            }

            @Override
            public void onFailure(Call<NotificacaoDados> call, Throwable t) {

            }
        });
    }

    public void recuperarToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                token = task.getResult().getToken();
                Log.i("infoNotificação", "" + token);
            }
        });
    }*/

}
