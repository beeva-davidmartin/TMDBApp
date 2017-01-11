package com.beeva.iiibeevaafterwork.domain;

public interface Callback<V> {

    void onCompleted(V value);
}
