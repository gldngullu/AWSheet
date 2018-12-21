package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class AttandanceSwipeMethodActivity extends AppCompatActivity implements NavigationMenuActions{

    ArrayList<SwipeCard> a1;
    SwipeCardAdapter swipeCardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_swipemethod);

        //Swipe Actions
        SwipeFlingAdapterView flingCounter = (SwipeFlingAdapterView) findViewById(R.id.itemToSwipe);

        //Student Data
        a1 = new ArrayList<SwipeCard>();
        a1.add(new SwipeCard("Mertaleeey","CSE900"));
        a1.add(new SwipeCard("MERTTTALEEE","CSE800"));
        a1.add(new SwipeCard("mertalü","CSE999"));


        swipeCardAdapter = new SwipeCardAdapter(this,getLayoutInflater(),a1);

        flingCounter.setAdapter(swipeCardAdapter);

        flingCounter.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                a1.remove(0);
                swipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {

            }

            @Override
            public void onRightCardExit(Object o) {

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });


    }

    public void navMenuSelection(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.navigation_home:
                intent = new Intent(AttandanceSwipeMethodActivity.this,HomeActivityInstructor.class);
                break;
            case R.id.navigation_courses:
                intent = new Intent(AttandanceSwipeMethodActivity.this,Courses_Instructor.class);
                break;
            case R.id.navigation_attend:
                intent = new Intent(AttandanceSwipeMethodActivity.this,AttandanceSelectorActivity.class);
                break;
            case R.id.navigation_calendar:
                //intent = new Intent(HomeActivityInstructor.this,.class);
                break;
        }
        startActivity(intent);
    }


}
