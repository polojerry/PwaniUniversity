package com.polotechnologies.polo.pwaniuniversity.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class DownloadingTaskIntentService extends IntentService {


    public DownloadingTaskIntentService() {
        super("DownloadingTaskIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        DownloadingTask.executeAction(this, action);
    }
}
