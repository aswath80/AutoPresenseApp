package com.m2e.cs5540.autopresence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.login.LoginActivity;
import com.m2e.cs5540.autopresence.professors.ProfessorActivity;
import com.m2e.cs5540.autopresence.students.StudentsActivity;
import com.m2e.cs5540.autopresence.vao.User;
import com.m2e.cs5540.autopresence.vao.UserRole;

public class MainActivity extends AppCompatActivity {

   public static final String SHARED_PREFS_KEY = "com.m2e.cs5540.autopresence";

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      initHomeScreen();
   }

   @Override protected void onResume() {
      super.onResume();
      restoreUserSession();
      initHomeScreen();
   }

   @Override protected void onPause() {
      super.onPause();
      saveUserSession();
   }

   @Override protected void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      saveUserSession();
   }

   private void saveUserSession() {
      try {
         if (AppContext.isUserLoggedIn()) {
            AppContext appContext = AppContext.getCurrentAppContext();
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_KEY,
                  MODE_PRIVATE);
            prefs.edit().putString("userId", appContext.getUser().getId())
                  .commit();
            Toast.makeText(this, "User session saved!", Toast.LENGTH_LONG)
                  .show();
         }
      } catch (AppException ae) {
         //App not initialized
      }
   }

   private static class UserSessionRestoreAsyncTask
         extends AsyncTask<String, Void, User> {
      @Override protected User doInBackground(String... params) {
         return DatabaseUtil.getInstance().getUserById(params[0]);
      }

      @Override protected void onPostExecute(User user) {
         super.onPostExecute(user);
         AppContext.initContext(user);
      }
   }

   @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);
      restoreUserSession();
   }

   private void restoreUserSession() {
      try {
         if (!AppContext.isUserLoggedIn()) {
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_KEY,
                  MODE_PRIVATE);
            String userId = prefs.getString("userId", null);
            if (userId != null) {
               new UserSessionRestoreAsyncTask().execute(new String[]{userId});
               Toast.makeText(MainActivity.this, "User session restored!",
                     Toast.LENGTH_LONG).show();
            }
         }
      } catch (Exception e) {
         Toast.makeText(MainActivity.this,
               "User session restore failed! " + "Cause: " + e.getMessage(),
               Toast.LENGTH_LONG).show();
      }
   }

   private void initHomeScreen() {
      if (AppContext.isUserLoggedIn()) {
         User user = AppContext.getCurrentAppContext().getUser();
         if (user.getRole() == UserRole.STUDENT) {
            Intent studentLandingPageIntent = new Intent(this,
                  StudentsActivity.class);
            startActivity(studentLandingPageIntent);
         } else if (user.getRole() == UserRole.PROFESSOR) {
            Intent professorLandingPageIntent = new Intent(this,
                  ProfessorActivity.class);
            startActivity(professorLandingPageIntent);
         }
      } else {
         startActivity(new Intent(this, LoginActivity.class));
      }
   }
}
