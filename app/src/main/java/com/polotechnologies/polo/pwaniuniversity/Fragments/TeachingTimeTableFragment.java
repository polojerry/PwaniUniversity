package com.polotechnologies.polo.pwaniuniversity.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.polotechnologies.polo.pwaniuniversity.Adapters.TeachingTimeTableAdapter;
import com.polotechnologies.polo.pwaniuniversity.Config;
import com.polotechnologies.polo.pwaniuniversity.Data.TeachingTimeTable;
import com.polotechnologies.polo.pwaniuniversity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeachingTimeTableFragment extends Fragment implements TeachingTimeTableAdapter.TeachingTimeTableClickListener {

    public static String admissionNumber;
    public static JSONArray arrayResponse;
    public final String TAG_ARRAY_LIST = "resultArray";
    private RecyclerView recyclerView;
    private TeachingTimeTableAdapter adapter;
    private List<TeachingTimeTable> teachingTimeTableLists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teaching_time_table, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Teaching TimeTable");

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.teachingTimeTableRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TeachingTimeTableAdapter(getActivity().getApplicationContext(), this);

        teachingTimeTableLists = new ArrayList<>();

        if (savedInstanceState != null) {
            String array = savedInstanceState.getString(TAG_ARRAY_LIST);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                    String semester = jsonObject.getString("semester");
                    String year = jsonObject.getString("year");
                    String time_table_url = jsonObject.getString("timetable_url");

                    TeachingTimeTable teachingTimeTable = new TeachingTimeTable(
                            semester,
                            year,
                            time_table_url
                    );

                    teachingTimeTableLists.add(teachingTimeTable);
                }
                adapter.swapList(teachingTimeTableLists);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            loadTeachingTimeTable();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String myArray = String.valueOf(arrayResponse);

        outState.putString(TAG_ARRAY_LIST, myArray);
    }

    private void loadTeachingTimeTable() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading TimeTable...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUDENT_TEACHING_TIME_TABLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            arrayResponse = jsonArray;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(i);

                                String semester = jsonObject.getString("semester");
                                String year = jsonObject.getString("year");
                                String time_table_url = jsonObject.getString("timetable_url");

                                TeachingTimeTable teachingTimeTable = new TeachingTimeTable(
                                        semester,
                                        year,
                                        time_table_url
                                );

                                teachingTimeTableLists.add(teachingTimeTable);
                            }
                            adapter.swapList(teachingTimeTableLists);
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
    public void onTeachingTimeTableClick(String semester, String year, String url) {

        String TAG = "time_table_view";
        String mSemester = semester;
        String mYear = year;
        String mTeachingTimeTableUrl = url;

        android.content.Intent browserIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(mTeachingTimeTableUrl));
        startActivity(browserIntent);

    }
}
