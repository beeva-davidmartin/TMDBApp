package com.beeva.tmdbapi.domain.tvshow;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.TMDbApplication;
import com.beeva.tmdbapi.data.session.SessionRepository;
import com.beeva.tmdbapi.data.tvshow.TvShowRepository;
import com.beeva.tmdbapi.domain.Callback;
import com.beeva.tmdbapi.domain.ErrorCallback;
import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.executor.JobExecutor;
import com.beeva.tmdbapi.domain.executor.UiExecutor;
import com.beeva.tmdbapi.domain.interactor.BaseInteractor;

public class RateTvShowInteractor extends BaseInteractor {

    private final TvShowRepository tvShowRepository;

    private final SessionRepository sessionRepository;

    public RateTvShowInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new TvShowRepository(context),
                new SessionRepository(context));
    }

    RateTvShowInteractor(JobExecutor jobExecutor,
                         UiExecutor uiExecutor,
                         TvShowRepository tvShowRepository,
                         SessionRepository sessionRepository) {
        super(jobExecutor, uiExecutor);
        this.tvShowRepository = tvShowRepository;
        this.sessionRepository = sessionRepository;
    }

    public void execute(@NonNull final Integer id,
                        @NonNull final Double vote,
                        @NonNull final Callback<Boolean> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(id, vote, callback, errorCallback);
            }
        });
    }

    private void runBackground(Integer id,
                               Double vote,
                               Callback<Boolean> callback,
                               ErrorCallback errorCallback) {
        boolean validVote = (vote >= 0.5)
                && (vote <= 10)
                && (vote % 0.5 == 0);

        if (!validVote) {
            postError(new TMDbException(TMDbException.Type.API), errorCallback);
            return;
        }

        try {
            String guestSession = sessionRepository.guestSession();
            Boolean success = tvShowRepository.rate(id, vote, guestSession);
            postSuccess(success, callback);
        } catch (TMDbException exception) {
            postError(exception, errorCallback);
        }
    }

    private void postSuccess(final Boolean success,
                             final Callback<Boolean> callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(success);
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
