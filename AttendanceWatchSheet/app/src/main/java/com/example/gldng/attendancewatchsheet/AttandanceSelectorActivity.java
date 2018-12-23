package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;

public class AttandanceSelectorActivity extends AppCompatActivity implements NavigationMenuActions{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_selector);
        setTitle("Attandace");
        navBarBuilder();
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

    @Override
    public void navBarBuilder(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        intent = new Intent(AttandanceSelectorActivity.this,HomeActivityInstructor.class);
                        break;
                    case R.id.navigation_courses_instructor:
                        intent = new Intent(AttandanceSelectorActivity.this,Courses_Instructor.class);
                        break;
                    case R.id.navigation_attend:
                        return true;
                    case R.id.navigation_calendar:
                        intent = new Intent(AttandanceSelectorActivity.this,HomeActivityInstructor.class);
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
    }


}
