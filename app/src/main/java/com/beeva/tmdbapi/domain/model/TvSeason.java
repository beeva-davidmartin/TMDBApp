package com.beeva.tmdbapi.domain.model;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TvSeason extends Media {

    @NonNull
    public final String poster;

    @NonNull
    public final Integer seasonNumber;

    @Nullable
    public final Integer episodeCount;

    @Nullable
    public final List<TvEpisode> episodes;

    @Nullable
    public final String name;

    @Nullable
    public final String description;

    public TvSeason(@NonNull Integer id,
                    @NonNull String poster,
                    @NonNull Integer seasonNumber,
                    @Nullable Integer episodeCount,
                    @Nullable List<TvEpisode> episodes,
                    @Nullable String name,
                    @Nullable String description) {
        super(id);
        this.poster = poster;
        this.seasonNumber = seasonNumber;
        this.episodeCount = (episodes != null) ? Integer.valueOf(episodes.size()) : episodeCount;
        this.name = name;
        this.description = description;
        this.episodes = episodes;
    }
}
