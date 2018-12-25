package com.example.gldng.attendancewatchsheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
private EditText etLoginMail;
private EditText etLoginPassword;
private Button btLogin;
private Button btGoRegister;
private TextView tvForgotPassword;
private Button btBorder;
private String userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginMail=findViewById(R.id.etLoginMail);
        etLoginPassword=findViewById(R.id.etLoginPassword);

        tvForgotPassword=findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassintent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(forgotpassintent);

            }
        });

        btBorder=findViewById(R.id.btBorder);
        btBorder.setEnabled(false);

        btLogin=findViewById(R.id.btLogin);
        btLogin.setEnabled(false);



        btGoRegister=findViewById(R.id.btGoRegister);
        btGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerintent);

            }
        });


       
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             String mail = etLoginMail.getText().toString();
                String pass = etLoginPassword.getText().toString();

                userMail = mail;

                Response.Listener<String> response1Listener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int success= jsonResponse.getInt("success");

                            if(success==1){
                                int usertype = jsonResponse.getInt("usertype");



                                Intent intent = null;
                                switch(usertype){
                                    case 3:
                                        intent = new Intent(LoginActivity.this,HomeActivityAdmin.class);
                                        intent.putExtra("usertype", usertype);
                                        finish();
                                        break;
                                    case 2:
                                        intent = new Intent(LoginActivity.this,HomeActivityInstructor.class);
                                        intent.putExtra("usertype", usertype);
                                        finish();
                                        break;
                                    case 1:
                                        intent = new Intent(LoginActivity.this,HomeActivity.class);
                                        intent.putExtra("usertype", usertype);
                                        finish();
                                        break;
                                }
								intent.putExtra("email",userMail);
                                startActivity(intent);
                            }else{

                                Toast.makeText(LoginActivity.this,"Login really unsuccesful",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this,"Login unsuccesful",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest= new LoginRequest(mail,pass,response1Listener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);



            }
        });


        etLoginMail.addTextChangedListener(loginTextWatcher);
        etLoginPassword.addTextChangedListener(loginTextWatcher);
    }


    private TextWatcher loginTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String mailinput=etLoginMail.getText().toString().trim();
            String passwordinput=etLoginPassword.getText().toString().trim();
            boolean a=isValidEmail(mailinput);

            btLogin.setEnabled(!mailinput.isEmpty() && !passwordinput.isEmpty()&& a);


        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
