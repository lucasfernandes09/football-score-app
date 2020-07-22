package com.app.app1.helper;


import com.app.app1.model.Jogos;

import java.util.Calendar;
import java.util.List;

public class DatasUtil {
    private static Calendar calendar = Calendar.getInstance();
    private static int dia = calendar.get(Calendar.DAY_OF_MONTH);
    private static int mes = calendar.get(Calendar.MONTH);
    private static int ano = calendar.get(Calendar.YEAR);

    public static String dataHoje() {
        return ano + "-" + (mes+1) + "-" + dia;
    }

    public static String data2MesesAtras() {
        //janeiro
        if(mes==0) {
            ano -= 1;
        }
        return ano + "-" + (mes-1) + "-" + dia;
    }

    public static String dataEm2Meses() {
        //dezembro
        if(mes==11) {
            ano += 1;
        }
        return ano + "-" + (mes+3) + "-" + dia;
    }

    public static List<Jogos> configData(List<Jogos> listaDeJogos) {
        int nIniciais;

        for(int i=0; i<listaDeJogos.size(); i++) {
            nIniciais = Integer.parseInt(listaDeJogos.get(i).getMatch_time().substring(0,2));
            nIniciais -= 5;
            listaDeJogos.get(i).setMatch_time(nIniciais + ":" + listaDeJogos.get(i).getMatch_time().substring(3,5));
        }
        return listaDeJogos;
    }
}
