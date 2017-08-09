package com.m2e.cs5540.autopresence.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by maeswara on 7/8/2017.
 */
//Source: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/BaseActivity.java
public class BaseActivity extends AppCompatActivity {
   private ProgressDialog mProgressDialog;

   protected void showProgressDialog(String message) {
      if (mProgressDialog == null) {
         mProgressDialog = new ProgressDialog(this);
         mProgressDialog.setCancelable(false);
         mProgressDialog.setIndeterminate(true);
      }
      mProgressDialog.setMessage(message);
      mProgressDialog.show();
   }

   protected void hideProgressDialog() {
      if (mProgressDialog != null && mProgressDialog.isShowing()) {
         mProgressDialog.dismiss();
      }
   }
}
