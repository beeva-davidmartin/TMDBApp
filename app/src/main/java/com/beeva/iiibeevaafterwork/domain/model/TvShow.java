package com.beeva.iiibeevaafterwork.domain.model;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TvShow {

    @NonNull
    public final Integer id;

    @NonNull
    public final String name;

    @NonNull
    public final String description;

    @NonNull
    public final String poster;

    @NonNull
    public final Double rating;

    @Nullable
    public final List<Season> seasons;

    public TvShow(@NonNull Integer id,
                  @NonNull String name,
                  @NonNull String description,
                  @NonNull String poster,
                  @NonNull Double rating,
                  @Nullable List<Season> seasons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.poster = poster;
        this.rating = rating;
        this.seasons = seasons;
    }

    public static class Season {

        @NonNull
        public final Integer id;

        @NonNull
        public final Integer seasonNumber;

        @NonNull
        public final Integer numOfEpisodies;

        @NonNull
        public final String poster;

        public Season(@NonNull Integer id,
                      @NonNull Integer seasonNumber,
                      @NonNull Integer numOfEpisodies,
                      @NonNull String poster) {
            this.id = id;
            this.seasonNumber = seasonNumber;
            this.numOfEpisodies = numOfEpisodies;
            this.poster = poster;
        }
    }
}
