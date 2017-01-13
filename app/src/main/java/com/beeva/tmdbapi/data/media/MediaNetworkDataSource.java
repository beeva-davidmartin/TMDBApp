package com.beeva.tmdbapi.data.media;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.data.Constants;
import com.beeva.tmdbapi.data.network.NetworkHelper;
import com.beeva.tmdbapi.data.network.Operation;
import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.model.Media;

public class MediaNetworkDataSource {

    private static final String SEARCH_URL = "/search/multi";

    private final NetworkHelper networkHelper;

    public MediaNetworkDataSource(@NonNull Context context) {
        this(NetworkHelper.get(context));
    }

    MediaNetworkDataSource(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @NonNull
    public List<Media> search(@NonNull final String query) throws TMDbException {
        String url = urlFromEndpoint(SEARCH_URL);
        Map<String, String> queryParams = new HashMap<String, String>() {{
            put("query", Uri.encode(query));
        }};

        Operation operation = Operation.newGet(url, queryParams);
        JSONObject object;
        try {
            object = networkHelper.request(operation);
        } catch (Exception e) {
            throw new TMDbException(TMDbException.Type.NETWORK, e);
        }

        JSONArray results = object.optJSONArray("results");
        if (results == null) {
            throw new TMDbException(TMDbException.Type.API);
        }

        return MediaMapper.parseMedia(results);
    }

    @NonNull
    private String urlFromEndpoint(String endpoint) {
        return Constants.BASE_API_URL + endpoint;
    }
}
