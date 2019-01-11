package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RemoveCourse extends Fragment {

    private JSONArray resultCourseName;
    private ArrayList<String> Coursenames;
    private Spinner courseSpinner;
    private String CourseNamesSpinnerURL = "http://awsheet.cf/connect/reallyAllCourses.php";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Coursenames = new ArrayList<>();
        courseSpinner =  (Spinner) getView().findViewById(R.id.courseSpinner);
        getDataToSpinner(CourseNamesSpinnerURL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remove_course_fragment, container, false);
        return view;

    }

    private void getDataToSpinner(String URL) {
        StringRequest stringRequest = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultCourseName = j.getJSONArray("result");
                            loadCourseName(resultCourseName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }

    private void loadCourseName(JSONArray j) {
        System.out.println(j.toString());
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                Coursenames.add(json.getString("coursecode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        courseSpinner.setAdapter(new ArrayAdapter<String>(RemoveCourse.this.getContext(), android.R.layout.simple_spinner_dropdown_item, Coursenames));
    }

    public void removeCourse(){
        final String courseCodeText = courseSpinner.getSelectedItem().toString();
        Response.Listener<String> responselistener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RemoveCourseRequest removeCourseRequest = new RemoveCourseRequest( courseCodeText, responselistener);
        RequestQueue queue = Volley.newRequestQueue(RemoveCourse.this.getContext());
        queue.add(removeCourseRequest);
    }

}