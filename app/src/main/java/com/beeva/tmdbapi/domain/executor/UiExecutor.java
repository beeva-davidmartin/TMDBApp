/*
 *  Copyright (c) 2016 BBVA. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  BBVA ("Confidential Information").  You shall not disclose such
 *  Confidential Information and shall use it only in accordance with
 *  the terms of the license agreement you entered into with BBVA.
 */

package com.beeva.tmdbapi.domain.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public class UiExecutor {

    private final Handler handler;

    public UiExecutor() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void execute(@NonNull Runnable runnable) {
        handler.post(runnable);
    }
}
