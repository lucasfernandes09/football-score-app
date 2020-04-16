package com.app.app1.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.model.Jogos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JogosSalvos {

    //private static List<Jogos> listaDeJogosSalvos;
    //private static DatabaseReference databaseRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios").child(UsuarioFirebase.getIdUsuario()).child("listaDeJogos");

     /*public List<Jogos> getJogosSalvos() {
        //String idUsuario = ;

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeJogosSalvos = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    listaDeJogosSalvos.add(ds.getValue(Jogos.class));
                }
                Log.i("info", "onDataChange JogosSalvos");
                Log.i("info", "" + listaDeJogosSalvos.isEmpty());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listaDeJogosSalvos;
    }*/

    public static List<Jogos> setarJogosSalvos(List<Jogos> listaDeJogos, List<Jogos> listaDeJogosSalvos) {
        for(int i=0; i<listaDeJogos.size(); i++) {
            listaDeJogos.get(i).setSelecionado(false);       //antes de verificar, seta todos para falso
            for(int j=0; j<listaDeJogosSalvos.size(); j++) {
                if(listaDeJogos.get(i).getMatch_id() == listaDeJogosSalvos.get(j).getMatch_id()) {
                    listaDeJogos.get(i).setSelecionado(true);
                }
            }
        }

        return listaDeJogos;
    }
}
