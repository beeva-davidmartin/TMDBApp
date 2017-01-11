package com.beeva.iiibeevaafterwork.domain.model;

import android.support.annotation.NonNull;

public class Movie {

    @NonNull
    public final Integer id;

    @NonNull
    public final String title;

    @NonNull
    public final String description;

    @NonNull
    public final String poster;

    @NonNull
    public final Double rating;

    public Movie(@NonNull Integer id,
                 @NonNull String title,
                 @NonNull String description,
                 @NonNull String poster,
                 @NonNull Double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.rating = rating;
    }
}
