package com.quocngay.carparkbooking.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ninhh on 5/22/2017.
 */

public class Constant {
    public static String APP_PREF = "AppPref";
    public static String SERVER_EMAIL = "email";
    public static String SERVER_PASSWORD = "password";
    public static String SERVER_TOKEN = "token";
    public static String SERVER_RESPONSE = "res";
    public static String SERVER_RESPONSE_DATA = "data";
    public static String SERVER_RESPONSE_GARA = "gara";
    public static String SERVER_RESPONSE_TICKTET = "booking_ticked";


    public static int KEY_EXPIRED_TICKET = 30 * 60 * 1000; //in milliseconds
    public static DateFormat KEY_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    public static DateFormat KEY_TIME_DURATION_FORMAT = new SimpleDateFormat("hh:mm:ss");
    public static DateFormat KEY_DATE_TIME_DURATION_FORMAT = new SimpleDateFormat("dd:hh:mm");

}
