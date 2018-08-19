package com.polotechnologies.polo.pwaniuniversity.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.polotechnologies.polo.pwaniuniversity.Adapters.ExamTranscriptAdapter;
import com.polotechnologies.polo.pwaniuniversity.Config;
import com.polotechnologies.polo.pwaniuniversity.Data.Transcript;
import com.polotechnologies.polo.pwaniuniversity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentExamTranscript extends Fragment implements ExamTranscriptAdapter.ExamTranscriptClickListener {

    public static String admissionNumber;
    public static JSONArray arrayResponse;
    public final String TAG_ARRAY_LIST = "resultArray";
    private RecyclerView recyclerView;
    private ExamTranscriptAdapter adapter;
    private List<Transcript> examTranscripts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_transcript, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Transcript");


        recyclerView = (RecyclerView) getActivity().findViewById(R.id.examTranscriptRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamTranscriptAdapter(getActivity().getApplicationContext(), this);

        examTranscripts = new ArrayList<>();

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
                    String transcriptPdf = jsonObject.getString("transcript");

                    Transcript examTranscript = new Transcript(
                            academicYear,
                            transcriptPdf
                    );

                    examTranscripts.add(examTranscript);
                }
                adapter.swapList(examTranscripts);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            loadExamTranscript();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String myArray = String.valueOf(arrayResponse);
        outState.putString(TAG_ARRAY_LIST, myArray);
    }

    private void loadExamTranscript() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Transcript...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUDENT_TRANSCRIPT_URL,
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
                                String transcriptPdf = jsonObject.getString("transcript");

                                Transcript examTranscript = new Transcript(
                                        academicYear,
                                        transcriptPdf
                                );

                                examTranscripts.add(examTranscript);
                            }
                            adapter.swapList(examTranscripts);
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
    public void onTranscriptItemClick(String academic_year, String transcript_url) {
        String TAG = "transcript_view";
        String mAcademicYear = academic_year;
        String mTranscriptUrl = transcript_url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTranscriptUrl));
        startActivity(browserIntent);

        Fragment fragment = new ExamTranscriptView();
        Bundle bundle = new Bundle();
        bundle.putString("Transcript Url", mTranscriptUrl);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.examContentMain, fragment).addToBackStack(TAG);
        fragmentTransaction.commit();
    }
}
