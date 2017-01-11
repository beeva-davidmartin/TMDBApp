package com.beeva.iiibeevaafterwork.domain.tvshow;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.iiibeevaafterwork.TMDbApplication;
import com.beeva.iiibeevaafterwork.data.tvshow.TvShowRepository;
import com.beeva.iiibeevaafterwork.domain.Callback;
import com.beeva.iiibeevaafterwork.domain.ErrorCallback;
import com.beeva.iiibeevaafterwork.domain.TMDbException;
import com.beeva.iiibeevaafterwork.domain.executor.JobExecutor;
import com.beeva.iiibeevaafterwork.domain.executor.UiExecutor;
import com.beeva.iiibeevaafterwork.domain.interactor.BaseInteractor;
import com.beeva.iiibeevaafterwork.domain.model.TvShow;

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
