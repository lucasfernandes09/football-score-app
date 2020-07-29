package com.app.app1.helper;


import android.util.Log;

import com.app.app1.model.Jogos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatasUtil {
    private static LocalDate localDate = LocalDate.now();

    public static String dataHoje() {
        return localDate.toString();
    }

    public static String data2MesesAtras() {
        return localDate.minusMonths(2).toString();
    }

    public static String dataEm2Meses() {
        return localDate.plusMonths(2).toString();
    }

    public static List<Jogos> configData(List<Jogos> listaDeJogos) {
        /*LocalDate localDate = LocalDate.parse(listaDeJogos.get(0).getMatch_date());
        LocalTime localTime = LocalTime.parse(listaDeJogos.get(0).getMatch_time());
        ZonedDateTime zonedDateTime = ZonedDateTime.now();*/

        int nIniciais;
        for(int i=0; i<listaDeJogos.size(); i++) {
            nIniciais = Integer.parseInt(listaDeJogos.get(i).getMatch_time().substring(0,2));
            nIniciais -= 5;
            listaDeJogos.get(i).setMatch_time(nIniciais + ":" + listaDeJogos.get(i).getMatch_time().substring(3,5));
        }

        List<Jogos> listaDeJogosFinal = new ArrayList<>();
        for (Jogos jogo : listaDeJogos) {
            if(!jogo.getMatch_time().contains("-")) {
                listaDeJogosFinal.add(jogo);
            }
        }

        return listaDeJogosFinal;
    }
}
