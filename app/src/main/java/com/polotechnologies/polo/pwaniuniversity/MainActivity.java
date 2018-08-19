package com.polotechnologies.polo.pwaniuniversity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.polotechnologies.polo.pwaniuniversity.background.DownloadingTask;
import com.polotechnologies.polo.pwaniuniversity.background.DownloadingTaskIntentService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GridLayout mGridLayout;
    CardView mCardView;
    TextView mTextView;


    String mFeeBalance;
    String admissionNumber;

    Context mContext = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getFeeBalance();
            }
        }).start();

        mGridLayout = findViewById(R.id.mainGridView);
        mCardView = findViewById(R.id.feeStatusColor);
        mTextView = findViewById(R.id.feeBalance);

        //Fetching admissionNumber from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF,"Not Available");

        Intent startDownloadingService = new Intent(mContext, DownloadingTaskIntentService.class);
        startDownloadingService.setAction(DownloadingTask.ACTION_DOWNLOAD_ID_DETAILS);
        startService(startDownloadingService);

        gridClickEvent(mGridLayout);

    }
    private void getFeeBalance() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUDENT_FINANCE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            mFeeBalance = jsonObject.getString("fee_balance");

                            setFeeBalance(mFeeBalance);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_ADM, admissionNumber);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void gridClickEvent(GridLayout mGridLayout) {
        for (int i = 0; i<mGridLayout.getChildCount(); i++){
            CardView mCardView = (CardView)mGridLayout.getChildAt(i);
            final int index = i;
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (index){
                        case 0:
                            Intent openAdmissionActivity = new Intent(MainActivity.this,AdmissionActivity.class);
                            startActivity(openAdmissionActivity);
                        break;
                        case 1:
                            Intent openFinanceActivity = new Intent(MainActivity.this,FinanceActivity.class);
                            startActivity(openFinanceActivity);
                        break;
                        case 2:
                            Intent openSemesterRegistrationActivity = new Intent(MainActivity.this,SemesterRegistrationActivity.class);
                            startActivity(openSemesterRegistrationActivity);
                            break;
                        case 3:
                            Intent openHostelBookingActivity = new Intent(MainActivity.this,HostelBookingActivity.class);
                            startActivity(openHostelBookingActivity);
                        break;
                        case 4:
                            Intent openUnitRegistrationActivity = new Intent(MainActivity.this,UnitRegistrationActivity.class);
                            startActivity(openUnitRegistrationActivity);
                            break;
                        case 5:
                            Intent openTimeTableActivity = new Intent(MainActivity.this,TimeTableActivity.class);
                            startActivity(openTimeTableActivity);
                            break;
                        case 6:
                            Intent openExamActivity = new Intent(MainActivity.this,ExamActivity.class);
                            startActivity(openExamActivity);
                            break;
                        case 7:
                            Intent openProfileActivity = new Intent(MainActivity.this,ProfileActivity.class);
                            startActivity(openProfileActivity);
                            break;
                    }

                }
            });
        }
    }

    public void setFeeBalance(String feeBalance) {

        mTextView.setText(feeBalance);

        Double balance = Double.valueOf(feeBalance);
        if (balance > 0) {
            mCardView.setCardBackgroundColor(Color.parseColor("#ffff4444"));
        } else {
            mCardView.setCardBackgroundColor(Color.parseColor("#ff669900"));
        }
    }
}
