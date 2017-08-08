package com.m2e.cs5540.autopresence.courses;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TimePicker;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.professors.ProfessorActivity;
import com.m2e.cs5540.autopresence.professors.home.ProfessorHomeActivity;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.MeetingDate;
import com.m2e.cs5540.autopresence.vao.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCourseActivity extends BaseActivity
      implements View.OnClickListener,
      LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

   private static final String TAG = AddCourseActivity.class.getName();

   private EditText courseId;
   private EditText courseName;
   private EditText classLocation;

   private EditText csTime;
   private EditText ceTime;
    private Calendar myTime;

  private EditText csDate;
   private EditText ceDate;
   private Calendar myCalendar;


   private List<CheckBox> weekdays;
   private char[] days = {'0', '0', '0', '0', '0', '0', '0'};

   //private Spinner semester;
   //private ArrayAdapter<CharSequence> spinnerAdapter;
   private Button submitButton;

   @Override protected void onCreate(Bundle savedInstanceState) {

      Log.i(TAG, "$$$$$$ onCreate() Invoked... ");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_course);

      this.courseId = (EditText) findViewById(R.id.cId);
      this.courseName = (EditText) findViewById(R.id.cName);
      this.classLocation = (EditText) findViewById(R.id.cLocation);

      this.csTime = (EditText) findViewById(R.id.sTime);
      this.ceTime = (EditText) findViewById(R.id.eTime);
        csTime.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               pickTime(csTime);
           }
       });
        ceTime.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               pickTime(ceTime);
           }
       });

        this.csDate = (EditText) findViewById(R.id.sDate);
      this.ceDate = (EditText) findViewById(R.id.eDate);

       csDate.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               pickDate(csDate);
           }
       });


       ceDate.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               pickDate(ceDate);
           }
       });

      weekdays = new ArrayList<>(6);

      weekdays.add((CheckBox) findViewById(R.id.sunday));
      weekdays.add((CheckBox) findViewById(R.id.monday));
      weekdays.add((CheckBox) findViewById(R.id.tuesday));
      weekdays.add((CheckBox) findViewById(R.id.wednesday));
      weekdays.add((CheckBox) findViewById(R.id.thrusday));
      weekdays.add((CheckBox) findViewById(R.id.friday));
      weekdays.add((CheckBox) findViewById(R.id.saturday));

      for (int i = 0; i < weekdays.size(); i++) {
         CheckBox cb = weekdays.get(i);
         final int j = i;
         cb.setOnCheckedChangeListener(
               new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton buttonView,
                        boolean isChecked) {
                     days[j] = (isChecked == true) ? '1' : '0';
                     Log.i(TAG,
                           "$$$$$$ AddCourseActivity.onCreateLoader called, days value is: " +
                                 String.valueOf(days));
                  }
               });
      }

      this.submitButton = (Button) findViewById(R.id.btn_Add_Course);
      submitButton.setOnClickListener(this);
   }


  @Override public void onBackPressed() {
      if (!AppContext.isUserLoggedIn()) {
         super.onBackPressed();
      }
   }

  //Implemented for proper back button functioning
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.add_course_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {

         case android.R.id.home:

            Intent about = new Intent(this,ProfessorActivity.class);;
            startActivity(about);
            finish();

            return true;

         default:
            return super.onOptionsItemSelected(item);
      }

   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      Log.i(TAG, "$$$$$$ AddCourseActivity.onCreateLoader called");

      Course course = new Course();
      course.setId(courseId.getText().toString());
      course.setName(courseName.getText().toString());
      course.setLocation(classLocation.getText().toString());

      User user = AppContext.getCurrentAppContext().getUser();
      course.setProfessorId(user.getId());

      MeetingDate md = new MeetingDate();
      md.setStartDate(csDate.getText().toString());
      md.setEndDate(ceDate.getText().toString());
      md.setStartTime(csTime.getText().toString());
      md.setEndTime(ceTime.getText().toString());

      course.setMeetingDate(md);
      md.setMeetingDays(String.valueOf(days));

      return new AddCourseAsyncTaskLoader(this, course);
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus loaderStatus) {

      Log.i(TAG, "$$$$$$ RegisterActivity.onLoadFinished called");
      if (loaderStatus.hasException()) {
         Toast.makeText(this, "Error " + loaderStatus.getExceptionMessage(),
               Toast.LENGTH_LONG).show();
      } else {
         Toast.makeText(this, "Course has been added successfully.",
               Toast.LENGTH_LONG).show();
      }
      //finish();
      //startActivity(getIntent());
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {
   }

   @Override public void onClick(View v) {
      addCourse();
   }

   public void addCourse() {
      Log.i(TAG, "Adding Course");
      submitButton.setEnabled(false);

      final ProgressDialog progressDialog = new ProgressDialog(
            AddCourseActivity.this,
            R.style.Theme_AppCompat_Light_DarkActionBar);
      progressDialog.setIndeterminate(true);
      progressDialog.setMessage("Adding Course...");
      progressDialog.show();

      getLoaderManager().initLoader(101, null, this);

      /*new android.os.Handler().postDelayed(new Runnable() {
         public void run() {
            progressDialog.dismiss();
         }
      }, 3000);*/
      progressDialog.hide();

       Intent intent = new Intent(getApplicationContext(),ProfessorActivity.class);
       startActivityForResult(intent, 0);
       finish();
   }
}