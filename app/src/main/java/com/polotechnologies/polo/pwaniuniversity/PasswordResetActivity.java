package com.polotechnologies.polo.pwaniuniversity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PasswordResetActivity extends AppCompatActivity {

    TextInputEditText mNewPassword;
    TextInputEditText mConfirmPassword;
    Button mResetPassword;

    /*Boolean for checking if Edit Text for new Password and confirm Password are Empty*/
    Boolean mCheckEditText = false;

    /*Boolean for checking if Edit Text for new Password and confirm Password are Same*/
    Boolean mCheckPasswordsSame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mNewPassword = (TextInputEditText) findViewById(R.id.newPassword);
        mConfirmPassword = (TextInputEditText) findViewById(R.id.confirmPassword);
        mResetPassword = (Button) findViewById(R.id.button_reset_password);

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        /*Get the Email amd Password*/
        String newPassword = mNewPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        /*Checks if Edit Text for Email and Password are Empty*/
        CheckEditTextIsEmptyOrNot(newPassword, confirmPassword);

        if (!mCheckEditText) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            /*Stops method from executing further*/
            return;
        }

        /*Checks if Edit Text for Email and Password are the same*/
        CheckPasswordMatch(newPassword, confirmPassword);

        if (!mCheckPasswordsSame) {
            Toast.makeText(this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
            /*Stops method from executing further*/
            return;
        }

        Toast.makeText(this, "You can now reset Password", Toast.LENGTH_SHORT).show();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                            //Starting profile activity
                            Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(PasswordResetActivity.this, "Invalid Admission Number or password", Toast.LENGTH_LONG).show();
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
                params.put(Config.KEY_ADMISSION, "");
                params.put(Config.KEY_PASSWORD, "");

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void CheckEditTextIsEmptyOrNot(String newPass, String confPass) {

        // Checking whether EditText value is empty or not.
        mCheckEditText = !TextUtils.isEmpty(newPass) && !TextUtils.isEmpty(confPass);
    }

    private void CheckPasswordMatch(String newPassword, String confirmPassword) {

        mCheckPasswordsSame = newPassword.equals(confirmPassword);
    }


}
