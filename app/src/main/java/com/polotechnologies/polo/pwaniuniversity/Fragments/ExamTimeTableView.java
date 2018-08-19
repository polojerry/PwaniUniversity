package com.polotechnologies.polo.pwaniuniversity.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.polotechnologies.polo.pwaniuniversity.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ExamTimeTableView extends Fragment {

    PDFView mPdfView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_exam_time_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Exam TimeTable");

        mPdfView = (PDFView) getActivity().findViewById(R.id.pdfViewExamTimeTable);

        Bundle examTimeTable = getArguments();
        String timeTableUrl = examTimeTable.getString("TimeTable Url");

        new RetrievePDFStream().execute(timeTableUrl);

    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            mPdfView.fromStream(inputStream).load();
        }
    }

}
