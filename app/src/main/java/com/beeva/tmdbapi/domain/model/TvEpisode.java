package com.beeva.tmdbapi.domain.model;

import android.support.annotation.NonNull;

public class TvEpisode extends Media {

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
        super(id);
        this.name = name;
        this.description = description;
        this.rating = rating;
    }
}
