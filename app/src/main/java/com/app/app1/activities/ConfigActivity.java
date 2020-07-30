package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.activities.user.LoginActivity;
import com.app.app1.activities.user.UsuarioLogadoActivity;
import com.app.app1.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ConfigActivity extends AppCompatActivity {

    private TextView tvLogin;
    private FirebaseAuth autenticacao;
    private Switch switchMN;
    public static final String ARQUIVO_PREFERENCIA = "arquivo do usuário";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        //referenciação
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        tvLogin = findViewById(R.id.tvLogin);
        switchMN = findViewById(R.id.switchMN);

        //actionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);


        configIniciais();
        configLogin();
        configModoNoturno();

    }

    public void configIniciais() {
        //modo noturno
        preferences = getSharedPreferences(ConfigActivity.ARQUIVO_PREFERENCIA, 0);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            switchMN.setChecked(true);
        }
    }

    public void configLogin() {
        if(autenticacao.getCurrentUser() != null) {
            tvLogin.setText(autenticacao.getCurrentUser().getEmail());
        }else {
            tvLogin.setText("Login");
        }
    }

    public void login(View view) {
        if(autenticacao.getCurrentUser() != null) {
            startActivity(new Intent(this, UsuarioLogadoActivity.class));
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    public void configModoNoturno() {
        switchMN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES
                    );
                    editor = preferences.edit();
                    editor.putInt("modoAtual", 2);
                    editor.apply();
                }else {
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO
                    );
                    editor = preferences.edit();
                    editor.putInt("modoAtual", 1);
                    editor.apply();
                }
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
