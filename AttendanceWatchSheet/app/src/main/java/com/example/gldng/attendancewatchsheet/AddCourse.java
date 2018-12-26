package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCourse extends Fragment {

    EditText courseName;
    EditText courseCode;
    String weeklySchedule;
    Button addScheduleButton;
    Spinner spinnerForDays,spinnerForHours;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        spinnerForDays = (Spinner) getView().findViewById(R.id.SpinnerForDays);
        spinnerForHours = (Spinner) getView().findViewById(R.id.SpinnerForHours);
        ArrayAdapter<CharSequence> arrayAdapterForDays =
                ArrayAdapter.createFromResource(this.getContext(), R.array.Days, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForHours =
                ArrayAdapter.createFromResource(this.getContext(), R.array.Hours, android.R.layout.simple_spinner_item);
        spinnerForDays.setAdapter(arrayAdapterForDays);
        spinnerForHours.setAdapter(arrayAdapterForHours);
        courseName = getView().findViewById(R.id.courseName);
        courseCode = getView().findViewById(R.id.courseCode);
        weeklySchedule = getWeeklySchedule();

    }

    public String getWeeklySchedule(){
        String weeklySchedule= "";
        StringBuilder sb = new StringBuilder();
        sb.append(weeklySchedule);
        for (String string : AdminCoursePage.StringsForListView) {
            String[] splitStr = string.split("\\s+");
            switch (splitStr[0]){
                case "Monday":
                    sb.append("M");
                case "Tuesday":
                    sb.append("T");
                case "Wednesday":
                    sb.append("W");
                case "Thursday":
                    sb.append("Th");
                case "Friday":
                    sb.append("F");
            }
            sb.append(splitStr[1]);
        }
        return weeklySchedule;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment, container, false);
        return view;

    }

    public void addNewCourse(){
        final String courseNameText = courseName.getText().toString();
        final String courseCodeText = courseCode.getText().toString();
        final String weeklyScheduleText = weeklySchedule;
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

        AddCourseRequest addCourseRequest = new AddCourseRequest( courseNameText, courseCodeText,weeklyScheduleText, responselistener);
        RequestQueue queue = Volley.newRequestQueue(AddCourse.this.getContext());
        queue.add(addCourseRequest);
    }

    }




