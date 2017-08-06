package com.m2e.cs5540.autopresence.student_enrollment;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.List;

/**
 * Created by Ekta on 04-08-2017.
 */
public class EnrollmentAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {
   private static final String TAG = "EnrollAsyncTaskLoader";
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

   public EnrollmentAsyncTaskLoader(Context context) {
      super(context);
      onContentChanged();
      Log.i(TAG, "$$$$ EnrollmentAsyncTaskLoader created");
   }

   @Override protected void onStartLoading() {
      Log.i(TAG, "$$$$ EnrollmentAsyncTaskLoader onStartLoading");
      if (takeContentChanged()) {
         forceLoad();
      }
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         List<Course> courseList = databaseUtil.getAllCourses();
         loaderStatus.setResult(courseList);
      } catch (Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
      Log.i(TAG, "$$$$ EnrollmentAsyncTaskLoader deliverResult " + data);
   }
}
