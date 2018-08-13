package com.polotechnologies.polo.pwaniuniversity;

public class Config {
    //URL  file
    public static final String LOGIN_URL = "https://pwaniapp.000webhostapp.com/login.php";
    public static final String STUDENT_ID_URL = "https://pwaniapp.000webhostapp.com/studentID.php";
    public static final String STUDENT_FINANCE_URL = "https://pwaniapp.000webhostapp.com/studentFinance.php";


    //Keys for admission and password as defined in our $_POST['key'] in login.php
    public static final String KEY_ADMISSION = "admission_no";
    public static final String KEY_PASSWORD = "password";

    //Keys for admission as defined in our $_POST['key']
    public static final String KEY_ADM = "admNo";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "pwaniApp";

    //This would be used to store the values of current logged in user
    public static final String ADMISSION_SHARED_PREF = "admission_no";
    public static final String FEE_BALANCE_SHARED_PREF = "fee_balance";


    //This would be used to store the course of current logged in user
    public static final String COURSE_SHARED_PREF = "admission_no";

    //This would be used to store the passport of current logged in user
    public static final String PASSPORT_SHARED_PREF = "admission_no";

    //This would be used to store the date of issue of current logged in user
    public static final String ISSUE_DATE_SHARED_PREF = "admission_no";


    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
