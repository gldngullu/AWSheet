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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddInstructor extends Fragment {


    TextView instructorName;
    TextView instructorEmail;
    TextView instructorSurname;
    Button createButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_instructor_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        createButton = getView().findViewById(R.id.doneButtonInstructor);
        instructorName = getView().findViewById(R.id.instructorName);
        instructorSurname = getView().findViewById(R.id.instructorSurname);
        instructorEmail = getView().findViewById(R.id.email);
        final String instructorNameText = (String) instructorName.getText();
        final String instructorSurnameText = (String) instructorSurname.getText();
        final String instructorEmailText = (String) instructorEmail.getText();
        final String password = Double.toString(Math.floor(100000 + Math.random() * 900000));

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int success = jsonResponse.getInt("success");
                            if (success == 1) {
                                Log.d("successful", "quary succesful");
                                Toast.makeText(AddInstructor.this.getContext(), "quary succesful", Toast.LENGTH_LONG).show();
                            } else {
                                Log.d("unsuccessful", "quary unsuccesful");
                                Toast.makeText(AddInstructor.this.getContext(), "quary unsuccesful", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                AddUserRequest addUserRequest = new AddUserRequest(instructorEmailText, instructorNameText, instructorSurnameText,  password, responselistener);
                RequestQueue queue = Volley.newRequestQueue(AddInstructor.this.getContext());
                queue.add(addUserRequest);

                //Toast.makeText(RegisterActivity.this, "Your registration request has been sent.", Toast.LENGTH_LONG).show();



        }
    });
}





}