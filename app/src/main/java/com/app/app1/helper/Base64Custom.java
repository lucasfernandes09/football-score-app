package com.app.app1.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto) {                                  //remove espaços no inicio e fim
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    public static String decodificarBase64(String textoCodificado) {
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }
}
