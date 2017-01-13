package com.beeva.tmdbapi.domain.media;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.tmdbapi.TMDbApplication;
import com.beeva.tmdbapi.data.media.MediaRepository;
import com.beeva.tmdbapi.domain.Callback;
import com.beeva.tmdbapi.domain.ErrorCallback;
import com.beeva.tmdbapi.domain.TMDbException;
import com.beeva.tmdbapi.domain.executor.JobExecutor;
import com.beeva.tmdbapi.domain.executor.UiExecutor;
import com.beeva.tmdbapi.domain.interactor.BaseInteractor;
import com.beeva.tmdbapi.domain.model.Media;

public class MultiSearchInteractor extends BaseInteractor {

    private final MediaRepository mediaRepository;

    public MultiSearchInteractor(@NonNull Context context) {
        this(TMDbApplication.from(context).getJobExecutor(),
                TMDbApplication.from(context).getUiExecutor(),
                new MediaRepository(context));
    }

    MultiSearchInteractor(JobExecutor jobExecutor,
                          UiExecutor uiExecutor,
                          MediaRepository mediaRepository) {
        super(jobExecutor, uiExecutor);
        this.mediaRepository = mediaRepository;
    }

    public void execute(@NonNull final String query,
                        @NonNull final Callback<List<Media>> callback,
                        @NonNull final ErrorCallback errorCallback) {
        run(new Runnable() {
            @Override
            public void run() {
                runBackground(query, callback, errorCallback);
            }
        });
    }

    private void runBackground(String query,
                               Callback<List<Media>> callback,
                               ErrorCallback errorCallback) {
        try {
            List<Media> medias = mediaRepository.search(query);
            postSuccess(medias, callback);
        } catch (TMDbException exception) {
            postError(exception, errorCallback);
        }
    }

    private void postSuccess(final List<Media> medias,
                             final Callback<List<Media>> callback) {
        postUi(new Runnable() {
            @Override
            public void run() {
                callback.onCompleted(medias);
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
