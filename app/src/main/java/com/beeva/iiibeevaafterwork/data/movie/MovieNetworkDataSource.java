package com.beeva.iiibeevaafterwork.data.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.beeva.iiibeevaafterwork.data.Constants;
import com.beeva.iiibeevaafterwork.data.network.NetworkHelper;
import com.beeva.iiibeevaafterwork.data.network.Operation;
import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.model.Movie;

public class MovieNetworkDataSource {

    private static final String LOG_TAG = MovieNetworkDataSource.class.getSimpleName();

    private static final String SEARCH_URL = "/search/movie";

    private static final String DISCOVER_URL = "/discover/movie";

    private static final String POPULAR_URL = "/movie/popular";

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

        return parseMovies(results);
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

        return parseMovies(results);
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

        return parseMovies(results);
    }

    @NonNull
    private String urlFromEndpoint(String endpoint) {
        return Constants.BASE_API_URL + endpoint;
    }

    @NonNull
    private ArrayList<Movie> parseMovies(JSONArray results) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int index = 0; index < results.length(); index++) {
            try {
                JSONObject movieObject = results.getJSONObject(index);
                movies.add(parseMovie(movieObject));
            } catch (JSONException exception) {
                Log.w(LOG_TAG, "Failed parsing movie #" + index, exception);
            }
        }
        return movies;
    }

    @NonNull
    private Movie parseMovie(JSONObject movieObject) throws JSONException {
        return new Movie(movieObject.getInt("id"),
                movieObject.getString("title"),
                movieObject.getString("overview"),
                movieObject.optString("poster_path"),
                movieObject.getDouble("vote_average"));
    }
}
