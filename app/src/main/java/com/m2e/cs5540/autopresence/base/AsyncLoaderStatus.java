package com.m2e.cs5540.autopresence.base;

/**
 * Created by maeswara on 7/15/2017.
 */
public class AsyncLoaderStatus {
   private Object result;
   private Exception exception;

   public Object getResult() {
      return result;
   }

   public void setResult(Object result) {
      this.result = result;
   }

   public Exception getException() {
      return exception;
   }

   public void setException(Exception exception) {
      this.exception = exception;
   }

   public boolean hasResult() {
      return result != null;
   }

   public boolean hasException() {
      return exception != null;
   }

   public String getExceptionMessage() {
      StringBuilder sb = new StringBuilder();
      Throwable t = exception;
      while (t != null) {
         sb.append(t.getMessage() + "\n");
         t = t.getCause();
      }
      return sb.toString();
   }
}
