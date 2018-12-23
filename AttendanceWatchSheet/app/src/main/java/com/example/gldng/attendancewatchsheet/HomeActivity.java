package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.qrcode.QRCodeReader;

public class HomeActivity extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity);

        navBarBuilder();
    }

    @Override
    public void navBarBuilder(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_courses:
                        intent = new Intent(HomeActivity.this,Courses_Student.class);
                        break;
                    case R.id.navigation_scan:
                        intent = new Intent(HomeActivity.this,AttandanceQrreaderActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(HomeActivity.this,HomeActivity.class);
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
    }







}
