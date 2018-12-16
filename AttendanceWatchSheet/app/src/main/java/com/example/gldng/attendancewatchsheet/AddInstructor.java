package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddInstructor extends Fragment {

    Spinner spinner;
    TextView instructorName;
    TextView instructorEmail;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spinner spinner =  (Spinner) getView().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.Departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        instructorName = getView().findViewById(R.id.instructorName);
        String instructorNameText = (String) instructorName.getText();
        instructorEmail = getView().findViewById(R.id.email);
        String instructorEmailText = (String) instructorEmail.getText();
        String deparment = spinner.getSelectedItem().toString();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_instructor_fragment, container, false);
        return view;

    }




}