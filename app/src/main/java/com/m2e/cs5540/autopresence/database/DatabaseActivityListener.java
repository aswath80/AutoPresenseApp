package com.m2e.cs5540.autopresence.database;

public interface DatabaseActivityListener<T> {
      public abstract void onRecordCreated(T obj);
}