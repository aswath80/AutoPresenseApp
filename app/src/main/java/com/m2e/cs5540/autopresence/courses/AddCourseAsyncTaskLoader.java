package com.m2e.cs5540.autopresence.courses;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.register.RegisterAsyncTaskLoader;
import com.m2e.cs5540.autopresence.vao.Course;

/**
 * Created by Kumar on 8/3/2017.
 */

public class AddCourseAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {

   private static final String TAG = RegisterAsyncTaskLoader.class.getName();
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

   private Course course;

   public AddCourseAsyncTaskLoader(Context context, Course course) {

      super(context);
      this.course = course;
      onContentChanged();
      Log.i(TAG, "$$$$ AddCourseAsyncTaskLoader created");
   }

   @Override protected void onStartLoading() {
      Log.i(TAG, "$$$$ AddCourseAsyncTaskLoader onStartLoading");
      if (takeContentChanged()) {
         forceLoad();
      }
   }

   @Override public AsyncLoaderStatus loadInBackground() {

      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      Log.i(TAG, "$$$$ AddCourseAsyncTaskLoader loadInBackground");

      try {
         databaseUtil.createCourse(course);
      } catch (Exception e) {
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
      Log.i(TAG, "$$$$ AddCourseAsyncTaskLoader deliverResult " + data);
   }
}
