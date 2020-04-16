package com.app.app1.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.app.app1.config.ConfiguracaoFirebase;
import com.app.app1.helper.Base64Custom;
import com.app.app1.helper.UsuarioFirebase;
import com.app.app1.model.lineup.Lineup;
import com.app.app1.model.lineup.Lineups;
import com.app.app1.model.substituicoes.Substituicoes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jogos implements Parcelable {

    private int match_id;
    private int league_id;
    private String league_name;
    private String match_status;
    private String match_time;
    private String match_hometeam_id;
    private String match_awayteam_id;
    private String match_hometeam_name;
    private String match_awayteam_name;
    private String match_hometeam_score;
    private String match_awayteam_score;
    private String match_hometeam_halftime_score;
    private String match_awayteam_halftime_score;
    private String match_hometeam_penalty_score;
    private String match_awayteam_penalty_score;
    private String match_hometeam_system;
    private String match_awayteam_system;
    private String match_live;  // 0=off 1=on
    private String match_round;
    private String match_stadium;
    private String match_referee;
    private String team_home_badge;
    private String team_away_badge;
    private String league_logo;
    private String country_logo;
    private List<Marcadores> goalscorer;
    private List<Cartoes> cards;
    private Substituicoes substitutions;
    private Lineups lineup;
    private List<Estatisticas> statistics;
    private int qtdCompeticao;
    private int auxiliar1;
    private String match_date;
    private Boolean selecionado = false;

    public Jogos(){}


    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Jogos(Parcel in) {
        match_id = in.readInt();
        league_id = in.readInt();
        league_name = in.readString();
        match_status = in.readString();
        match_time = in.readString();
        match_hometeam_id = in.readString();
        match_awayteam_id = in.readString();
        match_hometeam_name = in.readString();
        match_awayteam_name = in.readString();
        match_hometeam_score = in.readString();
        match_awayteam_score = in.readString();
        match_hometeam_halftime_score = in.readString();
        match_awayteam_halftime_score = in.readString();
        match_hometeam_penalty_score = in.readString();
        match_awayteam_penalty_score = in.readString();
        match_hometeam_system = in.readString();
        match_awayteam_system = in.readString();
        match_live = in.readString();
        match_round = in.readString();
        match_stadium = in.readString();
        match_referee = in.readString();
        team_home_badge = in.readString();
        team_away_badge = in.readString();
        league_logo = in.readString();
        country_logo = in.readString();
        goalscorer = in.createTypedArrayList(Marcadores.CREATOR);
        cards = in.createTypedArrayList(Cartoes.CREATOR);
        substitutions = in.readParcelable(Substituicoes.class.getClassLoader());
        lineup = in.readParcelable(Lineups.class.getClassLoader());
        statistics = in.createTypedArrayList(Estatisticas.CREATOR);
        qtdCompeticao = in.readInt();
        auxiliar1 = in.readInt();
        match_date = in.readString();
        selecionado = in.readBoolean();
    }

    public static final Creator<Jogos> CREATOR = new Creator<Jogos>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Jogos createFromParcel(Parcel in) {
            return new Jogos(in);
        }

        @Override
        public Jogos[] newArray(int size) {
            return new Jogos[size];
        }
    };

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_hometeam_id() {
        return match_hometeam_id;
    }

    public void setMatch_hometeam_id(String match_hometeam_id) {
        this.match_hometeam_id = match_hometeam_id;
    }

    public String getMatch_awayteam_id() {
        return match_awayteam_id;
    }

    public void setMatch_awayteam_id(String match_awayteam_id) {
        this.match_awayteam_id = match_awayteam_id;
    }

    public String getMatch_hometeam_name() {
        return match_hometeam_name;
    }

    public void setMatch_hometeam_name(String match_hometeam_name) {
        this.match_hometeam_name = match_hometeam_name;
    }

    public String getMatch_awayteam_name() {
        return match_awayteam_name;
    }

    public void setMatch_awayteam_name(String match_awayteam_name) {
        this.match_awayteam_name = match_awayteam_name;
    }

    public String getMatch_hometeam_score() {
        return match_hometeam_score;
    }

    public void setMatch_hometeam_score(String match_hometeam_score) {
        this.match_hometeam_score = match_hometeam_score;
    }

    public String getMatch_awayteam_score() {
        return match_awayteam_score;
    }

    public void setMatch_awayteam_score(String match_awayteam_score) {
        this.match_awayteam_score = match_awayteam_score;
    }

    public String getMatch_hometeam_halftime_score() {
        return match_hometeam_halftime_score;
    }

    public void setMatch_hometeam_halftime_score(String match_hometeam_halftime_score) {
        this.match_hometeam_halftime_score = match_hometeam_halftime_score;
    }

    public String getMatch_awayteam_halftime_score() {
        return match_awayteam_halftime_score;
    }

    public void setMatch_awayteam_halftime_score(String match_awayteam_halftime_score) {
        this.match_awayteam_halftime_score = match_awayteam_halftime_score;
    }

    public String getMatch_hometeam_penalty_score() {
        return match_hometeam_penalty_score;
    }

    public void setMatch_hometeam_penalty_score(String match_hometeam_penalty_score) {
        this.match_hometeam_penalty_score = match_hometeam_penalty_score;
    }

    public String getMatch_awayteam_penalty_score() {
        return match_awayteam_penalty_score;
    }

    public void setMatch_awayteam_penalty_score(String match_awayteam_penalty_score) {
        this.match_awayteam_penalty_score = match_awayteam_penalty_score;
    }

    public String getMatch_hometeam_system() {
        return match_hometeam_system;
    }

    public void setMatch_hometeam_system(String match_hometeam_system) {
        this.match_hometeam_system = match_hometeam_system;
    }

    public String getMatch_awayteam_system() {
        return match_awayteam_system;
    }

    public void setMatch_awayteam_system(String match_awayteam_system) {
        this.match_awayteam_system = match_awayteam_system;
    }

    public String getMatch_live() {
        return match_live;
    }

    public void setMatch_live(String match_live) {
        this.match_live = match_live;
    }

    public String getMatch_round() {
        return match_round;
    }

    public void setMatch_round(String match_round) {
        this.match_round = match_round;
    }

    public String getMatch_stadium() {
        return match_stadium;
    }

    public void setMatch_stadium(String match_stadium) {
        this.match_stadium = match_stadium;
    }

    public String getMatch_referee() {
        return match_referee;
    }

    public void setMatch_referee(String match_referee) {
        this.match_referee = match_referee;
    }

    public String getTeam_home_badge() {
        return team_home_badge;
    }

    public void setTeam_home_badge(String team_home_badge) {
        this.team_home_badge = team_home_badge;
    }

    public String getTeam_away_badge() {
        return team_away_badge;
    }

    public void setTeam_away_badge(String team_away_badge) {
        this.team_away_badge = team_away_badge;
    }

    public String getLeague_logo() {
        return league_logo;
    }

    public void setLeague_logo(String league_logo) {
        this.league_logo = league_logo;
    }

    public String getCountry_logo() {
        return country_logo;
    }

    public void setCountry_logo(String country_logo) {
        this.country_logo = country_logo;
    }

    public List<Marcadores> getGoalscorer() {
        return goalscorer;
    }

    public void setGoalscorer(List<Marcadores> goalscorer) {
        this.goalscorer = goalscorer;
    }

    public List<Cartoes> getCards() {
        return cards;
    }

    public void setCards(List<Cartoes> cards) {
        this.cards = cards;
    }

    public Substituicoes getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(Substituicoes substitutions) {
        this.substitutions = substitutions;
    }

    public Lineups getLineup() {
        return lineup;
    }

    public void setLineup(Lineups lineup) {
        this.lineup = lineup;
    }

    public List<Estatisticas> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Estatisticas> statistics) {
        this.statistics = statistics;
    }

    public int getQtdCompeticao() {
        return qtdCompeticao;
    }

    public void setQtdCompeticao(int qtdCompeticao) {
        this.qtdCompeticao = qtdCompeticao;
    }

    public int getAuxiliar1() {
        return auxiliar1;
    }

    public void setAuxiliar1(int auxiliar1) {
        this.auxiliar1 = auxiliar1;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(match_id);
        parcel.writeInt(league_id);
        parcel.writeString(league_name);
        parcel.writeString(match_status);
        parcel.writeString(match_time);
        parcel.writeString(match_hometeam_id);
        parcel.writeString(match_awayteam_id);
        parcel.writeString(match_hometeam_name);
        parcel.writeString(match_awayteam_name);
        parcel.writeString(match_hometeam_score);
        parcel.writeString(match_awayteam_score);
        parcel.writeString(match_hometeam_halftime_score);
        parcel.writeString(match_awayteam_halftime_score);
        parcel.writeString(match_hometeam_penalty_score);
        parcel.writeString(match_awayteam_penalty_score);
        parcel.writeString(match_hometeam_system);
        parcel.writeString(match_awayteam_system);
        parcel.writeString(match_live);
        parcel.writeString(match_round);
        parcel.writeString(match_stadium);
        parcel.writeString(match_referee);
        parcel.writeString(team_home_badge);
        parcel.writeString(team_away_badge);
        parcel.writeString(league_logo);
        parcel.writeString(country_logo);
        parcel.writeTypedList(goalscorer);
        parcel.writeTypedList(cards);
        parcel.writeParcelable(substitutions, i);
        parcel.writeParcelable(lineup, i);
        parcel.writeTypedList(statistics);
        parcel.writeInt(qtdCompeticao);
        parcel.writeInt(auxiliar1);
        parcel.writeString(match_date);
        parcel.writeBoolean(selecionado);
    }

    public void salvarJogo() {
        String idUsuario = UsuarioFirebase.getIdUsuario();
        DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebaseDatabase.child("usuarios").child(idUsuario).child("listaDeJogos")
                .child(String.valueOf(this.getMatch_id())).setValue(this);
    }

    public void removerJogoSalvo() {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebaseDatabase.child("usuarios").child(idUsuario).child("listaDeJogos")
                .child(String.valueOf(this.getMatch_id())).removeValue();
    }
}
