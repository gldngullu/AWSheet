package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Courses_Instructor extends AppCompatActivity implements NavigationMenuActions{



    private ListView course_list;
    private Button addCoursebtn;
    private Button removeCoursebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses__instructor);
        addCoursebtn=(Button)findViewById(R.id.addcourse);
        removeCoursebtn=(Button)findViewById(R.id.removecourse);
        course_list=(ListView) findViewById(R.id.course_list);
        navBarBuilder();



        addCoursebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CourseAddition = new Intent(Courses_Instructor.this,SelectCourse.class);
                CourseAddition.putExtra("email",getIntent().getStringExtra("email"));
                Courses_Instructor.this.startActivity(CourseAddition);
            }
        });

        removeCoursebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CourseCancel = new Intent(Courses_Instructor.this,HomeActivityInstructor.class);
                CourseCancel.putExtra("email",getIntent().getStringExtra("email"));
                Courses_Instructor.this.startActivity(CourseCancel);
            }
        });



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
                        intent = new Intent(Courses_Instructor.this,HomeActivityInstructor.class);
                        break;
                    case R.id.navigation_courses_instructor:
                        return true;
                    case R.id.navigation_attend:
                        intent = new Intent(Courses_Instructor.this,AttandanceSelectorActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(Courses_Instructor.this,HomeActivityInstructor.class);
                        break;
                }
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return false;
            }
        });
    }
}
