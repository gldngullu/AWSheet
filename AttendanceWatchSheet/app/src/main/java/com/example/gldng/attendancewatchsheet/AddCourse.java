package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment, container, false);
        return view;

    }

    }




