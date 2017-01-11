package com.beeva.iiibeevaafterwork.data.network;

import com.beeva.iiibeevaafterwork.domain.TMDbException;

public interface NetworkErrorCallback {

    void onError(TMDbException exception);
}
