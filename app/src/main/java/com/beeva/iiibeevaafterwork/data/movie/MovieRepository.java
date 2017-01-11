package com.beeva.iiibeevaafterwork.data.movie;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.model.Movie;

public class MovieRepository {

    private final MovieNetworkDataSource movieNetworkDataSource;

    public MovieRepository(@NonNull Context context) {
        this(new MovieNetworkDataSource(context));
    }

    MovieRepository(MovieNetworkDataSource movieNetworkDataSource) {
        this.movieNetworkDataSource = movieNetworkDataSource;
    }

    @NonNull
    public List<Movie> search(@NonNull String query) throws TMDbException {
        return movieNetworkDataSource.search(query);
    }

    @NonNull
    public List<Movie> discover() throws TMDbException {
        return movieNetworkDataSource.discover();
    }

    @NonNull
    public List<Movie> getPopular() throws TMDbException {
        return movieNetworkDataSource.getPopular();
    }

    @NonNull
    public Movie getDetail(@NonNull Integer id) throws TMDbException {
        return movieNetworkDataSource.getDetail(id);
    }
}
