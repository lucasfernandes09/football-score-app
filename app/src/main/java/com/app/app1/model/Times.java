package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Times implements Parcelable {

    private String team_key;
    private String team_name;
    private String team_badge;
    private List<Jogador> players;
    private List<Treinador> coaches;

    public Times() {
    }

    protected Times(Parcel in) {
        team_key = in.readString();
        team_name = in.readString();
        team_badge = in.readString();
        players = in.createTypedArrayList(Jogador.CREATOR);
        coaches = in.createTypedArrayList(Treinador.CREATOR);
    }

    public static final Creator<Times> CREATOR = new Creator<Times>() {
        @Override
        public Times createFromParcel(Parcel in) {
            return new Times(in);
        }

        @Override
        public Times[] newArray(int size) {
            return new Times[size];
        }
    };

    public String getTeam_key() {
        return team_key;
    }

    public void setTeam_key(String team_key) {
        this.team_key = team_key;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_badge() {
        return team_badge;
    }

    public void setTeam_badge(String team_badge) {
        this.team_badge = team_badge;
    }

    public List<Jogador> getPlayers() {
        return players;
    }

    public void setPlayers(List<Jogador> players) {
        this.players = players;
    }

    public List<Treinador> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Treinador> coaches) {
        this.coaches = coaches;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(team_key);
        parcel.writeString(team_name);
        parcel.writeString(team_badge);
        parcel.writeTypedList(players);
        parcel.writeTypedList(coaches);
    }
}
