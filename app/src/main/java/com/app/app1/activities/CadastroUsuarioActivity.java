package com.app.app1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.app1.R;
import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.helper.Base64Custom;
import com.app.app1.model.Jogos;
import com.app.app1.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText etEmail, etSenha, etConfirmarSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        //referenciação
        etEmail = findViewById(R.id.etEmail);etSenha = findViewById(R.id.etSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);btnCadastrar = findViewById(R.id.btnCadastrar);

        //btn back action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                String confirmarSenha = etConfirmarSenha.getText().toString();

                //validar campos
                if(!email.isEmpty()) {
                    if(!senha.isEmpty()) {
                        if(!confirmarSenha.isEmpty()) {
                            if(senha.equals(confirmarSenha)) {
                                usuario = new Usuario();
                                usuario.setEmail(email);
                                usuario.setSenha(senha);
                                usuario.setConfirmarSenha(confirmarSenha);
                                cadastrarUsuario();
                            }else {
                                Toast.makeText(CadastroUsuarioActivity.this, "Senhas desiguais", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(CadastroUsuarioActivity.this, "Confirme a senha", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroUsuarioActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroUsuarioActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setIdUsuario(idUsuario);
                            usuario.salvarUsuario();
                            Toast.makeText(CadastroUsuarioActivity.this, "usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UsuarioLogadoActivity.class));
                            finish();
                        }else {
                            String excecao;
                            try {
                                throw task.getException();  //=> lançar uma exceção
                            }catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "digíte uma senha mais forte.";
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "email inválido.";
                            }catch (FirebaseAuthUserCollisionException e) {
                                excecao = "email já foi cadastrado.";
                            }catch (Exception e) {
                                excecao = "erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace(); //mostrar no log
                            }
                            Toast.makeText(CadastroUsuarioActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
