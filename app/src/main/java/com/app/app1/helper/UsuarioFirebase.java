package com.app.app1.helper;

import android.util.Log;

import com.app.app1.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioFirebase {

    //private static FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    public static String getIdUsuario() {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao == null) {
            Log.i("info", "aqui autenticacao == null");
            return null;
        }else{
            return Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        }
    }

    public static FirebaseUser getUsuarioAtual() {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao == null) {
            return null;
        }else{
            return autenticacao.getCurrentUser();
        }
    }
}
