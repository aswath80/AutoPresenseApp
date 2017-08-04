package com.m2e.cs5540.autopresence.courses;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.register.RegisterAsyncTaskLoader;
import com.m2e.cs5540.autopresence.vao.Course;

/**
 * Created by Kumar on 8/3/2017.
 */

public class AddCourseAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {

    private static final String TAG = RegisterAsyncTaskLoader.class.getName();
    private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

    private String id;
    private String name;
    private String semester;
    private String time;
    private String location;

    public AddCourseAsyncTaskLoader(Context context, EditText cid,
                                    EditText cname, EditText clocation, EditText ctime, String semster) {
        super(context);
        this.id = cid.getText().toString();
        this.name = cname.getText().toString();
        this.semester = semster;
        this.time = ctime.getText().toString();
        this.location = clocation.getText().toString();

        onContentChanged();
        Log.d(TAG, "$$$$ AddCourseAsyncTaskLoader created");
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
        Log.d(TAG, "$$$$ AddCourseAsyncTaskLoader loadInBackground");

        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setLocation(location);
        course.getLocation();
        course.setSemester(semester);

        try {
            databaseUtil.createCourse(course);
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
