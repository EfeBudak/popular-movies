package com.pasha.efebudak.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by efebudak on 01/11/15.
 */
public class MovieReviewResult implements Parcelable {

    @SerializedName("id")
    int id;
    @SerializedName("results")
    ArrayList<MovieReview> movieReviewArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MovieReview> getMovieReviewArrayList() {
        return movieReviewArrayList;
    }

    public void setMovieReviewArrayList(ArrayList<MovieReview> movieReviewArrayList) {
        this.movieReviewArrayList = movieReviewArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(movieReviewArrayList);
    }

    public MovieReviewResult() {
    }

    protected MovieReviewResult(Parcel in) {
        this.id = in.readInt();
        this.movieReviewArrayList = in.createTypedArrayList(MovieReview.CREATOR);
    }

    public static final Parcelable.Creator<MovieReviewResult> CREATOR = new Parcelable.Creator<MovieReviewResult>() {
        public MovieReviewResult createFromParcel(Parcel source) {
            return new MovieReviewResult(source);
        }

        public MovieReviewResult[] newArray(int size) {
            return new MovieReviewResult[size];
        }
    };
}
