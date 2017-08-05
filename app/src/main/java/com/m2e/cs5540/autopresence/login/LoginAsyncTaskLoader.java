package com.m2e.cs5540.autopresence.login;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.exception.AppException;
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
      Log.i(TAG, "$$$$ LoginAsyncTaskLoader created");
   }

   @Override protected void onStartLoading() {
      Log.i(TAG, "$$$$ LoginAsyncTaskLoader onStartLoading");
      //Log.i(TAG, "$$$$ LoginAsyncTaskLoader takeContentChanged() = " +
      //      takeContentChanged());
      if (takeContentChanged()) {
         forceLoad();
      }
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      Log.i(TAG, "$$$$ LoginAsyncTaskLoader loadInBackground");
      try {
         String userLogin = usernameEditText.getText().toString();
         if (userLogin != null && !userLogin.isEmpty()) {
            User user = databaseUtil.getUserByLogin(userLogin);
            if (user != null) {
               Log.i(TAG, "$$$$ Logged in user = " + user.getName());
               AppContext.initContext(user);
               loaderStatus.setResult(user);
            } else {
               loaderStatus.setException(new AppException(
                     "User " + userLogin + "" + " not found in the system!"));
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }

      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
      Log.i(TAG, "$$$$ LoginAsyncTaskLoader deliverResult " + data);
   }
}
