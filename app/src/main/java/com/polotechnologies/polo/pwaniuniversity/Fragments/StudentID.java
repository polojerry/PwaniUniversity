package com.polotechnologies.polo.pwaniuniversity.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import com.polotechnologies.polo.pwaniuniversity.Config;
import com.polotechnologies.polo.pwaniuniversity.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


public class StudentID extends Fragment {

    public static TextView mStudentFullName;
    public static TextView mStudentCourse;
    public static TextView mStudentRegistrationNumberFull;
    public static TextView mStudentIdDateOfIssue;
    public static TextView mStudentRegistrationNumber;

    public static ImageView mStudentPassport;
    public static ImageView mStudentBarCode;

    public static String admissionNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_id, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Student ID");

        mStudentFullName = (TextView) getActivity().findViewById(R.id.student_id_full_name);
        mStudentCourse = (TextView) getActivity().findViewById(R.id.student_id_course);
        mStudentRegistrationNumberFull = (TextView) getActivity().findViewById(R.id.student_id_reg_number);
        mStudentIdDateOfIssue = (TextView) getActivity().findViewById(R.id.student_id_date_of_issue);
        mStudentRegistrationNumber = (TextView) getActivity().findViewById(R.id.student_id_reg_number_short);

        mStudentPassport = (ImageView) getActivity().findViewById(R.id.student_id_passport);
        mStudentBarCode = (ImageView) getActivity().findViewById(R.id.student_id_barcode);

        //Fetching admissionNumber from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF, "Not Available");

        getStudentID();

    }

    public void getStudentID() {
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

                            mStudentFullName.setText(sStudentFullName);
                            mStudentCourse.setText(sStudentCourse);
                            mStudentRegistrationNumberFull.setText(sStudentRegistrationNumberFull);
                            mStudentIdDateOfIssue.setText(sStudentIdDateOfIssue);
                            mStudentRegistrationNumber.setText(sStudentRegistrationNumber);

                            Picasso.get()
                                    .load(sStudentImageUrl)
                                    .into(mStudentPassport);


                            try {

                                Bitmap bitmap = encodeAsBitmap(sStudentRegistrationNumber, BarcodeFormat.CODE_93, 500, 70);
                                mStudentBarCode.setImageBitmap(bitmap);

                            } catch (WriterException e) {
                                e.printStackTrace();
                            }


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
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);
    }

    public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int imgWidth, int imgHeight) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contents, format, imgWidth, imgHeight, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
