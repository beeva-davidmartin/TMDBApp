package com.beeva.iiibeevaafterwork.domain.model;

import java.util.Date;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TvShow {

    @NonNull
    public final Integer id;

    @NonNull
    public final String name;

    @NonNull
    public final String overview;

    @NonNull
    public final String posterPath;

    @NonNull
    public final Double voteAverage;

    @NonNull
    public final Double popularity;

    @Nullable
    public final Date firstAirDate;

    @Nullable
    public final List<TvSeason> seasons;

    public TvShow(@NonNull Integer id,
                  @NonNull String name,
                  @NonNull String overview,
                  @NonNull String posterPath,
                  @NonNull Double voteAverage,
                  @NonNull Double popularity,
                  @Nullable Date firstAirDate,
                  @Nullable List<TvSeason> seasons) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.firstAirDate = firstAirDate;
        this.seasons = seasons;
    }
}
