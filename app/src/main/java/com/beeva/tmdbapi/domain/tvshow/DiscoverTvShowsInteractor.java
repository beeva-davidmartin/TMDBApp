package com.beeva.tmdbapi.domain.tvshow;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.TMDbApplication;
import com.beeva.tmdbapi.data.tvshow.TvShowRepository;
import com.beeva.tmdbapi.domain.Callback;
import com.beeva.tmdbapi.domain.ErrorCallback;
import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.executor.JobExecutor;
import com.beeva.tmdbapi.domain.executor.UiExecutor;
import com.beeva.tmdbapi.domain.interactor.BaseInteractor;
import com.beeva.tmdbapi.domain.model.TvShow;

public class DiscoverTvShowsInteractor extends BaseInteractor {

    private final TvShowRepository tvShowRepository;

    public DiscoverTvShowsInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new TvShowRepository(context));
    }

    DiscoverTvShowsInteractor(JobExecutor jobExecutor,
                              UiExecutor uiExecutor,
                              TvShowRepository tvShowRepository) {
        super(jobExecutor, uiExecutor);
        this.tvShowRepository = tvShowRepository;
    }

    public void execute(@NonNull final Callback<List<TvShow>> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(callback, errorCallback);
            }
        });
    }

    private void runBackground(Callback<List<TvShow>> callback,
                               ErrorCallback errorCallback) {
        try {
            List<TvShow> tvShows = tvShowRepository.discover();
            postSuccess(tvShows, callback);
        } catch (TMDbException exception) {
            postError(exception, errorCallback);
        }
    }

    private void postSuccess(final List<TvShow> tvShows,
                             final Callback<List<TvShow>> callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(tvShows);
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
