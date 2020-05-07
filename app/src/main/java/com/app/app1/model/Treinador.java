package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Treinador implements Parcelable {

    private String coach_name;
    private String coach_country;
    private String coach_age;

    protected Treinador(Parcel in) {
        coach_name = in.readString();
        coach_country = in.readString();
        coach_age = in.readString();
    }

    public static final Creator<Treinador> CREATOR = new Creator<Treinador>() {
        @Override
        public Treinador createFromParcel(Parcel in) {
            return new Treinador(in);
        }

        @Override
        public Treinador[] newArray(int size) {
            return new Treinador[size];
        }
    };

    public String getCoach_name() {
        return coach_name;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public String getCoach_country() {
        return coach_country;
    }

    public void setCoach_country(String coach_country) {
        this.coach_country = coach_country;
    }

    public String getCoach_age() {
        return coach_age;
    }

    public void setCoach_age(String coach_age) {
        this.coach_age = coach_age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(coach_name);
        parcel.writeString(coach_country);
        parcel.writeString(coach_age);
    }
}
