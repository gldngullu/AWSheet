package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddAdmin extends Fragment {

    TextView adminName;
    TextView adminEmail;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adminName = getView().findViewById(R.id.adminName);
        String adminNameText = (String) adminName.getText();
        adminEmail = getView().findViewById(R.id.email);
        String instructorEmailText = (String) adminEmail.getText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_admin_fragment, container, false);
        return view;

    }


}
