/*
 * Copyright (c) 2015 BBVA. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * BBVA ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with BBVA.
 */

package com.beeva.tmdbapi.domain.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.support.annotation.NonNull;

public class JobExecutor {

    private static final int INITIAL_POOL_SIZE = 3;

    private static final int MAX_POOL_SIZE = 5;

    private static final int KEEP_ALIVE_TIME = 10;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final ThreadPoolExecutor threadPoolExecutor;

    public JobExecutor() {

        threadPoolExecutor = new ThreadPoolExecutor(
                INITIAL_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                new LinkedBlockingQueue<Runnable>());
    }

    public void execute(@NonNull Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }
        threadPoolExecutor.execute(runnable);
    }
}
