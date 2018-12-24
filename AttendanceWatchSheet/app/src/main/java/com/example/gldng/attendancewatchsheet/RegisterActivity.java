package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText etMail;
    private EditText etName;
    private EditText etSurname;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etSecretQ;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=findViewById(R.id.etName);
        etMail=findViewById(R.id.etMail);
        etSurname=findViewById(R.id.etSurname);
        etPassword=findViewById(R.id.etPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        etSecretQ=findViewById(R.id.etSecretQ);

        btRegister=findViewById(R.id.btRegister);
        btRegister.setEnabled(false);

        etName.addTextChangedListener(registerTextWatcher);
        etSurname.addTextChangedListener(registerTextWatcher);
        etMail.addTextChangedListener(registerTextWatcher);
        etPassword.addTextChangedListener(registerTextWatcher);
        etConfirmPassword.addTextChangedListener(registerTextWatcher);
        etSecretQ.addTextChangedListener(registerTextWatcher);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(RegisterActivity.this,LoginActivity.class);
                String mailinput=etMail.getText().toString();
                String nameinput=etName.getText().toString();
                String surnameinput=etSurname.getText().toString();
                String passwordinput=etPassword.getText().toString();
                String confirmpasswordinput=etConfirmPassword.getText().toString();
                String secretqinput=etSecretQ.getText().toString();
                if(0!=passwordinput.compareTo(confirmpasswordinput)){
                    Toast.makeText(RegisterActivity.this,"Confirmation Password is wrong",Toast.LENGTH_LONG).show();
                }else {
                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse  = new JSONObject(response);
                                int success = jsonResponse.getInt("success");
                                if(success==1){
                                    Log.d("successful","quary succesful");
                                    Toast.makeText(RegisterActivity.this, "Registiration succesful", Toast.LENGTH_LONG).show();
                                }else{
                                    Log.d("unsuccessful","quary unsuccesful");
                                    Toast.makeText(RegisterActivity.this, "Registiration unsuccesful", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(mailinput,nameinput,surnameinput,passwordinput,secretqinput,responselistener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);

                    //Toast.makeText(RegisterActivity.this, "Your registration request has been sent.", Toast.LENGTH_LONG).show();
                    RegisterActivity.this.startActivity(registerintent);
                    finish();


                }
            }
        });

    }

    private TextWatcher registerTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameinput=etName.getText().toString().trim();
            String mailinput=etMail.getText().toString().trim();
            String surnameinput=etSurname.getText().toString().trim();
            String passwordinput=etPassword.getText().toString().trim();
            String confirmpasswordinput=etConfirmPassword.getText().toString().trim();
            String secretqinput=etSecretQ.getText().toString().trim();


                btRegister.setEnabled(!mailinput.isEmpty() && !passwordinput.isEmpty() && !nameinput.isEmpty() && !surnameinput.isEmpty() && !secretqinput.isEmpty() && !confirmpasswordinput.isEmpty());




        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}

