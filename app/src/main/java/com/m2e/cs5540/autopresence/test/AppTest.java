package com.m2e.cs5540.autopresence.test;

import android.util.Log;

import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.util.AppUtil;
import com.m2e.cs5540.autopresence.vao.User;

import java.util.UUID;

/**
 * Created by maeswara on 7/15/2017.
 */
public class AppTest {
   private static final String TAG = AppTest.class.getName();

   public void testCreateMani() {
      DatabaseUtil dbUtil = DatabaseUtil.getInstance();
      User user = new User();
      user.setId(UUID.randomUUID().toString());
      user.setLogin("mani");
      user.setName("Manikandan Eswaran");
      String encryptedPass = AppUtil.encryptPassword("password");
      Log.d(TAG, "$$$ encryptedPass = " + encryptedPass);
      user.setPassword(encryptedPass);
      dbUtil.createUser(user);
   }

   public void testCreateManish() {
      DatabaseUtil dbUtil = DatabaseUtil.getInstance();
      User user = new User();
      user.setId(UUID.randomUUID().toString());
      user.setLogin("manish");
      user.setName("Manish Kumar");
      String encryptedPass = AppUtil.encryptPassword("password");
      Log.d(TAG, "$$$ encryptedPass = " + encryptedPass);
      user.setPassword(encryptedPass);
      dbUtil.createUser(user);
   }

   public void testCreateEkta() {
      DatabaseUtil dbUtil = DatabaseUtil.getInstance();
      User user = new User();
      user.setId(UUID.randomUUID().toString());
      user.setLogin("ekta");
      user.setName("Ekta Kumari");
      String encryptedPass = AppUtil.encryptPassword("password");
      Log.d(TAG, "$$$ encryptedPass = " + encryptedPass);
      user.setPassword(encryptedPass);
      dbUtil.createUser(user);
   }
}
