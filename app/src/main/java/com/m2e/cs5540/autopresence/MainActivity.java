package com.m2e.cs5540.autopresence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m2e.cs5540.autopresence.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      initLoginScreen();
   }

   private void initLoginScreen() {
      startActivity(new Intent(this, LoginActivity.class));
   }
}
