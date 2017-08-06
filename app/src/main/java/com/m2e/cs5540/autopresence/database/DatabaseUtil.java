package com.m2e.cs5540.autopresence.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;
import com.m2e.cs5540.autopresence.vao.Permit;
import com.m2e.cs5540.autopresence.vao.User;
import com.m2e.cs5540.autopresence.vao.UserAttendance;
import com.m2e.cs5540.autopresence.vao.UserCoordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maeswara on 7/8/2017.
 */
public class DatabaseUtil {

   private static final String TAG = DatabaseUtil.class.getName();
   private static DatabaseUtil databaseUtil = new DatabaseUtil();
   private DatabaseReference database =
         FirebaseDatabase.getInstance().getReference();

   private DatabaseUtil() {
   }

   public static DatabaseUtil getInstance() {
      return databaseUtil;
   }

   public synchronized void createUser(User user) {
      User existingUser = getUserByLogin(user.getLogin());
      if (existingUser != null) {
         throw new AppException("User with login " + user.getLogin() + " " +
               "already exists in the system!");
      }
      try {
         DatabaseReference usersRef = database.child("users");
         Log.i(TAG, "$$$ usersRef: " + usersRef);
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

   public synchronized void createCourse(Course course) {
      if (course != null) {
         try {
            Course existingCourse = getCourse(course.getId());
            if (existingCourse != null) {
               throw new AppException("Course with id " + course.getId() +
                     " already exists in the system!");
            }
            DatabaseReference coursesRef = database.child("courses");
            Log.i(TAG, "$$$ coursesRef: " + coursesRef);
            if (coursesRef != null) {
               coursesRef.push().setValue(course);
            }
         } catch (Exception e) {
            Log.e(TAG, "getUser failed", e);
            throw new AppException(
                  "Error saving user info for user " + course.getId() +
                        " into firebase. Cause: " + e.getClass().getName() +
                        ": " + e.getMessage(), e);
         }
      }
   }

   public synchronized void createPermit(Permit permit) {
      try {
         DatabaseReference permitRegsRef = database.child("permits");
         Log.i(TAG, "$$$ permitRegsRef: " + permitRegsRef);
         if (permitRegsRef != null) {
            permitRegsRef.push().setValue(permit);
         }
      } catch (Exception e) {
         Log.e(TAG, "createPermit failed", e);
         throw new AppException(
               "Error creating permit " + permit.getCourseId() + " - " +
                     permit.getCin() + " into firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   public synchronized UserCoordinate getUserCoordinate(String userId) {
      try {
         DatabaseReference userCoordsRef = database.child("userCoordinates");
         Log.i(TAG, "$$$ userCoordsRef: " + userCoordsRef);
         Log.i(TAG, "$$$ userId: " + userId);
         if (userCoordsRef != null) {
            UserCoordinate userCoordinate = getChildOnce(
                  userCoordsRef.orderByChild("userId").equalTo(userId),
                  UserCoordinate.class);
            Log.i(TAG, "$$$ userCoordinate: " + userCoordinate);
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

   public synchronized void createCourseEnrollment(
         CourseEnrollment courseEnrollment) {
      if (courseEnrollment != null) {
         try {
            List<CourseEnrollment> existingUserCourseEnrollments =
                  getCourseEnrollmentsByUserId(courseEnrollment.getUserId());
            if (existingUserCourseEnrollments != null) {
               for (CourseEnrollment existingCourseRegistration : existingUserCourseEnrollments) {
                  if (existingCourseRegistration.getCourseId().equals(
                        courseEnrollment.getCourseId())) {
                     throw new AppException(
                           "User " + courseEnrollment.getUserId() +
                                 " is already registered in course " +
                                 courseEnrollment.getCourseId());
                  }
               }
            }
            DatabaseReference courseEnrollRef = database.child(
                  "courseEnrollments");
            Log.i(TAG, "$$$ courseEnrollRef: " + courseEnrollRef);
            if (courseEnrollRef != null) {
               courseEnrollRef.push().setValue(courseEnrollment);
            }
         } catch (Exception e) {
            Log.e(TAG, "Student Course Enrollment failed", e);
            throw new AppException(
                  "Error saving course enroll info for student " +
                        " into firebase. Cause: " + e.getClass().getName() +
                        ": " + e.getMessage(), e);
         }
      }
   }

   public synchronized void updateUserCoordinate(
         UserCoordinate userCoordinate) {
      try {
         DatabaseReference userCoordsRef = database.child("userCoordinates");
         Log.i(TAG, "$$$ userCoordsRef: " + userCoordsRef);
         Log.i(TAG, "$$$ userId: " + userCoordinate.getUserId());
         if (userCoordsRef != null) {
            DatabaseReference currUserCoordinateRef = getChildReference(
                  userCoordsRef.orderByChild("userId")
                        .equalTo(userCoordinate.getUserId()));
            Log.i(TAG, "$$$ currUserCoordinateRef: " + currUserCoordinateRef);
            if (currUserCoordinateRef == null) {
               userCoordsRef.push().setValue(userCoordinate);
            } else {
               Map<String, Object> updateMap = getUserCoordinateUpdateMap(
                     userCoordinate);
               Log.i(TAG, "$$$ location coord updateMap = " + updateMap);
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

   public synchronized void createUserAttendance(
         UserAttendance userAttendance) {
      try {
         DatabaseReference userAttendancesRef = database.child(
               "userAttendances");
         Log.i(TAG, "$$$ userAttendancesRef: " + userAttendancesRef);
         Log.i(TAG, "$$$ userId: " + userAttendance.getUserId());
         if (userAttendancesRef != null) {
            DatabaseReference currUserAttendanceRef = getChildReference(
                  userAttendancesRef.orderByChild("attendanceDate")
                        .equalTo(userAttendance.getAttendanceDate()));
            Log.i(TAG, "$$$ currUserCoordinateRef: " + currUserAttendanceRef);
            if (currUserAttendanceRef == null) {
               userAttendancesRef.push().setValue(userAttendance);
            }
         }
      } catch (Exception e) {
         Log.e(TAG, "updateUserCoordinate failed", e);
         throw new AppException("Error updating userAttendance info for user " +
               userAttendance.getUserId() + " into firebase. Cause: " +
               e.getClass().getName() + ": " + e.getMessage(), e);
      }
   }

   private synchronized Map<String, Object> getUserCoordinateUpdateMap(
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

   public synchronized User getUserByLogin(String login) {
      try {
         DatabaseReference usersRef = database.child("users");
         Log.i(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            Query userQuery = usersRef.orderByChild("login").equalTo(login);
            Log.i(TAG, "$$$ userQuery: " + userQuery.getRef());
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

   public synchronized User getUserById(String userId) {
      try {
         DatabaseReference usersRef = database.child("users");
         Log.i(TAG, "$$$ usersRef: " + usersRef);
         if (usersRef != null) {
            Query userQuery = usersRef.orderByChild("id").equalTo(userId);
            Log.i(TAG, "$$$ userQuery: " + userQuery.getRef());
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

   public synchronized List<CourseEnrollment> getCourseEnrollmentsByUserId(
         String userId) {
      try {
         DatabaseReference courseEnrollmentsRef = database.child(
               "courseEnrollments");
         Log.i(TAG, "$$$ courseEnrollments: " + courseEnrollmentsRef);
         if (courseEnrollmentsRef != null) {
            Query courseRegistrationQuery = courseEnrollmentsRef.orderByChild(
                  "userId").equalTo(userId);
            Log.i(TAG,
                  "$$$ courseEnrollments Query: " + courseRegistrationQuery);
            List<CourseEnrollment> courseRegistrationList = getChildrenOnce(
                  courseRegistrationQuery, CourseEnrollment.class);
            return courseRegistrationList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourseEnrollmentsByUserId failed", e);
         throw new AppException(
               "Error querying course enrollments info for userId " + userId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public synchronized List<Course> getCoursesByProfId(String userId) {
      try {
         DatabaseReference courseRef = database.child("courses");
         Log.i(TAG, "$$$ courses: " + courseRef);
         if (courseRef != null) {
            Query courseQuery = courseRef.orderByChild("professorId").equalTo(
                  userId);
            Log.i(TAG, "$$$ ProfessorsCourse Query: " + courseQuery);
            List<Course> course = getChildrenOnce(courseQuery, Course.class);
            Log.i(TAG, "$$$ ProfessorsCourse Query, Total returned size is: " +
                  course.size());
            return course;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCoursesByProfId failed", e);
         throw new AppException(
               "Error querying course prof courses info for " + "userId " +
                     userId + " from firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
      return null;
   }

   public synchronized List<UserAttendance> getCourseAttendances(
         String courseId) {
      try {
         DatabaseReference userAttendancesRef = database.child(
               "userAttendances");
         Log.i(TAG, "$$$ userAttendancesRef: " + userAttendancesRef);
         if (userAttendancesRef != null) {
            Query courseRegistrationQuery = userAttendancesRef.orderByChild(
                  "courseId").equalTo(courseId);
            Log.i(TAG, "$$$ userAttendances Query: " + courseRegistrationQuery);
            List<UserAttendance> courseAttendanceList = getChildrenOnce(
                  courseRegistrationQuery, UserAttendance.class);
            return courseAttendanceList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getStudentAttendancesByCourseId failed", e);
         throw new AppException(
               "Error querying user attendance info for courseId " + courseId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public synchronized List<UserAttendance> getUserAttendances(String courseId,
         String userId) {
      try {
         DatabaseReference userAttendancesRef = database.child(
               "userAttendances");
         Log.i(TAG, "$$$ userAttendancesRef: " + userAttendancesRef);
         if (userAttendancesRef != null) {
            Query courseRegistrationQuery = userAttendancesRef.orderByChild(
                  "courseId").equalTo(courseId);
            Log.i(TAG, "$$$ userAttendances Query: " + courseRegistrationQuery);
            List<UserAttendance> finalList = new ArrayList<>();
            List<UserAttendance> userAttendanceList = getChildrenOnce(
                  courseRegistrationQuery, UserAttendance.class);
            for (int i = 0; i < userAttendanceList.size(); i++) {
               UserAttendance userAttendance = userAttendanceList.get(i);
               if (userAttendance.getUserId().equals(userId)) {
                  finalList.add(userAttendance);
               }
            }
            return finalList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getUserAttendances failed", e);
         throw new AppException(
               "Error querying user attendance info for courseId " + courseId +
                     " and userId " + userId + " from firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
      return null;
   }

   public synchronized List<UserAttendance> getUserAttendances(String userId) {
      try {
         DatabaseReference userAttendancesRef = database.child(
               "userAttendances");
         Log.i(TAG, "$$$ userAttendancesRef: " + userAttendancesRef);
         if (userAttendancesRef != null) {
            Query courseRegistrationQuery = userAttendancesRef.orderByChild(
                  "userId").equalTo(userId);
            Log.i(TAG, "$$$ userAttendances Query: " + courseRegistrationQuery);
            List<UserAttendance> userAttendanceList = getChildrenOnce(
                  courseRegistrationQuery, UserAttendance.class);
            return userAttendanceList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getUserAttendances failed", e);
         throw new AppException(
               "Error querying user attendance info for userId " + userId +
                     " from firebase. Cause: " + e.getClass().getName() + ": " +
                     e.getMessage(), e);
      }
      return null;
   }

   public synchronized List<CourseEnrollment> getCourseEnrollmentsByCourseId(
         String courseId) {
      try {
         DatabaseReference courseEnrollmentsRef = database.child(
               "courseEnrollments");
         Log.i(TAG, "$$$ courseEnrollmentsRef: " + courseEnrollmentsRef);
         if (courseEnrollmentsRef != null) {
            Query courseRegistrationQuery = courseEnrollmentsRef.orderByChild(
                  "courseId").equalTo(courseId);
            Log.i(TAG,
                  "$$$ courseEnrollments Query: " + courseRegistrationQuery);
            List<CourseEnrollment> courseRegistrationList = getChildrenOnce(
                  courseRegistrationQuery, CourseEnrollment.class);
            return courseRegistrationList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourseEnrollmentsByCourseId failed", e);
         throw new AppException(
               "Error querying course registration info for courseId " +
                     courseId + " from firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
      return null;
   }

   public synchronized Course getCourse(String courseId) {
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.i(TAG, "$$$ coursesRef: " + coursesRef);
         if (coursesRef != null) {
            Query courseQuery = coursesRef.orderByChild("id").equalTo(courseId);
            Log.i(TAG, "$$$ courseQuery: " + courseQuery);
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

   public synchronized List<Course> getAllCourses() {
      try {
         DatabaseReference coursesRef = database.child("courses");
         Log.i(TAG, "$$$ coursesRef: " + coursesRef);
         if (coursesRef != null) {
            Query allCoursesQuery = coursesRef.orderByChild("courseId");
            Log.i(TAG, "$$$ allCoursesQuery: " + allCoursesQuery);
            List<Course> allCoursesList = getChildrenOnce(allCoursesQuery,
                  Course.class);
            return allCoursesList;
         }
      } catch (Exception e) {
         Log.e(TAG, "getCourse failed", e);
         throw new AppException(
               "Error querying all courses from firebase. Cause: " +
                     e.getClass().getName() + ": " + e.getMessage(), e);
      }
      return null;
   }

   private <T extends Object> T getChildOnce(Query dbQuery,
         final Class<T> valueType) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      Log.i(TAG, "$$$ Waiting for DB results for : " + dbQuery);
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {

         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               objList.add(dataSnapshot.getChildren().iterator().next()
                     .getValue(valueType));
            }
            wait[0] = false;
            Log.i(TAG, "$$$ " + valueType.getSimpleName() + " received from " +
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
      Log.i(TAG,
            "$$$ Returning " + objList + " for " + valueType.getSimpleName());
      return objList.size() > 0 ? objList.get(0) : null;
   }

   private <T extends Object> List<T> getChildrenOnce(Query dbQuery,
         final Class<T> valueType) {

      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<T> objList = new ArrayList<>();
      Log.i(TAG, "$$$ Waiting for DB results for : " + dbQuery);
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               for (DataSnapshot dbSnapshot : dataSnapshot.getChildren()) {
                  objList.add(dbSnapshot.getValue(valueType));
               }
            }
            wait[0] = false;
            Log.i(TAG, "$$$ " + valueType.getSimpleName() + " received from " +
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
      Log.i(TAG,
            "$$$ Returning " + Collections.unmodifiableCollection(objList) +
                  " for " + valueType.getSimpleName());
      return objList;
   }

   private DatabaseReference getChildReference(Query dbQuery) {
      final Exception[] exceptions = {null};
      final boolean[] wait = {true};
      final List<DatabaseReference> objList = new ArrayList<>();
      Log.i(TAG, "$$$ Waiting for DB results for : " + dbQuery);
      dbQuery.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i(TAG, "$$$ debug: " + dataSnapshot.getValue());
            if (dataSnapshot.exists()) {
               objList.add(
                     dataSnapshot.getChildren().iterator().next().getRef());
            }
            wait[0] = false;
            Log.i(TAG, "$$$ DatabaseReference received from " + "database = " +
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
      Log.i(TAG, "$$$ Returning " + objList + " for DatabaseReference");
      return objList.size() > 0 ? objList.get(0) : null;
   }
}
