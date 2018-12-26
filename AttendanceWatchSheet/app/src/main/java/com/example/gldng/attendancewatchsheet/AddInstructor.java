package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddInstructor extends Fragment {


    EditText instructorName;
    EditText instructorEmail;
    EditText instructorSurname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_instructor_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        instructorName = getView().findViewById(R.id.instructorName);
        instructorSurname = getView().findViewById(R.id.instructorSurname);
        instructorEmail = getView().findViewById(R.id.instructorEmail);
    }

    public void addNewInstructor() {
        final String instructorSurnameText = instructorSurname.getText().toString();
        final String instructorNameText = instructorName.getText().toString();
        final String instructorEmailText = instructorEmail.getText().toString() + "@isikun.edu.tr";
        final String password = Integer.toString(((Double) (Math.floor(100000 + Math.random() * 900000))).intValue());
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
        AddUserRequest addUserRequest = new AddUserRequest(instructorEmailText, instructorNameText, instructorSurnameText, password, 2, responselistener);
        RequestQueue queue = Volley.newRequestQueue(AddInstructor.this.getContext());
        queue.add(addUserRequest);
    }
}

