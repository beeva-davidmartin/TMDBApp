package com.beeva.iiibeevaafterwork.data.tvshow;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.beeva.iiibeevaafterwork.data.Constants;
import com.beeva.iiibeevaafterwork.data.network.NetworkHelper;
import com.beeva.iiibeevaafterwork.data.network.Operation;
import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.model.TvSeason;

public class TvSeasonNetworkDataSource {

    private static final String LOG_TAG = TvSeasonNetworkDataSource.class.getSimpleName();

    private static final String DETAIL_URL = "/tv/{tv_id}/season/{season_number}";

    private final NetworkHelper networkHelper;

    public TvSeasonNetworkDataSource(@NonNull Context context) {
        this(NetworkHelper.get(context));
    }

    TvSeasonNetworkDataSource(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @NonNull
    public TvSeason getDetail(@NonNull Integer seasonNumber, @NonNull Integer tvShowId) throws TMDbException {
        String url = urlFromEndpoint(DETAIL_URL,
                Pair.create("{tv_id}", tvShowId.toString()),
                Pair.create("{season_number}", seasonNumber.toString()));

        Operation operation = Operation.newGet(url);
        JSONObject object;
        try {
            object = networkHelper.request(operation);
        } catch (Exception e) {
            throw new TMDbException(TMDbException.Type.NETWORK, e);
        }

        try {
            return TvShowMapper.parseTvSeason(object);
        } catch (JSONException e) {
            throw new TMDbException(TMDbException.Type.API, e);
        }
    }

    @NonNull
    private String urlFromEndpoint(String endpoint) {
        return Constants.BASE_API_URL + endpoint;
    }

    @SafeVarargs
    @NonNull
    private final String urlFromEndpoint(String endpoint, Pair<String, String>... parameters) {
        String url = urlFromEndpoint(endpoint);
        for (Pair<String, String> parameter : parameters) {
            url = url.replace(parameter.first, parameter.second);
        }
        return url;
    }
}
