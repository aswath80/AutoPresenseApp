package com.m2e.cs5540.autopresence.permit;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.courses.AddCourseActivity;

public class PermitActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_permit);
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus data) {

    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }

    @Override
    public void onClick(View v) {

    }
}