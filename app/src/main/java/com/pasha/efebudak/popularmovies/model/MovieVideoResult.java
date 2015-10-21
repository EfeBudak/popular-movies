package com.pasha.efebudak.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by efebudak on 20/10/15.
 */
public class MovieVideoResult implements Parcelable {

    @SerializedName("id")
    int id;
    @SerializedName("results")
    ArrayList<MovieVideo> movieVideoArrayList;

    public int getId() {
        return id;
    }

    public ArrayList<MovieVideo> getMovieVideoArrayList() {
        return movieVideoArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(movieVideoArrayList);
    }

    public MovieVideoResult() {
    }

    protected MovieVideoResult(Parcel in) {
        this.id = in.readInt();
        this.movieVideoArrayList = in.createTypedArrayList(MovieVideo.CREATOR);
    }

    public static final Parcelable.Creator<MovieVideoResult> CREATOR = new Parcelable.Creator<MovieVideoResult>() {
        public MovieVideoResult createFromParcel(Parcel source) {
            return new MovieVideoResult(source);
        }

        public MovieVideoResult[] newArray(int size) {
            return new MovieVideoResult[size];
        }
    };
}
