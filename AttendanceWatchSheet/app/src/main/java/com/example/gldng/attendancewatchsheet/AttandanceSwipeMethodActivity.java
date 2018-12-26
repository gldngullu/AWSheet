package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttandanceSwipeMethodActivity extends AppCompatActivity implements NavigationMenuActions,SpinnerHelper{

    private String selectedCourse;
    ArrayList<SwipeCard> a1;
    SwipeCardAdapter swipeCardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_swipemethod);

        navBarBuilder();

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
    @Override
    public void navBarBuilder(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        intent = new Intent(AttandanceSwipeMethodActivity.this,HomeActivityInstructor.class);
                        break;
                    case R.id.navigation_courses_instructor:
                        intent = new Intent(AttandanceSwipeMethodActivity.this,Courses_Instructor.class);
                        break;
                    case R.id.navigation_attend:
                        intent = new Intent(AttandanceSwipeMethodActivity.this,AttandanceSelectorActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(AttandanceSwipeMethodActivity.this,HomeActivityInstructor.class);
                        break;
                }
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void spinnerBuilder() {

        //spinner
        final Spinner spinner = (Spinner) findViewById(R.id.courseSelectSpinner);

        final List<String> courseList = new ArrayList<>();

        Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        JSONArray jsonArray=jsonObject.getJSONArray("Name");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String country=jsonObject1.getString("Country");
                            courseList.add(country);
                        }
                    }
                }catch (JSONException e){e.printStackTrace();}
            }
        };


        // int socketTimeout = 30000;
        // RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        // stringRequest.setRetryPolicy(policy);
        SpinnerDataRequest spinnerDataRequest = new SpinnerDataRequest(getIntent().getStringExtra("email"),listener );
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(spinnerDataRequest);


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,courseList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    selectedCourse = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
