package com.polotechnologies.polo.pwaniuniversity;

public class Config {
    //URL  file
    public static final String LOGIN_URL = "http://192.168.43.129/PwaniApp/PHP/login.php";
    public static final String STUDENT_ID_URL = "http://192.168.43.129/PwaniApp/PHP/studentID.php";
    public static final String STUDENT_FINANCE_URL = "http://192.168.43.129/PwaniApp/PHP/studentFinance.php";
    public static final String STUDENT_EXAM_RESULT_URL = "http://192.168.43.129/PwaniApp/PHP/studentExamResult.php";
    public static final String STUDENT_TRANSCRIPT_URL = "http://192.168.43.129/PwaniApp/PHP/studentTranscript.php";
    public static final String STUDENT_EXAM_TIME_TABLE_URL = "http://192.168.43.129/PwaniApp/PHP/studentExamTimeTable.php";
    public static final String STUDENT_TEACHING_TIME_TABLE_URL = "http://192.168.43.129/PwaniApp/PHP/studentTeachingTimeTable.php";

    //Keys for admission and password as defined in our $_POST['key'] in login.php
    public static final String KEY_ADMISSION = "admission_no";
    public static final String KEY_PASSWORD = "password";

    //Keys for admission as defined in our $_POST['key']
    public static final String KEY_ADM = "admNo";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Shared Preferences

    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "pwaniApp";

    //This would be used to store the values of current logged in user
    public static final String ADMISSION_SHARED_PREF = "admission_no";

    public static final String FULL_NAME_SHARED_PREF = "full_name";
    public static final String COURSE_SHARED_PREF = "course";
    public static final String FULL_REG_SHARED_PREF = "full_reg";
    public static final String ISSUE_DATE_SHARED_PREF = "date_of_issue";
    public static final String IMAGE_URL_SHARED_PREF = "student_image";
    public static final String REG_NO_SHARED_PREF = "reg-no";

    //We will use this to store the boolean in shared preference to track user is logged in or not
    public static final String LOGGED_IN_SHARED_PREF = "logged_in";
}
