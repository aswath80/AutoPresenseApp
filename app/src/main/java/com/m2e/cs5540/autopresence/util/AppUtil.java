package com.m2e.cs5540.autopresence.util;

import com.m2e.cs5540.autopresence.exception.AppException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by maeswara on 7/16/2017.
 */
public class AppUtil {
   public static String encryptPassword(String password) {
      try {
         MessageDigest digest = MessageDigest.getInstance("MD5");
         digest.update(password.getBytes(), 0, password.length());
         String encryptedPass = new BigInteger(1, digest.digest()).toString(16);
         return encryptedPass;
      } catch (NoSuchAlgorithmException e) {
         throw new AppException("Error encrypting the user password", e);
      }
   }
}
