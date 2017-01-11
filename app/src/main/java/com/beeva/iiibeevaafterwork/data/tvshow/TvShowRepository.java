package com.beeva.iiibeevaafterwork.data.tvshow;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.model.TvSeason;
import com.beeva.iiibeevaafterwork.domain.model.TvShow;

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
}
