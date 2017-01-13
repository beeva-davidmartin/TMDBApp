package com.beeva.tmdbapi.data.network;

import com.beeva.tmdbapi.domain.TMDbException;

public interface NetworkErrorCallback {

    void onError(TMDbException exception);
}
