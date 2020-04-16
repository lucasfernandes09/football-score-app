package com.app.app1.model.lineup;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Lineup implements Parcelable {

    private List<LineupJogador> starting_lineups;
    private List<LineupJogador> substitutes;
    private List<LineupJogador> coach;
    private List<LineupJogador> missing_players;

    public Lineup() {
    }

    protected Lineup(Parcel in) {
        starting_lineups = in.createTypedArrayList(LineupJogador.CREATOR);
        substitutes = in.createTypedArrayList(LineupJogador.CREATOR);
        coach = in.createTypedArrayList(LineupJogador.CREATOR);
        missing_players = in.createTypedArrayList(LineupJogador.CREATOR);
    }

    public static final Creator<Lineup> CREATOR = new Creator<Lineup>() {
        @Override
        public Lineup createFromParcel(Parcel in) {
            return new Lineup(in);
        }

        @Override
        public Lineup[] newArray(int size) {
            return new Lineup[size];
        }
    };

    public List<LineupJogador> getStarting_lineups() {
        return starting_lineups;
    }

    public void setStarting_lineups(List<LineupJogador> starting_lineups) {
        this.starting_lineups = starting_lineups;
    }

    public List<LineupJogador> getSubstitutes() {
        return substitutes;
    }

    public void setSubstitutes(List<LineupJogador> substitutes) {
        this.substitutes = substitutes;
    }

    public List<LineupJogador> getCoach() {
        return coach;
    }

    public void setCoach(List<LineupJogador> coach) {
        this.coach = coach;
    }

    public List<LineupJogador> getMissing_players() {
        return missing_players;
    }

    public void setMissing_players(List<LineupJogador> missing_players) {
        this.missing_players = missing_players;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(starting_lineups);
        parcel.writeTypedList(substitutes);
        parcel.writeTypedList(coach);
        parcel.writeTypedList(missing_players);
    }
}
