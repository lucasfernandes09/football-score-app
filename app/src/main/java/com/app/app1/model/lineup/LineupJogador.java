package com.app.app1.model.lineup;

import android.os.Parcel;
import android.os.Parcelable;



public class LineupJogador implements Parcelable {

    private String lineup_player;
    private String lineup_number;
    private String lineup_position;

    public LineupJogador() {
    }

    protected LineupJogador(Parcel in) {
        lineup_player = in.readString();
        lineup_number = in.readString();
        lineup_position = in.readString();
    }

    public static final Creator<LineupJogador> CREATOR = new Creator<LineupJogador>() {
        @Override
        public LineupJogador createFromParcel(Parcel in) {
            return new LineupJogador(in);
        }

        @Override
        public LineupJogador[] newArray(int size) {
            return new LineupJogador[size];
        }
    };

    public String getLineup_player() {
        return lineup_player;
    }

    public void setLineup_player(String lineup_player) {
        this.lineup_player = lineup_player;
    }

    public String getLineup_number() {
        return lineup_number;
    }

    public void setLineup_number(String lineup_number) {
        this.lineup_number = lineup_number;
    }

    public String getLineup_position() {
        return lineup_position;
    }

    public void setLineup_position(String lineup_position) {
        this.lineup_position = lineup_position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lineup_player);
        parcel.writeString(lineup_number);
        parcel.writeString(lineup_position);
    }
}
