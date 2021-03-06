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
import com.beeva.tmdbapi.domain.model.TvSeason;

public class GetTvSeasonDetailInteractor extends BaseInteractor {

    private final TvShowRepository tvShowRepository;

    public GetTvSeasonDetailInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new TvShowRepository(context));
    }

    GetTvSeasonDetailInteractor(JobExecutor jobExecutor,
                                UiExecutor uiExecutor,
                                TvShowRepository tvShowRepository) {
        super(jobExecutor, uiExecutor);
        this.tvShowRepository = tvShowRepository;
    }

    public void execute(@NonNull final Integer seasonNumber,
                        @NonNull final Integer tvShowId,
                        @NonNull final Callback<TvSeason> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(seasonNumber, tvShowId, callback, errorCallback);
            }
        });
    }

    private void runBackground(Integer seasonNumber,
                               Integer tvShowId,
                               Callback<TvSeason> callback,
                               ErrorCallback errorCallback) {
        try {
            TvSeason tvSeason = tvShowRepository.getSeasonDetail(seasonNumber, tvShowId);
            postSuccess(tvSeason, callback);
        } catch (TMDbException exception) {
            postError(exception, errorCallback);
        }
    }

    private void postSuccess(final TvSeason tvSeason,
                             final Callback<TvSeason> callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(tvSeason);
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
