package com.polotechnologies.polo.pwaniuniversity.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.polotechnologies.polo.pwaniuniversity.Config;
import com.polotechnologies.polo.pwaniuniversity.R;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;
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

    public static final String TAG_FULL_NAME = "full_name";
    public static final String TAG_COURSE = "course";
    public static final String TAG_REGISTRATION_FULL = "reg_full";
    public static final String TAG_DATE_OF_ISSUE = "issue_date";
    public static final String TAG_IMAGE_URL = "image_url";
    public static final String TAG_REGISTRATION_NUMBER = "reg_num";
    public static Context mContext;
    public static String StudentFullName, StudentCourse, StudentRegistrationNumberFull,
            StudentIdDateOfIssue, StudentImageUrl, StudentRegistrationNumber;

    private static void setIdDetails(String sStudentFullName, String sStudentCourse, String sStudentRegistrationNumberFull, String sStudentIdDateOfIssue, String sStudentImageUrl, String sStudentRegistrationNumber) {
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
    }

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

        mContext = this.getActivity();

        //Fetching admissionNumber from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF, "Not Available");

        if (savedInstanceState != null) {
            //Get values from savedInstantState
            StudentFullName = savedInstanceState.getString(TAG_FULL_NAME);
            StudentCourse = savedInstanceState.getString(TAG_COURSE);
            StudentRegistrationNumberFull = savedInstanceState.getString(TAG_REGISTRATION_FULL);
            StudentIdDateOfIssue = savedInstanceState.getString(TAG_DATE_OF_ISSUE);
            StudentImageUrl = savedInstanceState.getString(TAG_IMAGE_URL);
            StudentRegistrationNumber = savedInstanceState.getString(TAG_REGISTRATION_NUMBER);

            setIdDetails(StudentFullName, StudentCourse, StudentRegistrationNumberFull,
                    StudentIdDateOfIssue, StudentImageUrl, StudentRegistrationNumber);
        } else {
            //getStudentID();

            //Get values from sharedPreference
            StudentFullName = sharedPreferences.getString(Config.FULL_NAME_SHARED_PREF, "Not Available");
            StudentCourse = sharedPreferences.getString(Config.COURSE_SHARED_PREF, "Not Available");
            StudentRegistrationNumberFull = sharedPreferences.getString(Config.FULL_REG_SHARED_PREF, "Not Available");
            StudentIdDateOfIssue = sharedPreferences.getString(Config.ISSUE_DATE_SHARED_PREF, "Not Available");
            StudentImageUrl = sharedPreferences.getString(Config.IMAGE_URL_SHARED_PREF, "Not Available");
            StudentRegistrationNumber = sharedPreferences.getString(Config.REG_NO_SHARED_PREF, "Not Available");

            setIdDetails(StudentFullName, StudentCourse, StudentRegistrationNumberFull,
                    StudentIdDateOfIssue, StudentImageUrl, StudentRegistrationNumber);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG_FULL_NAME, StudentFullName);
        outState.putString(TAG_COURSE, StudentCourse);
        outState.putString(TAG_REGISTRATION_FULL, StudentRegistrationNumberFull);
        outState.putString(TAG_DATE_OF_ISSUE, StudentIdDateOfIssue);
        outState.putString(TAG_IMAGE_URL, StudentImageUrl);
        outState.putString(TAG_REGISTRATION_NUMBER, StudentRegistrationNumber);

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
