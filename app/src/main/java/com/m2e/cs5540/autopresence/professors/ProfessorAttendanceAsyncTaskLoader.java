package com.m2e.cs5540.autopresence.professors;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

/**
 * Created by Kumar on 8/6/2017.
 */

public class ProfessorAttendanceAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {

    private static final String TAG = "ProfessorAttendanceAsyncTaskLoader";
    private String courseId;

    public ProfessorAttendanceAsyncTaskLoader(Context context, String courseId) {
        super(context);
        this.courseId = courseId;
    }

    @Override
    public AsyncLoaderStatus loadInBackground() {

        AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
        try {
            if (courseId != null ) {
                List<UserAttendance> userAttendanceList =  DatabaseUtil.getInstance().getCourseAttendances(courseId);
                loaderStatus.setResult(userAttendanceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            loaderStatus.setException(e);
        }
        return loaderStatus;
    }

    @Override
    public void deliverResult(AsyncLoaderStatus data) {

        super.deliverResult(data);
    }
}
