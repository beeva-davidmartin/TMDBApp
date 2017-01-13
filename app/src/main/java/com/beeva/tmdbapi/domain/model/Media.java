package com.beeva.tmdbapi.domain.model;

import android.support.annotation.NonNull;

public class Media {

    @NonNull
    public final Integer id;

    public Media(@NonNull Integer id) {
        this.id = id;
    }
}
