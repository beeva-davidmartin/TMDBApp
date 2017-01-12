package com.beeva.iiibeevaafterwork.domain.model;

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
    public final Date releaseDate;

    @NonNull
    public final Double popularity;

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
                 @NonNull Date releaseDate,
                 @NonNull Double popularity) {
        this(id, title, overview, posterPath, voteAverage, adult, releaseDate, popularity, null, null);
    }

    public Movie(@NonNull Integer id,
                 @NonNull String title,
                 @NonNull String overview,
                 @NonNull String posterPath,
                 @NonNull Double voteAverage,
                 @NonNull Boolean adult,
                 @NonNull Date releaseDate,
                 @NonNull Double popularity,
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
