package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.zxing.qrcode.QRCodeReader;

public class HomeActivityAdmin extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_admin);
    }

    @Override
    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                break;
            case R.id.navigation_courses:
                intent = new Intent(HomeActivityAdmin.this,AdminCoursePage.class);
                break;
            case R.id.navigation_member:
                intent = new Intent(HomeActivityAdmin.this,AddUser.class);
                break;
        }
        startActivity(intent);
    }

}
