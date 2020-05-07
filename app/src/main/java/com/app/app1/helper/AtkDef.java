package com.app.app1.helper;

import android.util.Log;

import com.app.app1.model.Jogos;

import java.util.ArrayList;

public class AtkDef {

    //ATK
    private static float apvNoGol1, fNoGol1, fNoGol2;
    private static float apvFfora1, fFora1, fFora2;
    private static float apvAPerigosos1, aPerigosos1, aPerigosos2;
    private static float tGols, gFeitos1, gFeitos2;
    //DEF
    private static float apvFBloqueadas1, fBloqueadas1, fBloqueadas2;
    private static float apvDefGoleiro1, DefGoleiro1, DefGoleiro2;
    private static float apvDesarmes1 = 0, desarmes1, desarmes2;
    private static float golsNaoSofridos1, golsNaoSofridos2;


    public static ArrayList<Float> getAtkDef(Jogos jogo) {
        recuperarEstatisticas(jogo);

        //gols
        gFeitos1 = Float.parseFloat(jogo.getMatch_hometeam_score());
        gFeitos2 = Float.parseFloat(jogo.getMatch_awayteam_score());
        tGols = gFeitos1 + gFeitos2;
        float apvGFeitos1 = 0;
        if(gFeitos1 != 0) {
            apvGFeitos1 = gFeitos1 / tGols;
        }
        golsNaoSofridos1 = tGols - gFeitos2;
        float apvGNaoSofridos1 = golsNaoSofridos1 / tGols;

        //Média ATK
        float atk1 = (apvNoGol1 + apvFfora1 + apvAPerigosos1 + apvGFeitos1)/4;
        float atk2 = 1 - atk1;

        //Média DEF
        float def1 = (apvFBloqueadas1 + apvDefGoleiro1 + apvGNaoSofridos1)/3;
        float def2 = 1 - def1;

        ArrayList<Float> listaDeTermos = new ArrayList<>();
        listaDeTermos.add(atk1*100);
        listaDeTermos.add(atk2*100);
        listaDeTermos.add(def1*100);
        listaDeTermos.add(def2*100);

        return listaDeTermos;
    }

    public static void recuperarEstatisticas(Jogos jogo) {
        for (int i=0; i<jogo.getStatistics().size(); i++) {
            float home = Float.parseFloat(jogo.getStatistics().get(i).getHome().replace("%", ""));
            float away = Float.parseFloat(jogo.getStatistics().get(i).getAway().replace("%", ""));

            switch (jogo.getStatistics().get(i).getType()) {
                case "Shots on Goal":
                    fNoGol1 = home;
                    fNoGol2 = away;
                    apvNoGol1 = fNoGol1 / (fNoGol1 + fNoGol2);
                    Log.i("info", "apvNoGol1 " + apvNoGol1);
                    break;

                case "Shots off Goal":
                    fFora1 = home;
                    fFora2 = away;
                    apvFfora1 = fFora1 / (fFora1 + fFora2);
                    Log.i("info", "apvFfora1 " + apvFfora1);
                    break;

                case "Dangerous Attacks":
                    aPerigosos1 = home;
                    aPerigosos2 = away;
                    apvAPerigosos1 = aPerigosos1 / (aPerigosos1 + aPerigosos2);
                    Log.i("info", "apvAPerigosos1 " + apvAPerigosos1);
                    break;

                case "Blocked Shots":
                    fBloqueadas1 = home;
                    fBloqueadas2 = away;
                    apvFBloqueadas1 = fBloqueadas2 / (fBloqueadas1 + fBloqueadas2);
                    Log.i("info", "apvFBloqueadas1 " + apvFBloqueadas1);
                    break;

                case "Goalkeeper Saves":
                    DefGoleiro1 = home;
                    DefGoleiro2 = away;
                    apvDefGoleiro1 = DefGoleiro1 / (DefGoleiro1 + DefGoleiro2);
                    Log.i("info", "apvDefGoleiro1 " + apvDefGoleiro1);
                    break;

                case "Tackles":
                    desarmes1 = home;
                    desarmes2 = away;
                    apvDesarmes1 = desarmes1 / (desarmes1 + desarmes2);
                    Log.i("info", "apvDesarmes1 " + apvDesarmes1);
                    break;
            }
        }
    }


}
