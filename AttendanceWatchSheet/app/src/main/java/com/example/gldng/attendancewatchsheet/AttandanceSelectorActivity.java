package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;

public class AttandanceSelectorActivity extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_selector);
        setTitle("Attandace");
    }

    public void goAttandance(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.listRow:
                intent = new Intent(AttandanceSelectorActivity.this, AttandanceListMethodActivity.class);
                break;
            case R.id.swipeRow:
                intent = new Intent(AttandanceSelectorActivity.this, AttandanceSwipeMethodActivity.class);
                break;
            case R.id.qrRow:
                intent = new Intent(AttandanceSelectorActivity.this, AttandanceQrMethodActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                intent = new Intent(AttandanceSelectorActivity.this,HomeActivityInstructor.class);
                break;
            case R.id.navigation_courses:
                intent = new Intent(AttandanceSelectorActivity.this,Courses_Instructor.class);
                break;
            case R.id.navigation_attend:
                //intent = new Intent(AttandanceListMethodActivity.this,AttandanceSelectorActivity.class);
                break;
            case R.id.navigation_calendar:
                //intent = new Intent(HomeActivityInstructor.this,.class);
                break;
        }
        startActivity(intent);
    }


}
