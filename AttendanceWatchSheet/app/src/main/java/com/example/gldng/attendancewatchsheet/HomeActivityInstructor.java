package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeActivityInstructor extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_instructor);
    }

    @Override
    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                break;
            case R.id.navigation_courses:
                intent = new Intent(HomeActivityInstructor.this,Courses_Instructor.class);
                break;
            case R.id.navigation_attend:
                intent = new Intent(HomeActivityInstructor.this,AttandanceSelectorActivity.class);
                break;
             case R.id.navigation_calendar:
                //intent = new Intent(HomeActivityInstructor.this,.class);
                break;
        }
    }
}
