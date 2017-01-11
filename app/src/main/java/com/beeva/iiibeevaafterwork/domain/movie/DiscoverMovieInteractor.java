package com.beeva.iiibeevaafterwork.domain.movie;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.iiibeevaafterwork.TMDbApplication;
import com.beeva.iiibeevaafterwork.data.movie.MovieRepository;
import com.beeva.iiibeevaafterwork.domain.Callback;
import com.beeva.iiibeevaafterwork.domain.ErrorCallback;
import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.executor.JobExecutor;
import com.beeva.iiibeevaafterwork.domain.executor.UiExecutor;
import com.beeva.iiibeevaafterwork.domain.interactor.BaseInteractor;
import com.beeva.iiibeevaafterwork.domain.model.Movie;

public class DiscoverMovieInteractor extends BaseInteractor {

    private final MovieRepository movieRepository;

    public DiscoverMovieInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new MovieRepository(context));
    }

    DiscoverMovieInteractor(JobExecutor jobExecutor,
                            UiExecutor uiExecutor,
                            MovieRepository movieRepository) {
        super(jobExecutor, uiExecutor);
        this.movieRepository = movieRepository;
    }

    public void execute(@NonNull final Callback<List<Movie>> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(callback, errorCallback);
            }
        });
    }

    private void runBackground(Callback<List<Movie>> callback,
                               ErrorCallback errorCallback) {
        try {
            List<Movie> movies = movieRepository.discover();
            postSuccess(movies, callback);
        } catch (TMDbException exception) {
            postError(exception, errorCallback);
        }
    }

    private void postSuccess(final List<Movie> movies,
                             final Callback<List<Movie>> callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(movies);
            }
        });
    }

    private void postError(final TMDbException exception,
                           final ErrorCallback callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onError(exception);
            }
        });
    }
}
