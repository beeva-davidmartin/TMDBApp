package com.beeva.iiibeevaafterwork;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.beeva.iiibeevaafterwork.domain.executor.JobExecutor;
import com.beeva.iiibeevaafterwork.domain.executor.UiExecutor;

public class TMDbApplication extends Application {

    private JobExecutor jobExecutor;

    private UiExecutor uiExecutor;

    public static TMDbApplication from(@NonNull Context context) {
        return (TMDbApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    @NonNull
    public JobExecutor getJobExecutor() {
        return jobExecutor;
    }

    @NonNull
    public UiExecutor getUiExecutor() {
        return uiExecutor;
    }

    private void init() {
        jobExecutor = new JobExecutor();
        uiExecutor = new UiExecutor();
    }
}
