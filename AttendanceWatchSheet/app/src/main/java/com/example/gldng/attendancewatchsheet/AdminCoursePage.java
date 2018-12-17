package com.example.gldng.attendancewatchsheet;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
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
FrameLayout frameLayout2;
FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_page);
        manualAddCourse = findViewById(R.id.manualAddCourse);
        uploadCourseList = findViewById(R.id.uploadCourseList);
        manualDelete = findViewById(R.id.manualDelete);
        setAddDrop = findViewById(R.id.setAddDrop);
        manager = getFragmentManager();
        frameLayout2 = findViewById(R.id.frameLayout2);
    }

    public void fragmentOpener(View v){
        manualAddCourse.setVisibility(View.INVISIBLE);
        uploadCourseList.setVisibility(View.INVISIBLE);
        manualDelete.setVisibility(View.INVISIBLE);
        setAddDrop.setVisibility(View.INVISIBLE);
        int buttonTapped = v.getId();
        FragmentAdder(buttonTapped);

    }

    public void FragmentAdder(int userType){
        FragmentTransaction transaction = manager.beginTransaction();
        switch (userType) {
            case R.id.manualAddCourse:
                AddCourse fragment1 = new AddCourse();
                transaction.add(R.id.frameLayout2,fragment1,"manualAddCourseFragment");
                break;
            case R.id.uploadCourseList:
                UploadCourses fragment2 = new UploadCourses();
                transaction.add(R.id.frameLayout2,fragment2,"uploadCoursesFragment");
                break;
            case R.id.manualDelete:
                RemoveCourse fragment3 = new RemoveCourse();
                transaction.add(R.id.frameLayout2,fragment3,"removeCourseFragment");
                break;
            case R.id.setAddDrop:
                SetAddDrop fragment4 = new SetAddDrop();
                transaction.add(R.id.frameLayout2,fragment4,"setAddDropFragment");
                break;
        }
        transaction.commit();
    }


}
