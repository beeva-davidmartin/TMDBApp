package com.beeva.tmdbapi.data.movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.beeva.tmdbapi.domain.model.Movie;

public class MovieMapper {

    private static final String LOG_TAG = MovieMapper.class.getSimpleName();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @NonNull
    public static ArrayList<Movie> parseMovies(JSONArray results) {
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
    public static Movie parseMovie(JSONObject movieObject) throws JSONException, ParseException {
        String releaseDate = movieObject.optString("release_date");
        return new Movie(movieObject.getInt("id"),
                movieObject.getString("title"),
                movieObject.getString("overview"),
                movieObject.getString("poster_path"),
                movieObject.getDouble("vote_average"),
                movieObject.getBoolean("adult"),
                movieObject.getDouble("popularity"),
                (!TextUtils.isEmpty(releaseDate) ? DATE_FORMAT.parse(releaseDate) : null),
                movieObject.optString("homepage"),
                movieObject.optString("imdb_id"));
    }
}
