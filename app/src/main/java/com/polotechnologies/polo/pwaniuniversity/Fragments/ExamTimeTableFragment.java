package com.polotechnologies.polo.pwaniuniversity.Fragments;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.polotechnologies.polo.pwaniuniversity.Adapters.ExamTimeTableAdapter;
import com.polotechnologies.polo.pwaniuniversity.Config;
import com.polotechnologies.polo.pwaniuniversity.Data.ExamTimeTable;
import com.polotechnologies.polo.pwaniuniversity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExamTimeTableFragment extends Fragment implements ExamTimeTableAdapter.ExamTimeTableClickListener {

    public static String admissionNumber;
    public static JSONArray arrayResponse;
    public final String TAG_ARRAY_LIST = "resultArray";
    private RecyclerView recyclerView;
    private ExamTimeTableAdapter adapter;
    private List<ExamTimeTable> examTimeTableLists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_time_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Exam TimeTable");

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.examTimeTableRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamTimeTableAdapter(this, getActivity().getApplicationContext());

        examTimeTableLists = new ArrayList<>();

        if (savedInstanceState != null) {
            String array = savedInstanceState.getString(TAG_ARRAY_LIST);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                    String comment = jsonObject.getString("comment");
                    String semester = jsonObject.getString("semester");
                    String year = jsonObject.getString("year");
                    String time_table_url = jsonObject.getString("timetable_url");

                    ExamTimeTable examTimeTable = new ExamTimeTable(
                            comment,
                            semester,
                            year,
                            time_table_url
                    );

                    examTimeTableLists.add(examTimeTable);
                }
                adapter.swapList(examTimeTableLists);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            loadExamTimeTable();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String myArray = String.valueOf(arrayResponse);

        outState.putString(TAG_ARRAY_LIST, myArray);
    }

    private void loadExamTimeTable() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading TimeTable...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUDENT_EXAM_TIME_TABLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            arrayResponse = jsonArray;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                                String comment = jsonObject.getString("comment");
                                String semester = jsonObject.getString("semester");
                                String year = jsonObject.getString("year");
                                String time_table_url = jsonObject.getString("timetable_url");

                                ExamTimeTable examTimeTable = new ExamTimeTable(
                                        comment,
                                        semester,
                                        year,
                                        time_table_url
                                );

                                examTimeTableLists.add(examTimeTable);
                            }
                            adapter.swapList(examTimeTableLists);
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
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onExamTimeTableClick(String comment, String semester, String year, String url) {

        String TAG = "exam_time_table_view";

        String mComment = comment;
        String mSemester = semester;
        String mYear = year;
        String mExamTimeTableUrl = url;

        android.content.Intent browserIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(mExamTimeTableUrl));
        startActivity(browserIntent);

        Fragment fragment = new ExamTimeTableView();
        Bundle bundle = new Bundle();
        bundle.putString("TimeTable Url", mExamTimeTableUrl);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.timeTableContentMain, fragment).addToBackStack(TAG);
        fragmentTransaction.commit();

    }
}
