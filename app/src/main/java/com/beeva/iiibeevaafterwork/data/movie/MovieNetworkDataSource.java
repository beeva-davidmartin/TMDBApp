package com.beeva.iiibeevaafterwork.data.movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

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

    private static final String DETAIL_URL = "/movie/{movie_id}";

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
            return parseMovie(object);
        } catch (JSONException | ParseException e) {
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

    @NonNull
    private ArrayList<Movie> parseMovies(JSONArray results) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int index = 0; index < results.length(); index++) {
            try {
                JSONObject movieObject = results.getJSONObject(index);
                movies.add(parseMovie(movieObject));
            } catch (JSONException | ParseException exception) {
                Log.w(LOG_TAG, "Failed parsing movie #" + index, exception);
            }
        }
        return movies;
    }

    @NonNull
    private Movie parseMovie(JSONObject movieObject) throws JSONException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return new Movie(movieObject.getInt("id"),
                movieObject.getString("title"),
                movieObject.getString("overview"),
                movieObject.getString("poster_path"),
                movieObject.getDouble("vote_average"),
                movieObject.getBoolean("adult"),
                dateFormat.parse(movieObject.getString("release_date")),
                movieObject.getDouble("popularity"),
                movieObject.optString("homepage"),
                movieObject.optString("imdb_id"));
    }
}
