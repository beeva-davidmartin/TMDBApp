package com.beeva.iiibeevaafterwork.data.tvshow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.beeva.iiibeevaafterwork.domain.model.TvEpisode;
import com.beeva.iiibeevaafterwork.domain.model.TvSeason;
import com.beeva.iiibeevaafterwork.domain.model.TvShow;

public class TvShowMapper {

    private static final String LOG_TAG = TvShowMapper.class.getSimpleName();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @NonNull
    public static ArrayList<TvShow> parseTvShows(JSONArray results) {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        for (int index = 0; index < results.length(); index++) {
            try {
                JSONObject tvShowObject = results.getJSONObject(index);
                tvShows.add(parseTvShow(tvShowObject));
            } catch (JSONException | ParseException exception) {
                Log.w(LOG_TAG, "Failed parsing TV show #" + index, exception);
            }
        }
        return tvShows;
    }

    @NonNull
    public static TvShow parseTvShow(JSONObject tvShowObject) throws JSONException, ParseException {
        JSONArray seasonsArray = tvShowObject.optJSONArray("seasons");
        List<TvSeason> seasons;
        if (seasonsArray != null) {
            seasons = parseTvSeasons(seasonsArray);
        } else {
            seasons = null;
        }

        String firstAirDate = tvShowObject.optString("firstAirDate");
        return new TvShow(tvShowObject.getInt("id"),
                tvShowObject.getString("name"),
                tvShowObject.getString("overview"),
                tvShowObject.getString("poster_path"),
                tvShowObject.getDouble("vote_average"),
                tvShowObject.getDouble("popularity"),
                (!TextUtils.isEmpty(firstAirDate) ? DATE_FORMAT.parse(firstAirDate) : null),
                seasons);
    }

    @NonNull
    public static List<TvSeason> parseTvSeasons(JSONArray seasonsArray) {
        ArrayList<TvSeason> tvSeasons = new ArrayList<>();
        for (int index = 0; index < seasonsArray.length(); index++) {
            try {
                JSONObject tvSeasonObject = seasonsArray.getJSONObject(index);
                tvSeasons.add(parseTvSeason(tvSeasonObject));
            } catch (JSONException exception) {
                Log.w(LOG_TAG, "Failed parsing TV season #" + index, exception);
            }
        }
        return tvSeasons;
    }

    @NonNull
    public static TvSeason parseTvSeason(JSONObject tvSeasonObject) throws JSONException {
        JSONArray episodesArray = tvSeasonObject.optJSONArray("episodes");
        List<TvEpisode> episodes;
        if (episodesArray != null) {
            episodes = parseTvEpisodes(episodesArray);
        } else {
            episodes = null;
        }

        return new TvSeason(tvSeasonObject.getInt("id"),
                tvSeasonObject.getString("poster_path"),
                tvSeasonObject.getInt("season_number"),
                tvSeasonObject.optInt("episode_count"),
                episodes,
                tvSeasonObject.optString("name"),
                tvSeasonObject.optString("overview"));
    }

    @NonNull
    public static List<TvEpisode> parseTvEpisodes(JSONArray episodesArray) throws JSONException {
        ArrayList<TvEpisode> episodes = new ArrayList<>();
        for (int index = 0; index < episodesArray.length(); index++) {
            try {
                JSONObject episodeObject = episodesArray.getJSONObject(index);
                episodes.add(parseTvEpisode(episodeObject));
            } catch (JSONException exception) {
                Log.w(LOG_TAG, "Failed parsing TV episode #" + index, exception);
            }
        }
        return episodes;
    }

    @NonNull
    public static TvEpisode parseTvEpisode(JSONObject episodeObject) throws JSONException {
        return new TvEpisode(episodeObject.getInt("id"),
                episodeObject.getString("name"),
                episodeObject.getString("overview"),
                episodeObject.getDouble("vote_average"));
    }
}
