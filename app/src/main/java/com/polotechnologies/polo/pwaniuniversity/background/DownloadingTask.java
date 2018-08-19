package com.polotechnologies.polo.pwaniuniversity.background;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.polotechnologies.polo.pwaniuniversity.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DownloadingTask {

    public static final String ACTION_DOWNLOAD_ID_DETAILS = "download-id-details";
    public static SharedPreferences sharedPreferences;
    public static String admissionNumber;

    public static void executeAction(Context context, String action) {
        //Creating a shared preference
        sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF, "Not Available");


        if (ACTION_DOWNLOAD_ID_DETAILS.equals(action)) {
            getStudentID(context);
        }

    }

    private static void getStudentID(final Context context) {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUDENT_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String sStudentFullName = jsonObject.getString("user_full_name");
                            String sStudentCourse = jsonObject.getString("user_course");
                            String sStudentRegistrationNumberFull = jsonObject.getString("user_admission_no");
                            String sStudentIdDateOfIssue = jsonObject.getString("user_date_of_issue");
                            String sStudentImageUrl = jsonObject.getString("user_passport");
                            String sStudentRegistrationNumber = sStudentRegistrationNumberFull.replace("/", "");

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding the values
                            editor.putString(Config.FULL_NAME_SHARED_PREF, sStudentFullName);
                            editor.putString(Config.COURSE_SHARED_PREF, sStudentCourse);
                            editor.putString(Config.FULL_REG_SHARED_PREF, sStudentRegistrationNumberFull);
                            editor.putString(Config.ISSUE_DATE_SHARED_PREF, sStudentIdDateOfIssue);
                            editor.putString(Config.IMAGE_URL_SHARED_PREF, sStudentImageUrl);
                            editor.putString(Config.REG_NO_SHARED_PREF, sStudentRegistrationNumber);

                            //Saving values to editor
                            editor.commit();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_ADM, admissionNumber);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
