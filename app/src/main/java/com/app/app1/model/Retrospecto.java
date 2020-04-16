package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Retrospecto implements Parcelable {

    private List<Jogos> firstTeam_VS_secondTeam;
    private List<Jogos> firstTeam_lastResults;
    private List<Jogos> secondTeam_lastResults;

    public Retrospecto() {
    }

    protected Retrospecto(Parcel in) {
        firstTeam_VS_secondTeam = in.createTypedArrayList(Jogos.CREATOR);
        firstTeam_lastResults = in.createTypedArrayList(Jogos.CREATOR);
        secondTeam_lastResults = in.createTypedArrayList(Jogos.CREATOR);
    }

    public static final Creator<Retrospecto> CREATOR = new Creator<Retrospecto>() {
        @Override
        public Retrospecto createFromParcel(Parcel in) {
            return new Retrospecto(in);
        }

        @Override
        public Retrospecto[] newArray(int size) {
            return new Retrospecto[size];
        }
    };

    public List<Jogos> getFirstTeam_VS_secondTeam() {
        return firstTeam_VS_secondTeam;
    }

    public void setFirstTeam_VS_secondTeam(List<Jogos> firstTeam_VS_secondTeam) {
        this.firstTeam_VS_secondTeam = firstTeam_VS_secondTeam;
    }

    public List<Jogos> getFirstTeam_lastResults() {
        return firstTeam_lastResults;
    }

    public void setFirstTeam_lastResults(List<Jogos> firstTeam_lastResults) {
        this.firstTeam_lastResults = firstTeam_lastResults;
    }

    public List<Jogos> getSecondTeam_lastResults() {
        return secondTeam_lastResults;
    }

    public void setSecondTeam_lastResults(List<Jogos> secondTeam_lastResults) {
        this.secondTeam_lastResults = secondTeam_lastResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(firstTeam_VS_secondTeam);
        parcel.writeTypedList(firstTeam_lastResults);
        parcel.writeTypedList(secondTeam_lastResults);
    }
}
