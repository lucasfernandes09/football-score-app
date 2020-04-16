package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Jogador implements Parcelable {

    String player_key;
    String player_name;
    String player_number;
    String player_country;
    String player_type;
    String player_age;
    String player_match_played;
    String player_goals;
    String player_yellow_cards;
    String player_red_cards;

    public Jogador() {
    }

    protected Jogador(Parcel in) {
        player_key = in.readString();
        player_name = in.readString();
        player_number = in.readString();
        player_country = in.readString();
        player_type = in.readString();
        player_age = in.readString();
        player_match_played = in.readString();
        player_goals = in.readString();
        player_yellow_cards = in.readString();
        player_red_cards = in.readString();
    }

    public static final Creator<Jogador> CREATOR = new Creator<Jogador>() {
        @Override
        public Jogador createFromParcel(Parcel in) {
            return new Jogador(in);
        }

        @Override
        public Jogador[] newArray(int size) {
            return new Jogador[size];
        }
    };

    public String getPlayer_key() {
        return player_key;
    }

    public void setPlayer_key(String player_key) {
        this.player_key = player_key;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_number() {
        return player_number;
    }

    public void setPlayer_number(String player_number) {
        this.player_number = player_number;
    }

    public String getPlayer_country() {
        return player_country;
    }

    public void setPlayer_country(String player_country) {
        this.player_country = player_country;
    }

    public String getPlayer_type() {
        return player_type;
    }

    public void setPlayer_type(String player_type) {
        this.player_type = player_type;
    }

    public String getPlayer_age() {
        return player_age;
    }

    public void setPlayer_age(String player_age) {
        this.player_age = player_age;
    }

    public String getPlayer_match_played() {
        return player_match_played;
    }

    public void setPlayer_match_played(String player_match_played) {
        this.player_match_played = player_match_played;
    }

    public String getPlayer_goals() {
        return player_goals;
    }

    public void setPlayer_goals(String player_goals) {
        this.player_goals = player_goals;
    }

    public String getPlayer_yellow_cards() {
        return player_yellow_cards;
    }

    public void setPlayer_yellow_cards(String player_yellow_cards) {
        this.player_yellow_cards = player_yellow_cards;
    }

    public String getPlayer_red_cards() {
        return player_red_cards;
    }

    public void setPlayer_red_cards(String player_red_cards) {
        this.player_red_cards = player_red_cards;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(player_key);
        parcel.writeString(player_name);
        parcel.writeString(player_number);
        parcel.writeString(player_country);
        parcel.writeString(player_type);
        parcel.writeString(player_age);
        parcel.writeString(player_match_played);
        parcel.writeString(player_goals);
        parcel.writeString(player_yellow_cards);
        parcel.writeString(player_red_cards);
    }
}
