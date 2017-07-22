package com.m2e.cs5540.autopresence.service;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.location.AppLocationListener;
import com.m2e.cs5540.autopresence.vao.User;
import com.m2e.cs5540.autopresence.vao.UserCoordinate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maeswara on 7/15/2017.
 */
public class LocationUpdateService extends IntentService {
   private static final SimpleDateFormat sdf = new SimpleDateFormat
         ("dd-MMM-yyyy HH:mm:ss");
   private boolean run = true;
   private AppLocationListener locationListener = new AppLocationListener();
   private LocationManager locationManager;
   private String userId;

   public LocationUpdateService() {
      super("LocationUpdateService");
   }

   @Override public void onCreate() {
      super.onCreate();
      intiLocationManager();
      registerLocationListener();
   }

   private void intiLocationManager() {
      locationManager = (LocationManager) getSystemService(
            Context.LOCATION_SERVICE);
   }

   @Override protected void onHandleIntent(@Nullable Intent intent) {
      this.userId = intent.getStringExtra("userId");
      while (run) {
         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         Location location = locationListener.getLocation();
         if (location != null) {
            sendLocationUpdateToDatabase(location);
         }
      }
   }

   private void sendLocationUpdateToDatabase(Location location) {
      DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
      UserCoordinate userCoordinate = new UserCoordinate();
      userCoordinate.setUserId(userId);
      userCoordinate.setLastUpdateTime(sdf.format(new Date()));
      userCoordinate.setCurrentLatitude(location.getLatitude());
      userCoordinate.setCurrentLongitude(location.getLongitude());
      databaseUtil.updateUserCoordinate(userCoordinate);
   }

   private void registerLocationListener() {
      if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,
                  Manifest.permission.ACCESS_COARSE_LOCATION) ==
                  PackageManager.PERMISSION_GRANTED) {
         locationManager.requestLocationUpdates(
               LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
               0, locationListener);
      }
   }

   @Override public void onDestroy() {
      super.onDestroy();
      locationManager.removeUpdates(locationListener);
      run = false;
   }
}
