package com.beeva.iiibeevaafterwork.data.tvshow;

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
import android.util.Pair;

import com.beeva.iiibeevaafterwork.data.Constants;
import com.beeva.iiibeevaafterwork.data.network.NetworkHelper;
import com.beeva.iiibeevaafterwork.data.network.Operation;
import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.model.TvShow;

public class TvShowNetworkDataSource {

    private static final String LOG_TAG = TvShowNetworkDataSource.class.getSimpleName();

    private static final String SEARCH_URL = "/search/tv";

    private static final String DISCOVER_URL = "/discover/tv";

    private static final String POPULAR_URL = "/tv/popular";

    private static final String DETAIL_URL = "/tv/{tv_id}";

    private final NetworkHelper networkHelper;

    public TvShowNetworkDataSource(@NonNull Context context) {
        this(NetworkHelper.get(context));
    }

    TvShowNetworkDataSource(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @NonNull
    public List<TvShow> search(@NonNull final String query) throws TMDbException {
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

        return parseTvShows(results);
    }

    @NonNull
    public List<TvShow> discover() throws TMDbException {
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

        return parseTvShows(results);
    }

    @NonNull
    public List<TvShow> getPopular() throws TMDbException {
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

        return parseTvShows(results);
    }

    @NonNull
    public TvShow getDetail(@NonNull Integer id) throws TMDbException {
        String url = urlFromEndpoint(DETAIL_URL, Pair.create("{tv_id}", id.toString()));

        Operation operation = Operation.newGet(url);
        JSONObject object;
        try {
            object = networkHelper.request(operation);
        } catch (Exception e) {
            throw new TMDbException(TMDbException.Type.NETWORK, e);
        }

        try {
            return parseTvShow(object);
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

    @NonNull
    private ArrayList<TvShow> parseTvShows(JSONArray results) {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        for (int index = 0; index < results.length(); index++) {
            try {
                JSONObject tvShowObject = results.getJSONObject(index);
                tvShows.add(parseTvShow(tvShowObject));
            } catch (JSONException exception) {
                Log.w(LOG_TAG, "Failed parsing TV show #" + index, exception);
            }
        }
        return tvShows;
    }

    @NonNull
    private TvShow parseTvShow(JSONObject tvShowObject) throws JSONException {
        JSONArray seasonsArray = tvShowObject.optJSONArray("seasons");
        List<TvShow.Season> seasons;
        if (seasonsArray != null) {
            seasons = parseTvShowSeasons(seasonsArray);
        } else {
            seasons = null;
        }

        return new TvShow(tvShowObject.getInt("id"),
                tvShowObject.getString("name"),
                tvShowObject.getString("overview"),
                tvShowObject.getString("poster_path"),
                tvShowObject.getDouble("vote_average"),
                seasons);
    }

    @NonNull
    private List<TvShow.Season> parseTvShowSeasons(JSONArray seasonsArray) throws JSONException {
        ArrayList<TvShow.Season> seasons = new ArrayList<>();
        for (int index = 0; index < seasonsArray.length(); index++) {
            try {
                JSONObject seasonObject = seasonsArray.getJSONObject(index);
                seasons.add(parseTvShowSeason(seasonObject));
            } catch (JSONException exception) {
                Log.w(LOG_TAG, "Failed parsing TV show #" + index, exception);
            }
        }
        return seasons;
    }

    private TvShow.Season parseTvShowSeason(JSONObject seasonObject) throws JSONException {
        return new TvShow.Season(seasonObject.getInt("id"),
                seasonObject.getInt("season_number"),
                seasonObject.getInt("episode_count"),
                seasonObject.getString("poster_path"));
    }
}
