package com.app.app1.fragments.jogo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.app.app1.R;
import com.app.app1.model.Jogos;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstatisticasFragment extends Fragment {
    private Jogos jogo = new Jogos();

    private RoundCornerProgressBar pb1, pb2, pb3, pb4, pb5, pb6, pb7, pb8, pb9, pb10, pb11, pb12;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;
    private View lEstatisticas;

    public EstatisticasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatisticas, container, false);
        //referenciação
        lEstatisticas = view.findViewById(R.id.lEstatisticas);

        pb1 = view.findViewById(R.id.pb1);pb2 = view.findViewById(R.id.pb2);pb3 = view.findViewById(R.id.pb3);
        pb4 = view.findViewById(R.id.pb4);pb5 = view.findViewById(R.id.pb5);pb6 = view.findViewById(R.id.pb6);
        pb7 = view.findViewById(R.id.pb7);pb8 = view.findViewById(R.id.pb8);pb9 = view.findViewById(R.id.pb9);
        pb10 = view.findViewById(R.id.pb10);pb11 = view.findViewById(R.id.pb11);pb12 = view.findViewById(R.id.pb12);

        tv1 = view.findViewById(R.id.tv1);tv2 = view.findViewById(R.id.tv2);tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);tv5 = view.findViewById(R.id.tv5);tv6 = view.findViewById(R.id.tv6);
        tv7 = view.findViewById(R.id.tv7);tv8 = view.findViewById(R.id.tv8);tv9 = view.findViewById(R.id.tv9);
        tv10 = view.findViewById(R.id.tv10);tv11 = view.findViewById(R.id.tv11);tv12 = view.findViewById(R.id.tv12);


        //objeto recebido de JogoActivity
        jogo = getArguments().getParcelable("jogo");

        eventoRealizado();

        return view;
    }

    private void estatisticas() {
        for (int i=0; i<jogo.getStatistics().size(); i++) {
            float statsHome;
            float statsAway;

            switch (jogo.getStatistics().get(i).getType()) {
                case "Ball Possession":
                    tv1.setText(jogo.getStatistics().get(i).getHome());
                    tv2.setText(jogo.getStatistics().get(i).getAway());
                    statsHome = Float.parseFloat(jogo.getStatistics().get(i).getHome().replace("%", ""));
                    statsAway = Float.parseFloat(jogo.getStatistics().get(i).getAway().replace("%", ""));
                    pb1.setMax(100);pb2.setMax(100);
                    pb1.setProgress(statsHome);pb2.setProgress(statsAway);
                    break;

                case "Goal Attempts":
                    tv3.setText(jogo.getStatistics().get(i).getHome());
                    tv4.setText(jogo.getStatistics().get(i).getAway());
                    statsHome = Float.parseFloat(jogo.getStatistics().get(i).getHome());
                    statsAway = Float.parseFloat(jogo.getStatistics().get(i).getAway());
                    pb3.setMax(statsHome + statsAway);pb4.setMax(statsHome + statsAway);
                    pb3.setProgress(statsHome);pb4.setProgress(statsAway);
                    break;

                case "Shots on Goal":
                    tv5.setText(jogo.getStatistics().get(i).getHome());
                    tv6.setText(jogo.getStatistics().get(i).getAway());
                    statsHome = Float.parseFloat(jogo.getStatistics().get(i).getHome());
                    statsAway = Float.parseFloat(jogo.getStatistics().get(i).getAway());
                    pb5.setMax(statsHome + statsAway);pb6.setMax(statsHome + statsAway);
                    pb5.setProgress(statsHome);pb6.setProgress(statsAway);
                    break;

                case "Corner Kicks":
                    tv7.setText(jogo.getStatistics().get(i).getHome());
                    tv8.setText(jogo.getStatistics().get(i).getAway());
                    statsHome = Float.parseFloat(jogo.getStatistics().get(i).getHome());
                    statsAway = Float.parseFloat(jogo.getStatistics().get(i).getAway());
                    pb7.setMax(statsHome + statsAway);pb8.setMax(statsHome + statsAway);
                    pb7.setProgress(statsHome);pb8.setProgress(statsAway);
                    break;

                case "Goalkeeper Saves":
                    tv9.setText(jogo.getStatistics().get(i).getHome());
                    tv10.setText(jogo.getStatistics().get(i).getAway());
                    statsHome = Float.parseFloat(jogo.getStatistics().get(i).getHome());
                    statsAway = Float.parseFloat(jogo.getStatistics().get(i).getAway());
                    pb9.setMax(statsHome + statsAway);pb10.setMax(statsHome + statsAway);
                    pb9.setProgress(statsHome);pb10.setProgress(statsAway);
                    break;

                case "Dangerous Attacks":
                    tv11.setText(jogo.getStatistics().get(i).getHome());
                    tv12.setText(jogo.getStatistics().get(i).getAway());
                    statsHome = Float.parseFloat(jogo.getStatistics().get(i).getHome());
                    statsAway = Float.parseFloat(jogo.getStatistics().get(i).getAway());
                    pb11.setMax(statsHome + statsAway);pb12.setMax(statsHome + statsAway);
                    pb11.setProgress(statsHome);pb12.setProgress(statsAway);
                    break;
            }
        }
    }

    public void eventoRealizado() {
        if(jogo.getMatch_status().equals("")) {  //ainda nao realizado
            lEstatisticas.setVisibility(View.GONE);
        }else {
            lEstatisticas.setVisibility(View.VISIBLE);
            estatisticas();
        }
    }

}
