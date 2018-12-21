package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectCourse extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
    }

    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                intent = new Intent(SelectCourse.this,HomeActivityInstructor.class);
                break;
            case R.id.navigation_courses:
                intent = new Intent(SelectCourse.this,Courses_Instructor.class);
                break;
            case R.id.navigation_attend:
                intent = new Intent(SelectCourse.this,AttandanceSelectorActivity.class);
                break;
            case R.id.navigation_calendar:
                //intent = new Intent(HomeActivityInstructor.this,.class);
                break;
        }
        startActivity(intent);
    }

}
