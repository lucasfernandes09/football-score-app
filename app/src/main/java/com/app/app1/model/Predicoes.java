package com.app.app1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Predicoes implements Parcelable {

    private int prob_HW;
    private int prob_D;
    private int prob_AW;
    private int prob_O_3;
    private int prob_U_3;
    private int bts;
    private int ots;

    protected Predicoes(Parcel in) {
        prob_HW = in.readInt();
        prob_D = in.readInt();
        prob_AW = in.readInt();
        prob_O_3 = in.readInt();
        prob_U_3 = in.readInt();
        bts = in.readInt();
        ots = in.readInt();
    }

    public static final Creator<Predicoes> CREATOR = new Creator<Predicoes>() {
        @Override
        public Predicoes createFromParcel(Parcel in) {
            return new Predicoes(in);
        }

        @Override
        public Predicoes[] newArray(int size) {
            return new Predicoes[size];
        }
    };

    public int getProb_HW() {
        return prob_HW;
    }

    public void setProb_HW(int prob_HW) {
        this.prob_HW = prob_HW;
    }

    public int getProb_D() {
        return prob_D;
    }

    public void setProb_D(int prob_D) {
        this.prob_D = prob_D;
    }

    public int getProb_AW() {
        return prob_AW;
    }

    public void setProb_AW(int prob_AW) {
        this.prob_AW = prob_AW;
    }

    public int getProb_O_3() {
        return prob_O_3;
    }

    public void setProb_O_3(int prob_O_3) {
        this.prob_O_3 = prob_O_3;
    }

    public int getProb_U_3() {
        return prob_U_3;
    }

    public void setProb_U_3(int prob_U_3) {
        this.prob_U_3 = prob_U_3;
    }

    public int getBts() {
        return bts;
    }

    public void setBts(int bts) {
        this.bts = bts;
    }

    public int getOts() {
        return ots;
    }

    public void setOts(int ots) {
        this.ots = ots;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(prob_HW);
        parcel.writeInt(prob_D);
        parcel.writeInt(prob_AW);
        parcel.writeInt(prob_O_3);
        parcel.writeInt(prob_U_3);
        parcel.writeInt(bts);
        parcel.writeInt(ots);
    }
}

