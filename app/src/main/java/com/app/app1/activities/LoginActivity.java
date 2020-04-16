package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.app1.R;
import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar, btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //referenciação
        etEmail = findViewById(R.id.etEmailL);etSenha = findViewById(R.id.etSenhaL);
        btnEntrar = findViewById(R.id.btnEntrar); btnCadastrar = findViewById(R.id.btnCadastrar);

        //btn back action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String senha = etSenha.getText().toString();

                //validar campos
                if(!email.isEmpty()) {
                    if(!senha.isEmpty()) {
                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        validarLogin();

                    }else {
                        Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }

    public void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "usuário logado com sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UsuarioLogadoActivity.class));
                            finish();
                        }else {
                            String excecao;
                            try {
                                throw task.getException();  //=> lançar uma exceção
                            }catch (FirebaseAuthInvalidUserException e) {
                                excecao = "usuário não cadastrado.";
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "senha incorreta.";
                            }catch (Exception e) {
                                excecao = "erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace(); //mostrar no log
                            }

                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null) {
            finish();
        }
    }

    public void esqueceuSenha(View view) {
        startActivity(new Intent(this, AlterarSenhaActivity.class));
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
