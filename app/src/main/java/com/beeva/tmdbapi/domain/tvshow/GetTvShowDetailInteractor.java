package com.beeva.tmdbapi.domain.tvshow;

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

public class GetTvShowDetailInteractor extends BaseInteractor {

    private final TvShowRepository tvShowRepository;

    public GetTvShowDetailInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new TvShowRepository(context));
    }

    GetTvShowDetailInteractor(JobExecutor jobExecutor,
                              UiExecutor uiExecutor,
                              TvShowRepository tvShowRepository) {
        super(jobExecutor, uiExecutor);
        this.tvShowRepository = tvShowRepository;
    }

    public void execute(@NonNull final Integer id,
                        @NonNull final Callback<TvShow> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(id, callback, errorCallback);
            }
        });
    }

    private void runBackground(Integer id,
                               Callback<TvShow> callback,
                               ErrorCallback errorCallback) {
        try {
            TvShow tvShow = tvShowRepository.getDetail(id);
            postSuccess(tvShow, callback);
        } catch (TMDbException exception) {
            postError(exception, errorCallback);
        }
    }

    private void postSuccess(final TvShow tvShow,
                             final Callback<TvShow> callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(tvShow);
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
