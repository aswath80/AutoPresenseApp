package com.m2e.cs5540.autopresence.professors;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfessorAttendanceActivity extends BaseActivity
      implements LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

   private static final String TAG = "ProfessorAtteActivity";
   private RecyclerView studentAttendanceRecyclerView;
   private ProfessoAttendanceAdapter professorMainAdaptor;

   private EditText datepicker;
   private Button datepickerButton;
   private Calendar myCalendar;
   private List<UserAttendance> attendanceList;

   public void setAttendanceList(List<UserAttendance> attendanceList) {
      if (attendanceList == null) {
         attendanceList = new ArrayList<>();
      }
      this.attendanceList = attendanceList;

   }

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_professor_attendance);

      datepicker = (EditText) findViewById(R.id.datepicker);
      datepickerButton = (Button) findViewById(R.id.datepickerButton);

      datepickerButton.setOnClickListener(new View.OnClickListener() {

         @Override public void onClick(View v) {
            pickDate(datepicker, datepickerButton);

            initalizeLoader();
         }
      });

      datepicker.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count,
               int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before,
               int count) {
            List<UserAttendance> result = new ArrayList<>(
                  attendanceList.size());
            for (UserAttendance attendance : attendanceList) {
               Log.i(TAG,
                     "Date Picker, Date received is: " + datepicker.getText());
               Log.i(TAG, "Date Picker, DbDate received is: " +
                     attendance.getAttendanceDate());
               if (attendance.getAttendanceDate().equals(
                     datepicker.getText().toString())) {
                  Log.i(TAG, "Date Picker, Matched id is: " +
                        attendance.getUserId());
                  result.add(attendance);
               }
            }
            Log.i(TAG, "Date Picker, Sending for update data size is: " +
                  result.size());
            professorMainAdaptor.setAttendanceList(result);
         }

         @Override public void afterTextChanged(Editable s) {

         }
      });

      studentAttendanceRecyclerView = (RecyclerView) findViewById(
            R.id.professorCourseRecyclerView);
      professorMainAdaptor = new ProfessoAttendanceAdapter();
      studentAttendanceRecyclerView.setAdapter(professorMainAdaptor);

      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
      studentAttendanceRecyclerView.setLayoutManager(linearLayoutManager);
      DatabaseUtil.getInstance().registerForCourseAttendances(
            getIntent().getStringExtra("courseId"),
            new StudentArrivalListener() {
               @Override
               public void onStudentArrival(UserAttendance studentAttendance) {
                  List<UserAttendance> attendanceList =
                        professorMainAdaptor.getAttendanceList();
                  attendanceList.add(studentAttendance);
                  Log.i(TAG, "Fetched data for user: " +
                        studentAttendance.getUserId());
                  professorMainAdaptor.setAttendanceList(attendanceList);
               }
            });
   }

   public void initalizeLoader() {
      getLoaderManager().initLoader(107, null, this).forceLoad();
      showProgressDialog("Loading data...");
   }

   public void pickDate(final EditText text, final Button button) {
      myCalendar = Calendar.getInstance();
      final DatePickerDialog.OnDateSetListener date =
            new DatePickerDialog.OnDateSetListener() {

               @Override
               public void onDateSet(DatePicker view, int year, int monthOfYear,
                     int dayOfMonth) {
                  myCalendar.set(Calendar.YEAR, year);
                  myCalendar.set(Calendar.MONTH, monthOfYear);
                  myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                  updateLabel(text);
               }

            };

      button.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            new DatePickerDialog(ProfessorAttendanceActivity.this, date,
                  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                  myCalendar.get(Calendar.DAY_OF_MONTH)).show();
         }
      });
   }

   private void updateLabel(EditText v) {
      String myFormat = "dd-MMM-yyyy"; //In which you need put here
      SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
      v.setText(sdf.format(myCalendar.getTime()));
   }

   @Override public void onBackPressed() {
      if (!AppContext.isUserLoggedIn()) {
         super.onBackPressed();
      }
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {

      String courseId = getIntent().getStringExtra("courseId");
      if (courseId == null) {
         return null;
      } else {
         return new ProfessorAttendanceAsyncTaskLoader(this, courseId);
      }
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus data) {
      if (data != null) {
         if (data.getException() == null) {
            List<UserAttendance> attendanceList =
                  (List<UserAttendance>) data.getResult();
            if (attendanceList != null && attendanceList.size() > 0) {
               setAttendanceList(attendanceList);
            }
         } else {
            Exception e = data.getException();
            Toast.makeText(this, "Error getting attendance data. Cause: " +
                        e.getClass().getName() + ": " + e.getMessage(),
                  Toast.LENGTH_LONG).show();
         }
      }
      hideProgressDialog();
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }
}