package com.m2e.cs5540.autopresence.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;
import com.m2e.cs5540.autopresence.vao.CourseRegistration;
import com.m2e.cs5540.autopresence.vao.Permit;
import com.m2e.cs5540.autopresence.vao.User;
import com.m2e.cs5540.autopresence.vao.UserAttendance;
import com.m2e.cs5540.autopresence.vao.UserCoordinate;
import com.m2e.cs5540.autopresence.vao.UserRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maeswara on 7/8/2017.
 */
public class DatabaseUtil {

   private static final String TAG = DatabaseUtil.class.getName();
   private DatabaseReference database =
         FirebaseDatabase.getInstance().getReference();
   private static DatabaseUtil databaseUtil = new DatabaseUtil();

   private DatabaseUtil() {
   }

   public static DatabaseUtil getInstance() {
      return databaseUtil;
   }

   public void createUser(User user) {
      User existingUser = getUserByLogin(user.getLogin());
      if (existingUser != null) {
         throw new AppException("User with login " + user.getLogin() + " " +
               "already exists in the system!");
      }
      try {
         DatabaseReference usersRef = database.child("users");
         Log.d(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            usersRef.push().setValue(user);
         }
      } catch (Exception e) {
         Log.e(TAG, "createUser failed", e);
         throw new AppException(
               "Error saving user info for user " + user.getLogin() +
                     " into firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
   }

   public void createCourse(Course course) {
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.d(TAG, "$$$ coursesRef: " + coursesRef);
         if (coursesRef != null) {
            coursesRef.push().setValue(course);
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException(
               "Error saving user info for user " + course.getId() +
                     " into firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
   }

   public void createCourseRegistration(CourseRegistration courseRegistration) {
      try {
         DatabaseReference courseRegsRef = database.child(
               "courseRegistrations");
         Log.d(TAG, "$$$ courseRegsRef: " + courseRegsRef);
         if (courseRegsRef != null) {
            courseRegsRef.push().setValue(courseRegistration);
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException("Error saving user info for user " +
               courseRegistration.getUserId() + " - " +
               courseRegistration.getCourseId() + " into firebase. Cause: " +
               e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public void createPermit(Permit permit){
      try {
         DatabaseReference permitRegsRef = database.child(
                 "Permits");
         Log.d(TAG, "$$$ permitRegsRef: " + permitRegsRef);
         if (permitRegsRef != null) {
            permitRegsRef.push().setValue(permit);
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException("Error saving user info for Course Pemit " +
                 permit.getCourseId() + " - " +
                 permit.getCin() + " into firebase. Cause: " +
                 e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public void updateUserRegistration(UserRegistration reg) {
      try {
         DatabaseReference userRegsRef = database.child("userRegistrations");
         Log.d(TAG, "$$$ userRegsRef: " + userRegsRef);
         if (userRegsRef != null) {
            userRegsRef.push().setValue(reg);
         }
      } catch (Exception e) {
         Log.e(TAG, "User Registration failed", e);
         throw new AppException(
               "Error saving user info for user " + " into firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public UserCoordinate getUserCoordinate(String userId) {
      try {
         DatabaseReference userCoordsRef = database.child("userCoordinates");
         Log.d(TAG, "$$$ userCoordsRef: " + userCoordsRef);
         Log.d(TAG, "$$$ userId: " + userId);
         if (userCoordsRef != null) {
            UserCoordinate userCoordinate = getChildOnce(
                  userCoordsRef.orderByChild("userId").equalTo(userId),
                  UserCoordinate.class);
            Log.d(TAG, "$$$ userCoordinate: " + userCoordinate);
            return userCoordinate;
         }
      } catch (Exception e) {
         Log.e(TAG, "updateUserCoordinate failed", e);
         throw new AppException(
               "Error getting userCoordinate info for user " + userId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public void studentCourseEnrollment(CourseEnrollment enroll) {
      try{
         DatabaseReference courseEnrollRef = database.child("studentCourseEnroll");
         Log.d(TAG, "$$$ courseEnrollRef: " + courseEnrollRef);
         if (courseEnrollRef != null) {
            courseEnrollRef.push().setValue(enroll);
         }
      } catch (Exception e) {
         Log.e(TAG, "Student Course Enrollment failed", e);
         throw new AppException("Error saving course enroll info for student " +
                 " into firebase. Cause: " + e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public void updateUserCoordinate(UserCoordinate userCoordinate) {
      try {
         DatabaseReference userCoordsRef = database.child("userCoordinates");
         Log.d(TAG, "$$$ userCoordsRef: " + userCoordsRef);
         Log.d(TAG, "$$$ userId: " + userCoordinate.getUserId());
         if (userCoordsRef != null) {
            DatabaseReference currUserCoordinateRef = getChildReference(
                  userCoordsRef.orderByChild("userId")
                        .equalTo(userCoordinate.getUserId()));
            Log.d(TAG, "$$$ currUserCoordinateRef: " + currUserCoordinateRef);
            if (currUserCoordinateRef == null) {
               userCoordsRef.push().setValue(userCoordinate);
            } else {
               Map<String, Object> updateMap = getUserCoordinateUpdateMap(
                     userCoordinate);
               Log.d(TAG, "$$$ location coord updateMap = " + updateMap);
               currUserCoordinateRef.updateChildren(updateMap);
            }
         }
      } catch (Exception e) {
         Log.e(TAG, "updateUserCoordinate failed", e);
         throw new AppException("Error updating userCoordinate info for user " +
               userCoordinate.getUserId() + " into firebase. Cause: " +
               e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public void registerAttendance(UserAttendance userAttendance) {
      try {
         DatabaseReference userAttendancesRef = database.child(
               "userAttendances");
         Log.d(TAG, "$$$ userAttendancesRef: " + userAttendancesRef);
         Log.d(TAG, "$$$ userId: " + userAttendance.getUserId());
         if (userAttendancesRef != null) {
            DatabaseReference currUserAttendanceRef = getChildReference(
                  userAttendancesRef.orderByChild("attendanceDate")
                        .equalTo(userAttendance.getAttendanceDate()));
            Log.d(TAG, "$$$ currUserCoordinateRef: " + currUserAttendanceRef);
            if (currUserAttendanceRef == null) {
               currUserAttendanceRef.push().setValue(userAttendance);
            }
         }
      } catch (Exception e) {
         Log.e(TAG, "updateUserCoordinate failed", e);
         throw new AppException("Error updating userAttendance info for user " +
               userAttendance.getUserId() + " into firebase. Cause: " +
               e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   private Map<String, Object> getUserCoordinateUpdateMap(
         UserCoordinate userCoordinate) {
      try {
         Map<String, Object> updateMap = new HashMap<>();
         updateMap.put("currentLatitude", userCoordinate.getCurrentLatitude());
         updateMap.put("currentLongitude",
               userCoordinate.getCurrentLongitude());
         updateMap.put("lastUpdateTime", userCoordinate.getLastUpdateTime());
         return updateMap;
      } catch (Exception e) {
         Log.e(TAG, "getUpdateMap failed", e);
         throw new AppException(
               "Error creating getUpdateMap for userCoordinate " +
                     userCoordinate + ".  Cause: " + e.getClass().getName() +
                     ": " + e.getMessage(), e);
      }
   }

   public User getUserByLogin(String login) {
      try {
         DatabaseReference usersRef = database.child("users");
         Log.d(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            Query userQuery = usersRef.orderByChild("login").equalTo(login);
            //Log.d(TAG, "$$$ userQuery: " + userQuery.getRef());
            User user = getChildOnce(userQuery, User.class);
            return user;
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException("Error querying user info for login " + login +
               " from firebase. Cause: " + e.getClass().getName() + ": " +
               e.getMessage(), e);
      }
      return null;
   }

   public User getUserById(String userId) {
      try {
         DatabaseReference usersRef = database.child("users");
         Log.d(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            Query userQuery = usersRef.orderByChild("id").equalTo(userId);
            //Log.d(TAG, "$$$ userQuery: " + userQuery.getRef());
            User user = getChildOnce(userQuery, User.class);
            return user;
         }
      } catch (Exception e) {
         Log.e(TAG, "getUser failed", e);
         throw new AppException(
               "Error querying user info for userId " + userId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public List<CourseRegistration> getUserCourseRegistrations(String userId) {
      try {
         DatabaseReference courseRegsRef = database.child(
               "courseRegistrations");
         Log.d(TAG, "$$$ courseRegsRef: " + courseRegsRef);
         if (courseRegsRef != null) {
            Query courseRegistrationQuery = courseRegsRef.orderByChild("userId")
                  .equalTo(userId);
            Log.d(TAG,
                  "$$$ courseRegistrationQuery: " + courseRegistrationQuery);
            List<CourseRegistration> courseRegistrationList = getChildrenOnce(
                  courseRegistrationQuery, CourseRegistration.class);
            return courseRegistrationList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourse failed", e);
         throw new AppException(
               "Error querying course registration info for userId " + userId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public List<CourseRegistration> getCourseRegistrations(String courseId) {
      try {
         DatabaseReference courseRegsRef = database.child(
               "courseRegistrations");
         Log.d(TAG, "$$$ courseRegsRef: " + courseRegsRef);
         if (courseRegsRef != null) {
            Query courseRegistrationQuery = courseRegsRef.orderByChild(
                  "courseId").equalTo(courseId);
            Log.d(TAG,
                  "$$$ courseRegistrationQuery: " + courseRegistrationQuery);
            List<CourseRegistration> courseRegistrationList = getChildrenOnce(
                  courseRegistrationQuery, CourseRegistration.class);
            return courseRegistrationList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourse failed", e);
         throw new AppException(
               "Error querying course registration info for courseId " +
                     courseId + " from firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
      return null;
   }

   public Course getCourse(String courseId) {
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.d(TAG, "$$$ coursesRef: " + coursesRef);
         if (coursesRef != null) {
            Query courseQuery = coursesRef.orderByChild("id").equalTo(courseId);
            Log.d(TAG, "$$$ courseQuery: " + courseQuery);
            Course course = getChildOnce(courseQuery, Course.class);
            return course;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourse failed", e);
         throw new AppException(
               "Error querying course info for courseId " + courseId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public List<String> getAllCourse() {

      final List<String> allCourses = new ArrayList<>();
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.d(TAG, "$$$ coursesRef: " + coursesRef);

         coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Map<String,Object> cources = (Map<String,Object>)dataSnapshot.getValue();

               //iterate through each courses
               for (Map.Entry<String, Object> entry : cources.entrySet()){
                  Map singlecourse = (Map) entry.getValue();
                  allCourses.add((String) singlecourse.get("id"));
               }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
               Log.e(TAG, "getAllCourse failed", databaseError.toException());
               databaseError.toException().printStackTrace();
                          }
         });
      } catch (Exception e) {
         Log.e(TAG, "getCourse failed", e);
         throw new AppException(                 "Error querying course info for courseId " + e.getMessage(), e);
      }
      return allCourses;
   }

   public List<String> courseEnroll(String cin) {
      final List<String> enrolls = new ArrayList<>();
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      try {
         DatabaseReference ref = database.child("studentCourseEnroll");
         Log.e(TAG, "db ref" + ref);
         try{
            Thread.sleep(20);
         }catch (Exception e){
            e.printStackTrace();
         }
         ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               try{
                  Thread.sleep(10);
               }catch (Exception e){
                  e.printStackTrace();
               }
               Log.e(TAG, "datasnapshot "+ dataSnapshot.getValue());
               Map<String, Object> allEnrolls = (Map<String, Object>) dataSnapshot.getValue();

               for (Map.Entry<String, Object> enroll : allEnrolls.entrySet()){
                  Map entry = (Map) enroll.getValue();
                  enrolls.add((String) entry.get("cin"));
               }
               wait[0] = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               Log.e(TAG, "getValueOnce failed", databaseError.toException());
               databaseError.toException().printStackTrace();
               exceptions[0] = databaseError.toException();
               wait[0] = false;


                 "Error querying course info for CIN " + cin +
                         " from firebase. Cause: " + e.getClass().getName() + ": " +
                         e.getMessage(), e);
      }
     /* while (wait[0] == true && enrolls.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
                 exceptions[0].getClass().getName() + ": " +
                 exceptions[0].getMessage(), exceptions[0]);
      }*/
      return enrolls;
   }



   private <T extends Object> T getChildOnce(Query dbQuery,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      Log.d(TAG, "$$$ Waiting for DB results for : " + dbQuery);
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {

         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               objList.add(dataSnapshot.getChildren().iterator().next()
                     .getValue(valueType));
            }
            wait[0] = false;
            Log.d(TAG, "$$$ " + valueType.getSimpleName() + " received from " +
                  "database = " + objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getValueOnce failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] == true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      Log.d(TAG,
            "$$$ Returning " + objList + " for " + valueType.getSimpleName());
      return objList.size() > 0 ? objList.get(0) : null;
   }

   private <T extends Object> List<T> getChildrenOnce(Query dbQuery,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      Log.d(TAG, "$$$ Waiting for DB results for : " + dbQuery);
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               for (DataSnapshot dbSnapshot : dataSnapshot.getChildren()) {
                  objList.add(dbSnapshot.getValue(valueType));
               }
            }
            wait[0] = false;
            Log.d(TAG, "$$$ " + valueType.getSimpleName() + " received from " +
                  "database = " + objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getValueOnce failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] == true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      Log.d(TAG,
            "$$$ Returning " + objList + " for " + valueType.getSimpleName());
      return objList;
   }

   private DatabaseReference getChildReference(Query dbQuery) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<DatabaseReference> objList = new ArrayList<>();
      Log.d(TAG, "$$$ Waiting for DB results for : " + dbQuery);
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               objList.add(
                     dataSnapshot.getChildren().iterator().next().getRef());
            }
            wait[0] = false;
            Log.d(TAG, "$$$ DatabaseReference received from " + "database = " +
                  objList);
         }

         @Override public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "getChildReference failed", databaseError.toException());
            databaseError.toException().printStackTrace();
            exceptions[0] = databaseError.toException();
            wait[0] = false;
         }
      });
      while (wait[0] == true && objList.size() == 0) {
         try {
            Thread.sleep(10);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (exceptions[0] != null) {
         throw new AppException("getValueOnce failed. Cause: " +
               exceptions[0].getClass().getName() + ": " +
               exceptions[0].getMessage(), exceptions[0]);
      }
      Log.d(TAG, "$$$ Returning " + objList + " for DatabaseReference");
      return objList.size() > 0 ? objList.get(0) : null;
   }
}
