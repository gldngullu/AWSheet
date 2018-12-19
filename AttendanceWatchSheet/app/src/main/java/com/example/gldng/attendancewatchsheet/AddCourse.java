package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class AddCourse extends Fragment {

    TextView courseName;
    TextView courseCode;
    TextView scheduleText1,scheduleText2;
    Button addScheduleButton;
    TextView addScheduleText;
    TextView semesterTextBox;
    Switch notification;
    Button createButton;
    Button cancelButton;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment, container, false);
        return view;

    }




}