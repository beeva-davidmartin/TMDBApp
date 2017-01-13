package com.beeva.tmdbapi.domain;

import android.support.annotation.NonNull;

public interface ErrorCallback {

    void onError(@NonNull TMDbException exception);
}
