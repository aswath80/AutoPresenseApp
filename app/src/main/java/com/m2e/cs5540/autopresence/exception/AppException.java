package com.m2e.cs5540.autopresence.exception;

/**
 * Created by maeswara on 7/8/2017.
 */
public class AppException extends RuntimeException {
   public AppException(String s) {
      super(s);
   }

   public AppException(String s, Exception cause) {
      super(s, cause);
   }
}
