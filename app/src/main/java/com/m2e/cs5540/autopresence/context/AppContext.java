package com.m2e.cs5540.autopresence.context;

import android.content.Context;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.vao.User;

/**
 * Created by maeswara on 7/8/2017.
 */
public class AppContext {
   private static AppContext appContext = null;
   private User user;

   private AppContext(User user) {
      this.user = user;
   }

   public static void initContext(User user) {
      if(appContext == null) {
         appContext = new AppContext(user);
      }
   }

   public static AppContext getCurrentAppContext() {
      if(appContext == null) {
         throw new AppException("AppContext not initialized!");
      }
      return appContext;
   }

   public User getUser() {
      return user;
   }
}
