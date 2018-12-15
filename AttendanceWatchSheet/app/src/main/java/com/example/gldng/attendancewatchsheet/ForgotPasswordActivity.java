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
                Intent changepassintent = new Intent(ForgotPasswordActivity.this,ResetPasswordActivity.class);
                changepassintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ForgotPasswordActivity.this.startActivity(changepassintent);
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
