package com.beeva.iiibeevaafterwork.domain.model;

import android.support.annotation.NonNull;

public class TvEpisode {

    @NonNull
    public final Integer id;

    @NonNull
    public final String title;

    @NonNull
    public final String description;

    @NonNull
    public final Double rating;

    public TvEpisode(@NonNull Integer id,
                     @NonNull String title,
                     @NonNull String description,
                     @NonNull Double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }
}
