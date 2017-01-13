package com.beeva.tmdbapi.data.session;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.beeva.tmdbapi.domain.TMDbException;

public class SessionMemoryDataSource {

    private static SessionMemoryDataSource singleton;

    private String guestSession;

    public static synchronized SessionMemoryDataSource get() {
        if (singleton == null) {
            singleton = new SessionMemoryDataSource();
        }
        return singleton;
    }

    SessionMemoryDataSource() {
    }

    public void setGuestSession(@NonNull String guestSession) {
        this.guestSession = guestSession;
    }

    @NonNull
    public String guestSession() throws TMDbException {
        if (TextUtils.isEmpty(guestSession)) {
            throw new TMDbException(TMDbException.Type.INTERNAL);
        }
        return guestSession;
    }
}
