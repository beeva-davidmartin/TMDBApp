package com.beeva.tmdbapi.data.media;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.NonNull;
import android.util.Log;

import com.beeva.tmdbapi.data.movie.MovieMapper;
import com.beeva.tmdbapi.data.tvshow.TvShowMapper;
import com.beeva.tmdbapi.domain.model.Media;

public class MediaMapper {

    private static final String LOG_TAG = MediaMapper.class.getSimpleName();

    private static final String MEDIA_TYPE_MOVIE = "movie";

    private static final String MEDIA_TYPE_TV_SHOW = "tv";

    @NonNull
    public static List<Media> parseMedia(JSONArray results) {
        ArrayList<Media> medias = new ArrayList<>();
        for (int index = 0; index < results.length(); index++) {
            try {
                JSONObject mediaObject = results.getJSONObject(index);
                String mediaType = mediaObject.getString("media_type");
                if (MEDIA_TYPE_MOVIE.equals(mediaType)) {
                    medias.add(MovieMapper.parseMovie(mediaObject));
                } else if (MEDIA_TYPE_TV_SHOW.equals(mediaType)) {
                    medias.add(TvShowMapper.parseTvShow(mediaObject));
                }
            } catch (JSONException | ParseException exception) {
                Log.w(LOG_TAG, "Failed parsing movie #" + index, exception);
            }
        }
        return medias;
    }
}
