package com.beeva.iiibeevaafterwork.domain.model;

import java.util.List;

import android.support.annotation.NonNull;

public class TvSeason {

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

    @NonNull
    public final Integer seasonNumber;

    @NonNull
    public final List<TvEpisode> episodes;

    public TvSeason(@NonNull Integer id,
                    @NonNull String name,
                    @NonNull String description,
                    @NonNull String poster,
                    @NonNull Double rating,
                    @NonNull Integer seasonNumber,
                    @NonNull List<TvEpisode> episodes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.poster = poster;
        this.rating = rating;
        this.seasonNumber = seasonNumber;
        this.episodes = episodes;
    }
}
