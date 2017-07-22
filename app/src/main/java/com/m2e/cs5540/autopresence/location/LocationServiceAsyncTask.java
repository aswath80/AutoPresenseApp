package com.m2e.cs5540.autopresence.location;

import android.os.AsyncTask;

import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.UserCoordinate;

import java.util.Date;

/**
 * Created by maeswara on 7/8/2017.
 */
public class LocationServiceAsyncTask extends AsyncTask<String, Void, String> {

   private static final String TAG = LocationServiceAsyncTask.class.getName();
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

   @Override protected String doInBackground(String... userIds) {
      sendLocationUpdateToDatabase(userIds[0]);
      return "done";
   }

   private void sendLocationUpdateToDatabase(String userId) {
      DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
      UserCoordinate userCoordinate = new UserCoordinate();
      userCoordinate.setUserId(userId);
      userCoordinate.setLastUpdateTime(new Date().toString());
      userCoordinate.setCurrentLatitude(123);
      userCoordinate.setCurrentLongitude(456);
      databaseUtil.updateUserCoordinate(userCoordinate);
   }
}
