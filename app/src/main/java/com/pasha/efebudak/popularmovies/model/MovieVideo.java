package com.pasha.efebudak.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by efebudak on 20/10/15.
 */
public class MovieVideo implements Parcelable {

    @SerializedName("id")
    String id;
    @SerializedName("iso_639_1")
    String iso_639_1;
    @SerializedName("key")
    String key;
    @SerializedName("name")
    String name;
    @SerializedName("site")
    String site;
    @SerializedName("size")
    int size;
    @SerializedName("type")
    String type;

    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso_639_1);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }

    public MovieVideo() {
    }

    protected MovieVideo(Parcel in) {
        this.id = in.readString();
        this.iso_639_1 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<MovieVideo> CREATOR = new Parcelable.Creator<MovieVideo>() {
        public MovieVideo createFromParcel(Parcel source) {
            return new MovieVideo(source);
        }

        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };
}
