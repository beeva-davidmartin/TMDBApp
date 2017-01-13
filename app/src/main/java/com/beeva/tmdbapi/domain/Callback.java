package com.beeva.tmdbapi.domain;

import android.support.annotation.NonNull;

public interface Callback<V> {

    void onCompleted(@NonNull V value);
}
