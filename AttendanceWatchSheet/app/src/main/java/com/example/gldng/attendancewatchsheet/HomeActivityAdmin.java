package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.qrcode.QRCodeReader;

public class HomeActivityAdmin extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_admin);


        TextView textView = (TextView) findViewById(R.id.userEmail);
        textView.setText(getIntent().getStringExtra("email"));

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
                        return false;
                    case R.id.navigation_courses:
                        intent = new Intent(HomeActivityAdmin.this,AdminCoursePage.class);
                        break;
                    case R.id.navigation_member:
                        intent = new Intent(HomeActivityAdmin.this,AddUser.class);
                        break;
                }
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return true;
            }
        });
    }

}
