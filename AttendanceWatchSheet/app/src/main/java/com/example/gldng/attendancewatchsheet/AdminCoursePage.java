package com.example.gldng.attendancewatchsheet;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AdminCoursePage extends AppCompatActivity implements NavigationMenuActions {
    Button manualAddCourseButton;
    Button manualDeleteCourseButton;
    FrameLayout frameLayout2;
    FragmentManager manager;
    AddCourse fragment1;
    RemoveCourse fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_page);

        navBarBuilder();
        manualAddCourseButton = findViewById(R.id.manualAddCourseButton);
        manualDeleteCourseButton = findViewById(R.id.manualDeleteCourseButton);
        manager = getFragmentManager();
        frameLayout2 = findViewById(R.id.frameLayout2);


    }

    public void fragmentOpener(View v) {
        manualAddCourseButton.setVisibility(View.INVISIBLE);
        manualDeleteCourseButton.setVisibility(View.INVISIBLE);
        int buttonTapped = v.getId();
        FragmentAdder(buttonTapped);

    }

    public void FragmentAdder(int userType) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (userType) {
            case R.id.manualAddCourseButton:
                fragment1 = new AddCourse();
                transaction.add(R.id.frameLayout2, fragment1, "manualAddCourseFragment");
                break;
            case R.id.manualDeleteCourseButton:
                fragment3 = new RemoveCourse();
                transaction.add(R.id.frameLayout2, fragment3, "removeCourseFragment");
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
                                try {
                                    boolean isSuccessful = fragment1.addNewCourse();
                                    //if(isSuccessful)
                                        //Toast.makeText(v.getContext(), "New course added successfully", Toast.LENGTH_LONG).show();
                                    //else
                                        //oast.makeText(v.getContext(), "This course already exists", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(v.getContext(), "New course cannot be added", Toast.LENGTH_LONG).show();
                                }
                                removeFragment2(-1);
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
                                try {
                                    fragment3.removeCourse();
                                    Toast.makeText(v.getContext(), "Course removed successfully", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(v.getContext(), "Course can not be deleted", Toast.LENGTH_LONG).show();
                                }
                                removeFragment2(-3);
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

    public void removeFragment2(final View v) {
        int id = v.getId();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id) {
            case R.id.cancelCourseAdditionButton:
                AddCourse fragment1 = (AddCourse) manager.findFragmentByTag("manualAddCourseFragment");
                fragment1.dataAdapter.clear();
                fragment1.dataAdapter.notifyDataSetChanged();
                if (fragment1 != null) {
                    transaction.remove(fragment1);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancelCourseRemovingButton:
                RemoveCourse fragment2 = (RemoveCourse) manager.findFragmentByTag("removeCourseFragment");
                if (fragment2 != null) {
                    transaction.remove(fragment2);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
        }
        manualAddCourseButton.setVisibility(View.VISIBLE);
        manualDeleteCourseButton.setVisibility(View.VISIBLE);
    }

    public void removeFragment2(int id) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id) {
            case -1:
                AddCourse fragment1 = (AddCourse) manager.findFragmentByTag("manualAddCourseFragment");
                if (fragment1 != null) {
                    transaction.remove(fragment1);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
            case -3:
                RemoveCourse fragment3 = (RemoveCourse) manager.findFragmentByTag("removeCourseFragment");
                if (fragment3 != null) {
                    transaction.remove(fragment3);
                    transaction.commit();
                } else {
                    Toast.makeText(this, "There is no fragment here", Toast.LENGTH_LONG).show();
                }
                break;
        }
        manualAddCourseButton.setVisibility(View.VISIBLE);
        manualDeleteCourseButton.setVisibility(View.VISIBLE);

    }



    @Override
    public void navBarBuilder() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(AdminCoursePage.this, HomeActivityAdmin.class);
                        break;
                    case R.id.navigation_courses:
                        return false;
                    case R.id.navigation_member:
                        intent = new Intent(AdminCoursePage.this, AddUser.class);
                        break;
                }
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                return true;
            }
        });
    }


}
