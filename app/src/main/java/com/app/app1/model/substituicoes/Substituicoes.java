package com.app.app1.model.substituicoes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Substituicoes implements Parcelable {

    private List<Substituicao> home;
    private List<Substituicao> away;

    public Substituicoes() {
    }

    protected Substituicoes(Parcel in) {
        home = in.createTypedArrayList(Substituicao.CREATOR);
        away = in.createTypedArrayList(Substituicao.CREATOR);
    }

    public static final Creator<Substituicoes> CREATOR = new Creator<Substituicoes>() {
        @Override
        public Substituicoes createFromParcel(Parcel in) {
            return new Substituicoes(in);
        }

        @Override
        public Substituicoes[] newArray(int size) {
            return new Substituicoes[size];
        }
    };

    public List<Substituicao> getHome() {
        return home;
    }

    public void setHome(List<Substituicao> home) {
        this.home = home;
    }

    public List<Substituicao> getAway() {
        return away;
    }

    public void setAway(List<Substituicao> away) {
        this.away = away;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(home);
        parcel.writeTypedList(away);
    }
}
