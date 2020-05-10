package com.app.app1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.app1.R;
import com.app.app1.activities.user.LoginActivity;
import com.app.app1.activities.user.UsuarioLogadoActivity;
import com.app.app1.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class ConfigActivity extends AppCompatActivity {

    private TextView tvLogin;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        tvLogin = findViewById(R.id.tvLogin);
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
}
