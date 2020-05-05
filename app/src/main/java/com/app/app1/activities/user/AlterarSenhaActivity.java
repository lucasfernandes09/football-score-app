package com.app.app1.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.app1.R;
import com.app.app1.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AlterarSenhaActivity extends AppCompatActivity {

    private EditText etASEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Alterar Senha");

        etASEmail = findViewById(R.id.etASEmail);
    }

    public void enviarEmail(View view) {
        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = etASEmail.getText().toString().trim(); //remove espaços no inicio e fim da string
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AlterarSenhaActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(AlterarSenhaActivity.this, "Email não cadastrado", Toast.LENGTH_SHORT).show();
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
