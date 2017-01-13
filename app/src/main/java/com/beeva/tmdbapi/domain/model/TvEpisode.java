package com.beeva.tmdbapi.domain.model;

import android.support.annotation.NonNull;

public class TvEpisode {

    @NonNull
    public final Integer id;

    @NonNull
    public final String name;

    @NonNull
    public final String description;

    @NonNull
    public final Double rating;

    public TvEpisode(@NonNull Integer id,
                     @NonNull String name,
                     @NonNull String description,
                     @NonNull Double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }
}
