package com.beeva.tmdbapi.data.media;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.model.Media;

public class MediaRepository {

    private final MediaNetworkDataSource mediaNetworkDataSource;

    public MediaRepository(@NonNull Context context) {
        this(new MediaNetworkDataSource(context));
    }

    MediaRepository(MediaNetworkDataSource mediaNetworkDataSource) {
        this.mediaNetworkDataSource = mediaNetworkDataSource;
    }

    @NonNull
    public List<Media> search(@NonNull String query) throws TMDbException {
        return mediaNetworkDataSource.search(query);
    }
}
