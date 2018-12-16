package com.example.gldng.attendancewatchsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Courses_Student extends AppCompatActivity {
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_courses:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_scan:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_calendar:
                    mTextMessage.setText(R.string.title_home);
                    return true;
            }
            return false;
        }
    };

    Button addcourse;
    Button removecourse;
    ListView course_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses__student);
        addcourse = findViewById(R.id.addcourse);
        removecourse = findViewById(R.id.removecourse);
        course_list = findViewById(R.id.course_list);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        addcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcourse.setVisibility(View.INVISIBLE);
                removecourse.setVisibility(View.INVISIBLE);
                course_list.setVisibility(View.INVISIBLE);
                addCourseStudent(v);


            }
        });



    }

    public void addCourseStudent(View v){





    }




}
