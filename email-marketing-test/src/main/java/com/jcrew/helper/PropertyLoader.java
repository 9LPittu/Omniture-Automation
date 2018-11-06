package com.jcrew.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Venkat
 * Date: 11/5/16
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyLoader {
    private static boolean isLocal = true;
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("testconfig");

    private static void setIsLocal(boolean b) {
        isLocal = b;
    }

    public static boolean isLocal() {
        return isLocal;
    }

    public static String getCodeVersion() {

        String codeVersion = System.getenv("codeVersion");
        if (codeVersion != null) {

            return codeVersion;
        }
        return "6.1";
    }

    public static String getTestCycleTime() {

        String testCycleTime = System.getenv("BUILD_ID");

        if (testCycleTime != null) {

            return testCycleTime;
        }

        Date date = Calendar.getInstance().getTime();

        String convertedDateFormat = "yyyy-MM-dd HH:mm:ss";

        String formattedDate = new SimpleDateFormat(convertedDateFormat).format(date);

        return formattedDate;
    }

    public static String getServerURL() {
        return RESOURCE_BUNDLE.getString("server.url");
    }

    public static String getDbConnectString() {
        String serverName = System.getenv("remoteHost");
        if (serverName != null) {
            setIsLocal(false);
            return "jdbc:oracle:thin:@" + serverName + ":1521:QUANTUM";
        }

        // If a VM argument is set up for a particular URL, then we use it instead.
        serverName = System.getProperty("db.host");
        if (serverName != null) {
            return "jdbc:oracle:thin:@" + serverName + ":1521:QUANTUM";
        }

        return RESOURCE_BUNDLE.getString("dbConnectString");

    }

    public static String getDbUser() {
        return RESOURCE_BUNDLE.getString("dbUser");
    }

    public static String getDbPassword() {
        return RESOURCE_BUNDLE.getString("dbPassword");
    }

    public static String getFirefoxBin() {
        return RESOURCE_BUNDLE.getString("firefox.bin");
    }

    public static String getDBConnectionURL() {
        return RESOURCE_BUNDLE.getString("dbConnectionUrl");
    }

    public static String getSeleniumBaseURL() {
        return RESOURCE_BUNDLE.getString("selenium.baseUrl");
    }

    public static String getEmailClient() {
        return RESOURCE_BUNDLE.getString("mail.client");
    }

    public static String getEmailUserId() {
        return RESOURCE_BUNDLE.getString("user.id");
    }

    public static String getBrokeImgConfig() {
        return RESOURCE_BUNDLE.getString("validateBrokenImage").trim();
    }

    public static String getEmailUserPassword() {
        return RESOURCE_BUNDLE.getString("user.password");
    }

    public static String getMaxNumberOfEmailsToSearch() {
        return RESOURCE_BUNDLE.getString("maxNumberOfEmailsToSearch");
    }

    public static String getMergedFile() {
        return RESOURCE_BUNDLE.getString("mergedFile");
    }

    public static long getWaitTime() {
        return 6000;
    }

    public static String getBrowserType() {

        return RESOURCE_BUNDLE.getString("browserType");
    }

    public static String getBufferedResultFile() {

        return RESOURCE_BUNDLE.getString("bufferedResultFile");
    }

    public static boolean isScreenShotsCaptured() {
        String captureScreenShot = RESOURCE_BUNDLE.getString("captureScreenShots");
        if ("true".equals(captureScreenShot)) {
            return true;
        }
        return false;
    }

    public static String getScreenCaptureLocation() {
        return RESOURCE_BUNDLE.getString("captureLocation");
    }

    public static String getExTempFile() {
        return RESOURCE_BUNDLE.getString("exTempFile");
    }

    public static String getPhantomJsPath() {
        return RESOURCE_BUNDLE.getString("phantomJsPath");
    }
}