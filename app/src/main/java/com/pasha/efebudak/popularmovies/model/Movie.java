package com.pasha.efebudak.popularmovies.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by efebudak on 28/09/15.
 */

public class Movie implements Parcelable {

    @SerializedName("adult")
    boolean adult;
    @SerializedName("backdrop_path")
    String backdropPath;
    @SerializedName("id")
    int id;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("overview")
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("title")
    String title;
    @SerializedName("vote_average")
    float voteAverage;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdropPath);
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.title);
        dest.writeFloat(this.voteAverage);
    }

    public Movie() {
    }

    protected Movie(android.os.Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdropPath = in.readString();
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.voteAverage = in.readFloat();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(android.os.Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
