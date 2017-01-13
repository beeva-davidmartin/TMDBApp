package com.beeva.tmdbapi.data.session;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.beeva.tmdbapi.data.Constants;
import com.beeva.tmdbapi.data.network.NetworkHelper;
import com.beeva.tmdbapi.data.network.Operation;
import com.beeva.tmdbapi.domain.TMDbException;

public class SessionNetworkDataSource {

    private static final String GUEST_SESSION_URL = "/authentication/guest_session/new";

    private final NetworkHelper networkHelper;

    public SessionNetworkDataSource(@NonNull Context context) {
        this(NetworkHelper.get(context));
    }

    SessionNetworkDataSource(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @NonNull
    public String guestSession() throws TMDbException {
        String url = urlFromEndpoint(GUEST_SESSION_URL);

        Operation operation = Operation.newGet(url);
        JSONObject object;
        try {
            object = networkHelper.request(operation);
        } catch (Exception e) {
            throw new TMDbException(TMDbException.Type.NETWORK, e);
        }

        try {
            String guestSessionId = object.getString("guest_session_id");
            if (TextUtils.isEmpty(guestSessionId)) {
                throw new TMDbException(TMDbException.Type.API);
            }
            return guestSessionId;
        } catch (JSONException e) {
            throw new TMDbException(TMDbException.Type.API);
        }

    }

    @NonNull
    private String urlFromEndpoint(String endpoint) {
        return Constants.BASE_API_URL + endpoint;
    }
}
