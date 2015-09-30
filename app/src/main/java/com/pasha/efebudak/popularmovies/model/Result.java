package com.pasha.efebudak.popularmovies.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by efebudak on 28/09/15.
 */

public class Result implements Parcelable {

    @SerializedName("results")
    ArrayList<Movie> movieArrayList;

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(this.movieArrayList);
    }

    public Result() {
    }

    protected Result(android.os.Parcel in) {
        this.movieArrayList = new ArrayList<Movie>();
        in.readList(this.movieArrayList, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(android.os.Parcel source) {
            return new Result(source);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
