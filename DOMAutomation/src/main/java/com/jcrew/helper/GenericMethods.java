package com.jcrew.helper;

import com.cucumber.listener.Reporter;
import org.junit.Assert;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class GenericMethods {
    private static List<String> asserts = new ArrayList<String>();

    public static List<String> getAssertFails() {
        return asserts;
    }

    public static void setAssertFails(List<String> assertErrors) {
        asserts = assertErrors;
    }

    public static String calculateFileHashCode(String filePath) {
        File file = new File(filePath);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] bytesBuffer = new byte[1024];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
                digest.update(bytesBuffer, 0, bytesRead);
            }

            byte[] hashedBytes = digest.digest();

            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | IOException ex) {
            Reporter.addStepLog("Failed to calculate for the file: " + filePath);
            Assert.fail();
            return "";
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xFF) + 256, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
