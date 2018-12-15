package com.example.gldng.attendancewatchsheet;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class AdminCoursePage extends AppCompatActivity {
Button manualAddCourse;
Button uploadCourseList;
Button manualDelete;
Button setAddDrop;
FrameLayout frameLayout;
FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_page);
        manualAddCourse = findViewById(R.id.manualAddCourse);
        uploadCourseList = findViewById(R.id.uploadCourseList);
        manualDelete = findViewById(R.id.manualDelete);
        setAddDrop = findViewById(R.id.setAddDrop);
        frameLayout = findViewById(R.id.frameLayout);

        manualAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualAddCourse.setVisibility(View.INVISIBLE);
                uploadCourseList.setVisibility(View.INVISIBLE);
                manualDelete.setVisibility(View.INVISIBLE);
            }
        });

        manualDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualAddCourse.setVisibility(View.INVISIBLE);
                uploadCourseList.setVisibility(View.INVISIBLE);
                manualDelete.setVisibility(View.INVISIBLE);
            }
        });

        uploadCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualAddCourse.setVisibility(View.INVISIBLE);
                uploadCourseList.setVisibility(View.INVISIBLE);
                manualDelete.setVisibility(View.INVISIBLE);
            }
        });
    }
}
