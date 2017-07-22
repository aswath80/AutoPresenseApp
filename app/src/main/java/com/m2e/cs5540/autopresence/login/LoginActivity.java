package com.m2e.cs5540.autopresence.login;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.register.RegisterActivity;
import com.m2e.cs5540.autopresence.service.LocationUpdateService;
import com.m2e.cs5540.autopresence.vao.User;

/**
 * Created by maeswara on 7/8/2017.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,
      LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

   private static final String TAG = LoginActivity.class.getName();
   private EditText usernameEditText;
   private Button loginButton;
   private Button register;


   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login_layout);
      this.usernameEditText = (EditText) findViewById(R.id.usernameText);

      this.register = (Button) findViewById(R.id.btn_signup);
      this.register.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivityForResult(intent, 0);
         }
      });
      addButtonClickListener();
   }

   private void addButtonClickListener() {
      loginButton = (Button) findViewById(R.id.loginButton);
      loginButton.setOnClickListener(this);
   }

   @Override public void onClick(View v) {
      EditText userNameText = (EditText) findViewById(R.id.usernameText);
      String username = userNameText.getText().toString();

      EditText passwordText = (EditText) findViewById(R.id.passwordText);
      String password = userNameText.getText().toString();

      Log.d(TAG, "$$$$$$ Login process start... ");

      if (username == null || username.isEmpty() || password == null ||
            password.isEmpty()) {
         Toast.makeText(this, "Enter a valid " + "username and password!",
               Toast.LENGTH_SHORT).show();
      } else {
         Log.d(TAG, "$$$$$$ LoadManager.initLoader called");
         getLoaderManager().initLoader(0, null, this);
         showProgressDialog();
      }
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      Log.d(TAG, "$$$$$$ LoginActivity.onCreateLoader called");
      return new LoginAsyncTaskLoader(this, usernameEditText);
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus loaderStatus) {
      hideProgressDialog();
      Log.d(TAG, "$$$$$$ LoginActivity.onLoadFinished called");
      if (loaderStatus.hasException()) {
         Toast.makeText(this, "Error " + loaderStatus.getExceptionMessage(),
               Toast.LENGTH_LONG).show();
      } else {
         String username = loaderStatus.getResult() != null ?
               ((User) loaderStatus.getResult()).getName() : null;
         Toast.makeText(this, "Logged in user is " + username,
               Toast.LENGTH_LONG).show();
         startLocationService(loaderStatus);
         //new LocationServiceAsyncTask().execute(
         //      "893b671d-d09e-428f-a251-8b21887d76a4");
      }
   }

   private void startLocationService(AsyncLoaderStatus loaderStatus) {
      User user = (User) loaderStatus.getResult();
      Intent locationServiceIntent = new Intent(this, LocationUpdateService.class);
      locationServiceIntent.putExtra("userId", user.getId());
      startService(locationServiceIntent);
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }
}
