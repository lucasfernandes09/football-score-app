package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cartoes implements Parcelable {

    private String time;
    private String home_fault;
    private String card;
    private String away_fault;

    public Cartoes() {
    }

    protected Cartoes(Parcel in) {
        time = in.readString();
        home_fault = in.readString();
        card = in.readString();
        away_fault = in.readString();
    }

    public static final Creator<Cartoes> CREATOR = new Creator<Cartoes>() {
        @Override
        public Cartoes createFromParcel(Parcel in) {
            return new Cartoes(in);
        }

        @Override
        public Cartoes[] newArray(int size) {
            return new Cartoes[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHome_fault() {
        return home_fault;
    }

    public void setHome_fault(String home_fault) {
        this.home_fault = home_fault;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAway_fault() {
        return away_fault;
    }

    public void setAway_fault(String away_fault) {
        this.away_fault = away_fault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(home_fault);
        parcel.writeString(card);
        parcel.writeString(away_fault);
    }
}
