package com.m2e.cs5540.autopresence.students;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */

public class StudentActivityAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {
   private List<CourseEnrollment> courseEnrollmentList;

   public StudentActivityAsyncTaskLoader(Context context) {
      super(context);
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         List<CourseEnrollment> courseEnrollmentList =
               DatabaseUtil.getInstance().getCourseEnrollmentsByUserId(
                     AppContext.getCurrentAppContext().getUser().getId());
         loaderStatus.setResult(courseEnrollmentList);
      }catch(Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
   }
}
