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

public class AddAssistant extends Fragment {


    TextView instructorName;
    TextView instructorEmail;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        instructorName = getView().findViewById(R.id.instructorName);
        String instructorNameText = (String) instructorName.getText();
        instructorEmail = getView().findViewById(R.id.email);
        String instructorEmailText = (String) instructorEmail.getText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_assistant_fragment, container, false);
        return view;

    }


}