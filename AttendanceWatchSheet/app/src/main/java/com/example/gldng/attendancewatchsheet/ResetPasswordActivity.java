package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity implements NavigationMenuActions{
private EditText etNewPassword;
private EditText etConfirmNewPassword;
private Button btChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword= findViewById(R.id.etConfirmNewPassword);
        btChangePassword=findViewById(R.id.btChangePassword);
        btChangePassword.setEnabled(false);
        etNewPassword.addTextChangedListener(resetPasswordTextWatcher);
        etConfirmNewPassword.addTextChangedListener(resetPasswordTextWatcher);

        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                goLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String passwordinput=etNewPassword.getText().toString();
                String confirmpasswordinput=etConfirmNewPassword.getText().toString();
                if(0!=passwordinput.compareTo(confirmpasswordinput)){
                    Toast.makeText(ResetPasswordActivity.this,"Confirmation Password is wrong",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ResetPasswordActivity.this, "Your password has been changed", Toast.LENGTH_LONG).show();
                    ResetPasswordActivity.this.startActivity(goLogin);

                }

            }
        });
    }

    private TextWatcher resetPasswordTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String newpasswordinput=etNewPassword.getText().toString().trim();
            String confirmnewpasswordinput=etConfirmNewPassword.getText().toString().trim();



            btChangePassword.setEnabled(!newpasswordinput.isEmpty() && !confirmnewpasswordinput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void navBarBuilder() {
        // look into it
    }
}
