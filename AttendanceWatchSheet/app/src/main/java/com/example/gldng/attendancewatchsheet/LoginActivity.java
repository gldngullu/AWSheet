package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

            btLogin.setEnabled(!mailinput.isEmpty() && !passwordinput.isEmpty());

            //login buton on click
            final int userType = 3;
            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch(userType){
                        case 1:
                            intent = new Intent(LoginActivity.this,HomeActivityAdmin.class);
                            break;
                        case 2:
                            intent = new Intent(LoginActivity.this,HomeActivityInstructor.class);
                            break;
                        case 3:
                            intent = new Intent(LoginActivity.this,HomeActivity.class);
                            break;
                    }
                    startActivity(intent);

                }
            });
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
}
