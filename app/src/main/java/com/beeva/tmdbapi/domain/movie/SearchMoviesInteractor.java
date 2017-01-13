package com.beeva.tmdbapi.domain.movie;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.TMDbApplication;
import com.beeva.tmdbapi.data.movie.MovieRepository;
import com.beeva.tmdbapi.domain.Callback;
import com.beeva.tmdbapi.domain.ErrorCallback;
import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.executor.JobExecutor;
import com.beeva.tmdbapi.domain.executor.UiExecutor;
import com.beeva.tmdbapi.domain.interactor.BaseInteractor;
import com.beeva.tmdbapi.domain.model.Movie;

public class SearchMoviesInteractor extends BaseInteractor {

    private final MovieRepository movieRepository;

    public SearchMoviesInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new MovieRepository(context));
    }

    SearchMoviesInteractor(JobExecutor jobExecutor,
                           UiExecutor uiExecutor,
                           MovieRepository movieRepository) {
        super(jobExecutor, uiExecutor);
        this.movieRepository = movieRepository;
    }

    public void execute(@NonNull final String query,
                        @NonNull final Callback<List<Movie>> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(query, callback, errorCallback);
            }
        });
    }

    private void runBackground(String query,
                               Callback<List<Movie>> callback,
                               ErrorCallback errorCallback) {
        try {
            List<Movie> movies = movieRepository.search(query);
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
