package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
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

public class AddAssistant extends Fragment {


    EditText assistantName;
    EditText assistantEmail;
    EditText assistantSurname;
    String password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_assistant_fragment, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        assistantName = getView().findViewById(R.id.assistantName);
        assistantSurname = getView().findViewById(R.id.assistantSurname);
        assistantEmail = getView().findViewById(R.id.assistantEmail);

    }

    public  String getPassword() {
        return password;
    }

    public void addNewAssistant(){
            final String assistantSurnameText = assistantSurname.getText().toString();
            final String assistantNameText = assistantName.getText().toString();
            final String assistantEmailText = assistantEmail.getText().toString()+"@isikun.edu.tr";
             password = Integer.toString(((Double)(Math.floor(100000 + Math.random() * 900000))).intValue());
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
            AddUserRequest addUserRequest = new AddUserRequest(assistantEmailText, assistantNameText, assistantSurnameText,  password, 4, responselistener);
            RequestQueue queue = Volley.newRequestQueue(AddAssistant.this.getContext());
            queue.add(addUserRequest);
        }





}