package com.beeva.tmdbapi.domain.model;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Movie {

    @NonNull
    public final Integer id;

    @NonNull
    public final String title;

    @NonNull
    public final String overview;

    @NonNull
    public final String posterPath;

    @NonNull
    public final Double voteAverage;

    @NonNull
    public final Boolean adult;

    @NonNull
    public final Double popularity;

    @Nullable
    public final Date releaseDate;

    @Nullable
    public String homepage;

    @Nullable
    public String imdbId;

    public Movie(@NonNull Integer id,
                 @NonNull String title,
                 @NonNull String overview,
                 @NonNull String posterPath,
                 @NonNull Double voteAverage,
                 @NonNull Boolean adult,
                 @NonNull Double popularity,
                 @Nullable Date releaseDate) {
        this(id, title, overview, posterPath, voteAverage, adult, popularity, releaseDate, null, null);
    }

    public Movie(@NonNull Integer id,
                 @NonNull String title,
                 @NonNull String overview,
                 @NonNull String posterPath,
                 @NonNull Double voteAverage,
                 @NonNull Boolean adult,
                 @NonNull Double popularity,
                 @Nullable Date releaseDate,
                 @Nullable String homepage,
                 @Nullable String imdbId) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.homepage = homepage;
        this.imdbId = imdbId;
    }
}
