package com.m2e.cs5540.autopresence.professors;

import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.EventListener;

/**
 * Created by maeswara on 8/6/2017.
 */
public interface StudentArrivalListener extends EventListener {
   public abstract void onStudentArrival(UserAttendance studentAttendance);
}
