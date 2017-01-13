package com.beeva.tmdbapi.data.network;

import android.support.annotation.NonNull;

public interface NetworkCallback {

    void onCompleted(@NonNull String response);

}
