package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {
private EditText etForgotMail;
private Button btForgotSubmit;
private EditText etForgotSecretQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etForgotMail=findViewById(R.id.etForgotMail);
        etForgotSecretQ=findViewById(R.id.etForgotSecretQ);
        btForgotSubmit=findViewById(R.id.btForgotSubmit);
        btForgotSubmit.setEnabled(false);
        etForgotMail.addTextChangedListener(ForgotTextWatcher);
        etForgotSecretQ.addTextChangedListener(ForgotTextWatcher);

        btForgotSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = etForgotMail.getText().toString();
                String secretq = etForgotSecretQ.getText().toString();

                Response.Listener<String> response2Listener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int success= jsonResponse.getInt("success");

                            if(success==1){
                                Intent changepassintent = new Intent(ForgotPasswordActivity.this,ResetPasswordActivity.class);
                                changepassintent.putExtra("mailAdress",etForgotMail.getText().toString());
                                ForgotPasswordActivity.this.startActivity(changepassintent);
                                finish();



                            }else{

                                Toast.makeText(ForgotPasswordActivity.this,"Invalid information",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(ForgotPasswordActivity.this,"JSON Exception",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                };
                ForgotPasswordRequest forgotRequest= new ForgotPasswordRequest(mail,secretq,response2Listener);
                RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
                queue.add(forgotRequest);




            }
        });
    }

    private TextWatcher ForgotTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String mailinput=etForgotMail.getText().toString().trim();
            String secretqinput=etForgotSecretQ.getText().toString().trim();

            btForgotSubmit.setEnabled(!mailinput.isEmpty() && !secretqinput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
