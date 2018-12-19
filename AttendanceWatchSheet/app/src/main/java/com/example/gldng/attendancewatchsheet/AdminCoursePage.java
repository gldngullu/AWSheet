package com.example.gldng.attendancewatchsheet;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdminCoursePage extends AppCompatActivity {
Button manualAddCourseButton;
Button uploadCourseListButton;
Button manualDeleteCourseButton;
Button setAddDropButton;
TextView addCourseText;
TextView removeCourseText;
FrameLayout frameLayout2;
FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_page);
        manualAddCourseButton = findViewById(R.id.manualAddCourseButton);
        uploadCourseListButton = findViewById(R.id.uploadCourseListButton);
        manualDeleteCourseButton = findViewById(R.id.manualDeleteCourseButton);
        setAddDropButton = findViewById(R.id.setAddDropButton);
        addCourseText = findViewById(R.id.addCourseText);
        removeCourseText = findViewById(R.id.removeCourseText);
        manager = getFragmentManager();
        frameLayout2 = findViewById(R.id.frameLayout2);
    }

    public void fragmentOpener(View v){
        manualAddCourseButton.setVisibility(View.INVISIBLE);
        uploadCourseListButton.setVisibility(View.INVISIBLE);
        manualDeleteCourseButton.setVisibility(View.INVISIBLE);
        setAddDropButton.setVisibility(View.INVISIBLE);
        addCourseText.setVisibility(View.INVISIBLE);
        removeCourseText.setVisibility(View.INVISIBLE);
        int buttonTapped = v.getId();
        FragmentAdder(buttonTapped);

    }

    public void FragmentAdder(int userType){
        FragmentTransaction transaction = manager.beginTransaction();
        switch (userType) {
            case R.id.manualAddCourseButton:
                AddCourse fragment1 = new AddCourse();
                transaction.add(R.id.frameLayout2,fragment1,"manualAddCourseFragment");
                break;
            case R.id.uploadCourseListButton:
                UploadCourses fragment2 = new UploadCourses();
                transaction.add(R.id.frameLayout2,fragment2,"uploadCoursesFragment");
                break;
            case R.id.manualDeleteCourseButton:
                RemoveCourse fragment3 = new RemoveCourse();
                transaction.add(R.id.frameLayout2,fragment3,"removeCourseFragment");
                break;
            case R.id.setAddDropButton:
                SetAddDrop fragment4 = new SetAddDrop();
                transaction.add(R.id.frameLayout2,fragment4,"setAddDropFragment");
                break;
        }
        transaction.commit();
    }

    public void alertDialog2(final View v) {
        int buttonId = v.getId();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        switch (buttonId) {
            case R.id.createCourseButton:
                builder.setTitle("Add New Course")
                        .setMessage("You will add a new course to the System, Are you sure ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // ADD course TO DATABASE
                                removeFragment(-1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // RETURN BACK
                            }
                        });
                break;
            case R.id.removeCourseButton:
                builder.setTitle("Remove Course")
                        .setMessage("You will delete a course from the System, Are you sure ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // delete course from DATABASE
                                removeFragment(-1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // RETURN BACK
                            }
                        });
                break;
            case R.id.confirmAddDropSetButton:
                builder.setTitle("Upload Course")
                        .setMessage("You will upload a new file for the course list. Do you confirm ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // delete course from DATABASE
                                removeFragment(-1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // RETURN BACK
                            }
                        });
                break;
        }
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public void removeFragment(View v){
        int id = v.getId();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id){
            case R.id.cancelCourseAdditionButton:
                AddInstructor fragment1 = (AddInstructor) manager.findFragmentByTag("AddInstructorFragment");
                if (fragment1 != null) {
                    transaction.remove(fragment1);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelCourseRemovingButton:
                AddAssistant fragment2 = (AddAssistant) manager.findFragmentByTag("AddAssistantFragment");
                if (fragment2 != null) {
                    transaction.remove(fragment2);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelUploadCourseButton:
                AddAdmin fragment3 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
                if (fragment3 != null) {
                    transaction.remove(fragment3);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelAddDropSetButton:
                AddAdmin fragment4 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
                if (fragment4 != null) {
                    transaction.remove(fragment4);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
        }
        manualAddCourseButton.setVisibility(View.VISIBLE);
        uploadCourseListButton.setVisibility(View.VISIBLE);
        manualDeleteCourseButton.setVisibility(View.VISIBLE);
        setAddDropButton.setVisibility(View.VISIBLE);
        addCourseText.setVisibility(View.VISIBLE);
        removeCourseText.setVisibility(View.VISIBLE);
    }

    public void removeFragment(int id){
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id){
            case -1:
                AddInstructor fragment1 = (AddInstructor) manager.findFragmentByTag("AddInstructorFragment");
                if (fragment1 != null) {
                    transaction.remove(fragment1);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case -2:
                AddAssistant fragment2 = (AddAssistant) manager.findFragmentByTag("AddAssistantFragment");
                if (fragment2 != null) {
                    transaction.remove(fragment2);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case -3:
                AddAdmin fragment3 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
                if (fragment3 != null) {
                    transaction.remove(fragment3);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case -4:
                AddAdmin fragment4 = (AddAdmin) manager.findFragmentByTag("AddAdminFragment");
                if (fragment4 != null) {
                    transaction.remove(fragment4);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
        }
        manualAddCourseButton.setVisibility(View.VISIBLE);
        uploadCourseListButton.setVisibility(View.VISIBLE);
        manualDeleteCourseButton.setVisibility(View.VISIBLE);
        setAddDropButton.setVisibility(View.VISIBLE);
        addCourseText.setVisibility(View.VISIBLE);
        removeCourseText.setVisibility(View.VISIBLE);
    }
}
