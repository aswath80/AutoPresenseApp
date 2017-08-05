package com.m2e.cs5540.autopresence.permit;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Permit;

/**
 * Created by Kumar on 8/5/2017.
 */

public class PermitAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {

    private static final String TAG = PermitAsyncTaskLoader.class.getName();
    private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
    private Permit permit;

    public PermitAsyncTaskLoader(Context context, Permit permit){
        super(context);
        this.permit = permit;
        onContentChanged();
        Log.d(TAG, "$$$$ PermitAsyncTaskLoader created");
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "$$$$ AddCourseAsyncTaskLoader onStartLoading");
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public AsyncLoaderStatus loadInBackground() {

        AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
        Log.d(TAG, "$$$$ PermitAsyncTaskLoader loadInBackground");

        try {
            databaseUtil.createPermit(permit);
        } catch (Exception e) {
            loaderStatus.setException(e);
        }
        return loaderStatus;
    }

    @Override
    public void deliverResult(AsyncLoaderStatus data) {
        super.deliverResult(data);
        Log.d(TAG, "$$$$ AddCourseAsyncTaskLoader deliverResult " + data);
    }
}
