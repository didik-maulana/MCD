package com.didik.mcd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String title;
    private String rate;
    private String date;
    private String overview;
    private String runtime;
    private String genres;
    private String director;
    private String actors;
    private int poster;

    public Movie() {
    }

    private Movie(Parcel in) {
        this.title = in.readString();
        this.rate = in.readString();
        this.date = in.readString();
        this.overview = in.readString();
        this.runtime = in.readString();
        this.genres = in.readString();
        this.director = in.readString();
        this.actors = in.readString();
        this.poster = in.readInt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.rate);
        dest.writeString(this.date);
        dest.writeString(this.overview);
        dest.writeString(this.runtime);
        dest.writeString(this.genres);
        dest.writeString(this.director);
        dest.writeString(this.actors);
        dest.writeInt(this.poster);
    }
}
