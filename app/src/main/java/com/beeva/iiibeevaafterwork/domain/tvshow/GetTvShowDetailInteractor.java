package com.beeva.iiibeevaafterwork.domain.tvshow;

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
