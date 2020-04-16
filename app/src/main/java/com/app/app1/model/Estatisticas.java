package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Estatisticas implements Parcelable {

    String type;
    String home;
    String away;

    public Estatisticas() {
    }

    protected Estatisticas(Parcel in) {
        type = in.readString();
        home = in.readString();
        away = in.readString();
    }

    public static final Creator<Estatisticas> CREATOR = new Creator<Estatisticas>() {
        @Override
        public Estatisticas createFromParcel(Parcel in) {
            return new Estatisticas(in);
        }

        @Override
        public Estatisticas[] newArray(int size) {
            return new Estatisticas[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(home);
        parcel.writeString(away);
    }
}
