package com.m2e.cs5540.autopresence.login;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.User;

/**
 * Created by maeswara on 7/8/2017.
 */
public class LoginAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {

   private static final String TAG = LoginAsyncTaskLoader.class.getName();
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
   private EditText usernameEditText;

   public LoginAsyncTaskLoader(Context context, EditText usernameEditText) {
      super(context);
      this.usernameEditText = usernameEditText;
      onContentChanged();
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader created");
   }

   @Override protected void onStartLoading() {
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader onStartLoading");
      //Log.d(TAG, "$$$$ LoginAsyncTaskLoader takeContentChanged() = " +
      //      takeContentChanged());
      if (takeContentChanged()) {
         forceLoad();
      }
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader loadInBackground");
      try {
         User user = databaseUtil.getUser(
               usernameEditText.getText().toString());
         Log.d(TAG, "$$$$ Logged in user = " + user.getName());
         AppContext.initContext(user);
         loaderStatus.setResult(user);
      } catch (Exception e) {
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
      Log.d(TAG, "$$$$ LoginAsyncTaskLoader deliverResult " + data);
   }
}
