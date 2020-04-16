package com.app.app1.model;

import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String idUsuario;
    private String email;
    private String senha;
    private String confirmarSenha;

    public Usuario() {
    }

    public void salvarUsuario() {
        DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebaseDatabase.child("usuarios").child(this.idUsuario).child("dados").setValue(this);
    }


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude  //nao salva no db
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }


}
