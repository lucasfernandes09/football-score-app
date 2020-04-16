package com.app.app1.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebaseDatabase;

    public static FirebaseAuth getFirebaseAutenticacao() {
        //verificar se tem uma instancia desse objeto
        if(autenticacao == null ) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    public static DatabaseReference getFirebaseDatabase()  {
        if(firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return firebaseDatabase;
    }

}
