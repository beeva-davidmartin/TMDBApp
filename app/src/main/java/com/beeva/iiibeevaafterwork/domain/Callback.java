package com.beeva.iiibeevaafterwork.domain;

import android.support.annotation.NonNull;

public interface Callback<V> {

    void onCompleted(@NonNull V value);
}
