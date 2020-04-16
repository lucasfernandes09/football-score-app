package com.app.app1.model.substituicoes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Substituicao implements Parcelable {

    private String time;
    private String substitution;

    public Substituicao() {
    }

    protected Substituicao(Parcel in) {
        time = in.readString();
        substitution = in.readString();
    }

    public static final Creator<Substituicao> CREATOR = new Creator<Substituicao>() {
        @Override
        public Substituicao createFromParcel(Parcel in) {
            return new Substituicao(in);
        }

        @Override
        public Substituicao[] newArray(int size) {
            return new Substituicao[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubstitution() {
        return substitution;
    }

    public void setSubstitution(String substitution) {
        this.substitution = substitution;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(substitution);
    }
}
