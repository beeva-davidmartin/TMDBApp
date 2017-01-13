/*
 * Copyright (c) 2015 BBVA. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * BBVA ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with BBVA.
 */

package com.beeva.tmdbapi.domain.interactor;

import android.support.annotation.NonNull;

import com.beeva.tmdbapi.domain.executor.JobExecutor;
import com.beeva.tmdbapi.domain.executor.UiExecutor;

/**
 * Base interactor that executes a task in background thread and post the result to a callback that will be executed in
 * the UI thread.
 */
public abstract class BaseInteractor {

    private final JobExecutor jobExecutor;

    private final UiExecutor uiExecutor;

    protected BaseInteractor(JobExecutor jobExecutor, UiExecutor uiExecutor) {
        this.jobExecutor = jobExecutor;
        this.uiExecutor = uiExecutor;
    }

    protected void run(@NonNull Runnable runnable) {
        jobExecutor.execute(runnable);
    }

    protected void postUi(@NonNull Runnable runnable) {
        uiExecutor.execute(runnable);
    }
}
