package com.m2e.cs5540.autopresence.database;

import android.test.AndroidTestCase;

import com.m2e.cs5540.autopresence.vao.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by maeswara on 7/15/2017.
 */
public class DatabaseUtilTest {
   private String encryptPassword(String password)
         throws NoSuchAlgorithmException {
      MessageDigest digest = MessageDigest.getInstance(
            "MD5");//Create MessageDigest object for MD5
      digest.update(password.getBytes(), 0,
            password.length());//Update input string in message digest
      String encryptedPass = new BigInteger(1, digest.digest()).toString(16);
      return encryptedPass;
   }

   @Test public void testCreateUser() throws Exception {
      DatabaseUtil dbUtil = DatabaseUtil.getInstance();
      User user = new User();
      user.setId(UUID.randomUUID().toString());
      user.setLogin("mani@test.com");
      user.setName("Manikandan Eswaran");
      String encryptedPass = encryptPassword("password");
      user.setPassword(encryptedPass);
      dbUtil.createUser(user);
   }
}
