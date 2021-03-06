package com.beeva.tmdbapi.data.tvshow;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.model.TvSeason;
import com.beeva.tmdbapi.domain.model.TvShow;

public class TvShowRepository {

    private final TvShowNetworkDataSource tvShowNetworkDataSource;

    private TvSeasonNetworkDataSource tvSeasonNetworkDataSource;

    public TvShowRepository(@NonNull Context context) {
        this(new TvShowNetworkDataSource(context), new TvSeasonNetworkDataSource(context));
    }

    TvShowRepository(TvShowNetworkDataSource tvShowNetworkDataSource,
                     TvSeasonNetworkDataSource tvSeasonNetworkDataSource) {
        this.tvShowNetworkDataSource = tvShowNetworkDataSource;
        this.tvSeasonNetworkDataSource = tvSeasonNetworkDataSource;
    }

    @NonNull
    public List<TvShow> search(@NonNull String query) throws TMDbException {
        return tvShowNetworkDataSource.search(query);
    }

    @NonNull
    public List<TvShow> discover() throws TMDbException {
        return tvShowNetworkDataSource.discover();
    }

    @NonNull
    public List<TvShow> getPopular() throws TMDbException {
        return tvShowNetworkDataSource.getPopular();
    }

    @NonNull
    public TvShow getDetail(@NonNull Integer id) throws TMDbException {
        return tvShowNetworkDataSource.getDetail(id);
    }

    public TvSeason getSeasonDetail(Integer seasonNumber, Integer tvShowId) throws TMDbException {
        return tvSeasonNetworkDataSource.getDetail(seasonNumber, tvShowId);
    }

    @NonNull
    public Boolean rate(@NonNull Integer id, @NonNull Double vote, @NonNull String guestSession) throws TMDbException {
        return tvShowNetworkDataSource.rate(id, vote, guestSession);
    }
}
