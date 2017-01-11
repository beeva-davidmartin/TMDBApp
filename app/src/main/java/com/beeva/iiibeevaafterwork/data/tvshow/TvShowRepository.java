package com.beeva.iiibeevaafterwork.data.tvshow;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.model.TvShow;

public class TvShowRepository {

    private final TvShowNetworkDataSource tvShowNetworkDataSource;

    public TvShowRepository(@NonNull Context context) {
        this(new TvShowNetworkDataSource(context));
    }

    TvShowRepository(TvShowNetworkDataSource tvShowNetworkDataSource) {
        this.tvShowNetworkDataSource = tvShowNetworkDataSource;
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
}
