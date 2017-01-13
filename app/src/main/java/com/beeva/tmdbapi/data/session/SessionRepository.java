package com.beeva.tmdbapi.data.session;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.domain.TMDbException;

public class SessionRepository {

    private final SessionNetworkDataSource sessionNetworkDataSource;

    private final SessionMemoryDataSource sessionMemoryDataSource;

    public SessionRepository(@NonNull Context context) {
        this(new SessionNetworkDataSource(context), SessionMemoryDataSource.get());
    }

    SessionRepository(SessionNetworkDataSource sessionNetworkDataSource,
                      SessionMemoryDataSource sessionMemoryDataSource) {
        this.sessionNetworkDataSource = sessionNetworkDataSource;
        this.sessionMemoryDataSource = sessionMemoryDataSource;
    }

    @NonNull
    public String guestSession() throws TMDbException {
        try {
            return sessionMemoryDataSource.guestSession();
        } catch (TMDbException exception) {
            String guestSession = sessionNetworkDataSource.guestSession();
            sessionMemoryDataSource.setGuestSession(guestSession);
            return guestSession;
        }
    }
}
