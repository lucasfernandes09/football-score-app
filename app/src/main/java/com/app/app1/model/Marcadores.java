package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Marcadores implements Parcelable {

    private String time;
    private String home_scorer;
    private String score;
    private String away_scorer;

    public Marcadores() {
    }

    protected Marcadores(Parcel in) {
        time = in.readString();
        home_scorer = in.readString();
        score = in.readString();
        away_scorer = in.readString();
    }

    public static final Creator<Marcadores> CREATOR = new Creator<Marcadores>() {
        @Override
        public Marcadores createFromParcel(Parcel in) {
            return new Marcadores(in);
        }

        @Override
        public Marcadores[] newArray(int size) {
            return new Marcadores[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHome_scorer() {
        return home_scorer;
    }

    public void setHome_scorer(String home_scorer) {
        this.home_scorer = home_scorer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAway_scorer() {
        return away_scorer;
    }

    public void setAway_scorer(String away_scorer) {
        this.away_scorer = away_scorer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(home_scorer);
        parcel.writeString(score);
        parcel.writeString(away_scorer);
    }
}
