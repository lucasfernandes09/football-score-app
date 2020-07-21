package com.app.app1.helper;

import java.util.Calendar;

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
}
