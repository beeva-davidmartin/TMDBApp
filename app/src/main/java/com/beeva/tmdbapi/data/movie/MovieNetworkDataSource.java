package com.beeva.tmdbapi.data.movie;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.beeva.tmdbapi.data.Constants;
import com.beeva.tmdbapi.data.network.NetworkHelper;
import com.beeva.tmdbapi.data.network.Operation;
import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.model.Movie;

public class MovieNetworkDataSource {

    private static final String SEARCH_URL = "/search/movie";

    private static final String DISCOVER_URL = "/discover/movie";

    private static final String POPULAR_URL = "/movie/popular";

    private static final String DETAIL_URL = "/movie/{movie_id}";

    private static final String RATE_URL = "/movie/{movie_id}/rating";

    private final NetworkHelper networkHelper;

    public MovieNetworkDataSource(@NonNull Context context) {
        this(NetworkHelper.get(context));
    }

    MovieNetworkDataSource(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @NonNull
    public List<Movie> search(@NonNull final String query) throws TMDbException {
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

        return MovieMapper.parseMovies(results);
    }

    @NonNull
    public List<Movie> discover() throws TMDbException {
        String url = urlFromEndpoint(DISCOVER_URL);

        Operation operation = Operation.newGet(url);
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

        return MovieMapper.parseMovies(results);
    }

    @NonNull
    public List<Movie> getPopular() throws TMDbException {
        String url = urlFromEndpoint(POPULAR_URL);

        Operation operation = Operation.newGet(url);
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

        return MovieMapper.parseMovies(results);
    }

    @NonNull
    public Movie getDetail(@NonNull Integer id) throws TMDbException {
        String url = urlFromEndpoint(DETAIL_URL, Pair.create("{movie_id}", id.toString()));

        Operation operation = Operation.newGet(url);
        JSONObject object;
        try {
            object = networkHelper.request(operation);
        } catch (Exception e) {
            throw new TMDbException(TMDbException.Type.NETWORK, e);
        }

        try {
            return MovieMapper.parseMovie(object);
        } catch (JSONException | ParseException e) {
            throw new TMDbException(TMDbException.Type.API, e);
        }
    }

    @NonNull
    public Boolean rate(@NonNull Integer id, @NonNull Double vote, @NonNull final String guestSession) throws TMDbException {
        String url = urlFromEndpoint(RATE_URL, Pair.create("{movie_id}", id.toString()));
        Map<String, String> queryParams = new HashMap<String, String>() {{
            put("guest_session_id", Uri.encode(guestSession));
        }};

        Operation operation = Operation.newPost(url, queryParams, "{\"value\":" + vote + "}");
        JSONObject object;
        try {
            object = networkHelper.request(operation);
        } catch (Exception e) {
            throw new TMDbException(TMDbException.Type.NETWORK, e);
        }

        try {
            int statusCode = object.getInt("status_code");
            return statusCode == 1 || statusCode == 12;
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
