package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.qrcode.QRCodeReader;

public class HomeActivity extends AppCompatActivity implements NavigationMenuActions {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity);
    }


    @Override
    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                break;
            case R.id.navigation_courses:
                intent = new Intent(HomeActivity.this,Courses_Student.class);
                break;
            case R.id.navigation_scan:
                intent = new Intent(HomeActivity.this,QRCodeReader.class);
                break;
            case R.id.navigation_calendar:
                //intent = new Intent(HomeActivity.this,.class);
                break;
        }
        startActivity(intent);
    }

    public void navMenuSelection(MenuItem item) {
        
    }
}
