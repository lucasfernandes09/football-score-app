package com.app.app1.model.lineup;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Lineups implements Parcelable {

    private Lineup home;
    private Lineup away;

    public Lineups() {
    }

    protected Lineups(Parcel in) {
        home = in.readParcelable(Lineup.class.getClassLoader());
        away = in.readParcelable(Lineup.class.getClassLoader());
    }

    public static final Creator<Lineups> CREATOR = new Creator<Lineups>() {
        @Override
        public Lineups createFromParcel(Parcel in) {
            return new Lineups(in);
        }

        @Override
        public Lineups[] newArray(int size) {
            return new Lineups[size];
        }
    };

    public Lineup getHome() {
        return home;
    }

    public void setHome(Lineup home) {
        this.home = home;
    }

    public Lineup getAway() {
        return away;
    }

    public void setAway(Lineup away) {
        this.away = away;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(home, i);
        parcel.writeParcelable(away, i);
    }
}
