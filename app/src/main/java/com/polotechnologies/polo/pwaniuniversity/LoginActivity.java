package com.polotechnologies.polo.pwaniuniversity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    TextView mForgotPassword;
    Context context = this;
    //Boolean for checking if Edit Text for new Password and confirm Password are Empty
    Boolean mCheckEditText = false;
    //Defining views
    private TextInputEditText mInputAdmissionNumber;
    private TextInputEditText mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.button_login);

        mInputAdmissionNumber = (TextInputEditText) findViewById(R.id.loginAdmission);
        mInputPassword = (TextInputEditText) findViewById(R.id.loginPassword);

        mForgotPassword = (TextView) findViewById(R.id.text_forgot_password);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        //Getting values from edit texts
        final String admissionNumber = mInputAdmissionNumber.getText().toString().trim().toUpperCase();
        final String password = mInputPassword.getText().toString().trim();

        /*Checks if Edit Text for Email and Password are Empty*/
        CheckEditTextIsEmptyOrNot(admissionNumber, password);

        if (!mCheckEditText) {
            Toast.makeText(this, "Please Enter Your Admission and Password", Toast.LENGTH_SHORT).show();
            //Stops method from executing further
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equals(Config.LOGIN_SUCCESS)) {
                            //Creating a shared preferences
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGED_IN_SHARED_PREF, true);
                            editor.putString(Config.ADMISSION_SHARED_PREF, admissionNumber);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //If the server response is failure
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Invalid Admission Number or Password", Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_ADMISSION, admissionNumber);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void CheckEditTextIsEmptyOrNot(String adm, String pass) {

        // Checking whether EditText value is empty or not.
        mCheckEditText = !TextUtils.isEmpty(adm) && !TextUtils.isEmpty(pass);
    }
}
