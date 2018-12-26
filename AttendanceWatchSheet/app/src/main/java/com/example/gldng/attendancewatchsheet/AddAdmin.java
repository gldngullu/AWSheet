package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddAdmin extends Fragment {

    EditText adminName;
    EditText adminEmail;
    EditText adminSurname;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adminName = getView().findViewById(R.id.adminName);
        adminEmail = getView().findViewById(R.id.adminEmail);
        adminSurname = getView().findViewById(R.id.adminSurname);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_admin_fragment, container, false);
        return view;

    }

    public void addNewAdmin(){
        final String adminSurnameText = adminSurname.getText().toString();
        final String adminNameText = adminName.getText().toString();
        final String adminEmailText = adminEmail.getText().toString()+"@isikun.edu.tr";
        final String password = Integer.toString(((Double)(Math.floor(100000 + Math.random() * 900000))).intValue());
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
        AddUserRequest addUserRequest = new AddUserRequest(adminEmailText, adminNameText, adminSurnameText,  password, 3, responselistener);
        RequestQueue queue = Volley.newRequestQueue(AddAdmin.this.getContext());
        queue.add(addUserRequest);
    }


}
