package com.polotechnologies.polo.pwaniuniversity.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.polotechnologies.polo.pwaniuniversity.Adapters.ExamResultAdapter;
import com.polotechnologies.polo.pwaniuniversity.Adapters.ExamResultAdapter.ExamResultClickListener;
import com.polotechnologies.polo.pwaniuniversity.Config;
import com.polotechnologies.polo.pwaniuniversity.Data.ExamResult;
import com.polotechnologies.polo.pwaniuniversity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentExamResult extends Fragment implements ExamResultClickListener {

    public static String admissionNumber;
    public static JSONArray arrayResponse;
    public final String TAG_ARRAY_LIST = "resultArray";
    private RecyclerView recyclerView;
    private ExamResultAdapter adapter;
    private List<ExamResult> examResults;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_exam_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Exam Results");

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.examResultRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamResultAdapter(getActivity().getApplicationContext(), this);

        examResults = new ArrayList<>();

        //Fetching admissionNumber from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF, "Not Available");

        if (savedInstanceState != null) {
            String array = savedInstanceState.getString(TAG_ARRAY_LIST);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                    String academicYear = jsonObject.getString("academic_year");
                    String studyStage = jsonObject.getString("study_stage");
                    String resultPdf = jsonObject.getString("exam_result");

                    ExamResult examResult = new ExamResult(
                            academicYear,
                            studyStage,
                            resultPdf
                    );

                    examResults.add(examResult);
                }
                adapter.swapList(examResults);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            loadExamResults();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String myArray = String.valueOf(arrayResponse);

        outState.putString(TAG_ARRAY_LIST, myArray);
    }

    private void loadExamResults() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Results...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUDENT_EXAM_RESULT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            arrayResponse = jsonArray;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                                String academicYear = jsonObject.getString("academic_year");
                                String studyStage = jsonObject.getString("study_stage");
                                String resultPdf = jsonObject.getString("exam_result");

                                ExamResult examResult = new ExamResult(
                                        academicYear,
                                        studyStage,
                                        resultPdf
                                );

                                examResults.add(examResult);
                            }
                            adapter.swapList(examResults);
                            recyclerView.setAdapter(adapter);

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

    @Override
    public void onExamItemClick(String academic_year, String study_stage, String result_url) {
        String TAG = "exam_view";
        String mAcademicYear = academic_year;
        String mStudyStage = study_stage;
        String mResultUrl = result_url;

        android.content.Intent browserIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(mResultUrl));
        startActivity(browserIntent);

        Fragment fragment = new ExamResultView();
        Bundle bundle = new Bundle();
        bundle.putString("Exam Url", mResultUrl);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.examContentMain, fragment).addToBackStack(TAG);
        fragmentTransaction.commit();
    }
}
