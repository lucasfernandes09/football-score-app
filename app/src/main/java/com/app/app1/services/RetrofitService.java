package com.app.app1.services;


import com.app.app1.model.Jogos;
import com.app.app1.model.Predicoes;
import com.app.app1.model.Retrospecto;
import com.app.app1.model.Tabela;
import com.app.app1.model.Times;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    String API_KEY = "4c160f9aaea5fe6bd75cfb998529917f77657f9b9853e42372bda344b6171ced";

    @GET("?action=get_events&APIkey=" + API_KEY)
    Call<List<Jogos>> listarJogos(@Query("from") String from,
                                  @Query("to") String to);

    @GET("?action=get_teams&APIkey=" + API_KEY)
    //sem estar entre barras, usei o @query. O trecho "league_id={id}&" foi retirado da url
    Call<List<Times>> listarTimes(@Query("league_id") int league_id);

    @GET("?action=get_teams&APIkey=" + API_KEY)
    Call<List<Times>> listarElenco(@Query("team_id") String team_id);

    @GET("?action=get_H2H&APIkey=" + API_KEY)
    Call<Retrospecto> listarRetrospectos(@Query("firstTeam") String firstTeam,
                                         @Query("secondTeam") String secondTeam);

    @GET("?action=get_standings&APIkey=" + API_KEY)
    Call<List<Tabela>> listarTabela(@Query("league_id") int league_id);

    @GET("?action=get_events&APIkey=" + API_KEY)
    Call<List<Jogos>> listarJogosDaEquipe(@Query("from") String from,
                                          @Query("to") String to,
                                          @Query("team_id") String team_id);

    @GET("?action=get_events&APIkey=" + API_KEY)
    Call<List<Jogos>> listarJogosDaCompeticao(@Query("from") String from,
                                          @Query("to") String to,
                                          @Query("league_id") int league_id);

    @GET("?action=get_predictions&APIkey=" + API_KEY)
    Call<List<Predicoes>> listarPredicoes(@Query("match_id") int match_id);



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apiv2.apifootball.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}
