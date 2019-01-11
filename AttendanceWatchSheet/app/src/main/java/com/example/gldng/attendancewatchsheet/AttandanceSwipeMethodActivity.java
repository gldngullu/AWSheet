package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<String> attanded;
    ArrayList<String> absent;
    SwipeCardAdapter swipeCardAdapter;


    private String selectedWeek;
    private JSONArray studentListResponse;
    private ArrayList<String[]> studentList;
    private List<String> courseInsList;
    private JSONObject attandanceResult;
    private Spinner courseSelectSpinner;
    private Spinner weekNoSpinner;
    private Response.Listener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_swipemethod);

        attanded = new ArrayList<>();
        absent = new ArrayList<>();

        navBarBuilder();

        studentList = new ArrayList<String[]>();
        courseInsList = new ArrayList<String>();
        attandanceResult = new JSONObject();

        courseSelectSpinner = (Spinner) findViewById(R.id.courseSelectSpinner);
        spinnerBuilder();


        weekNoSpinner = (Spinner) findViewById(R.id.weekNoSpinner);
        ArrayAdapter<CharSequence> arrayAdapterForWeekNo =  ArrayAdapter.createFromResource(this, R.array.WeekNo, android.R.layout.simple_spinner_item);
        weekNoSpinner.setAdapter(arrayAdapterForWeekNo);
        weekNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item =  String.valueOf(position+1);
                selectedWeek = item;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SwipeFlingAdapterView flingCounter = (SwipeFlingAdapterView) findViewById(R.id.itemToSwipe);

        a1 = new ArrayList<SwipeCard>();

        a1.add(new SwipeCard("","",""));

        swipeCardAdapter = new SwipeCardAdapter(this,getLayoutInflater(),a1);

        flingCounter.setAdapter(swipeCardAdapter);

        //Swipe Actions

        flingCounter.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                a1.remove(0);
                swipeCardAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLeftCardExit(Object o) {
                SwipeCard s = (SwipeCard) o;
                absent.add(s.getEmail());
                try {
                    attandanceResult.put(s.getEmail(),false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(a1.isEmpty()){
                    SendAttendance();
                }
            }

            @Override
            public void onRightCardExit(Object o) {
                SwipeCard s = (SwipeCard) o;
                attanded.add(s.getEmail());
                try {
                    attandanceResult.put(s.getEmail(),true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(a1.isEmpty()){
                    SendAttendance();
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });



    }

    private void SendAttendance() {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse  = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    if(success == 1){
                        Toast.makeText(AttandanceSwipeMethodActivity.this, "Attendance successfully recorded!", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(AttandanceSwipeMethodActivity.this, "Unsuccessfull", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(AttandanceSwipeMethodActivity.this, "Attendance successfully recorded!", Toast.LENGTH_LONG).show();
                    //e.printStackTrace();
                }

            }
        };

        //Toast.makeText(AttandanceListMethodActivity.this,email,Toast.LENGTH_LONG);
        AttandanceSendRequest attandanceSendRequest= new AttandanceSendRequest(attandanceResult,selectedCourse,selectedWeek,listener);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(attandanceSendRequest);




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
        //List<String> courseList = new ArrayList<String>();
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int count = jsonObject.getInt("count");
                    for (int i = 0; i < count ; i++){
                        String temp = jsonObject.getString("courses"+i);
                        courseInsList.add(temp);
                    }
                    spinnerProcess();
                }catch (JSONException e){e.printStackTrace();}
            }
        };
        // int socketTimeout = 30000;
        // RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        // stringRequest.setRetryPolicy(policy);
        String email = getIntent().getStringExtra("email");
        //Toast.makeText(AttandanceListMethodActivity.this,email,Toast.LENGTH_LONG);
        SpinnerDataRequest spinnerDataRequest = new SpinnerDataRequest(email,listener);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(spinnerDataRequest);

    }

    private void spinnerProcess(){
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AttandanceSwipeMethodActivity.this,android.R.layout.simple_spinner_dropdown_item,this.courseInsList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        courseSelectSpinner.setAdapter(spinnerArrayAdapter);
        courseSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = courseInsList.get(position);
                selectedCourse = item;
                studentListCleaner();
                studentListBuilder();
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void studentListCleaner(){
        studentList = new ArrayList<String[]>();
        // clean card array
        a1.clear();
        swipeCardAdapter.notifyDataSetChanged();

    }

    public void studentListBuilder(){



        //adding data to table
        //TableLayout tableLayout = findViewById(R.id.attandaceListTable);



        Response.Listener<String> response1Listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int count = jsonResponse.getInt("count");
                    for (int i= 0; i < count ; i++){
                        String temp=jsonResponse.getString("name"+i);
                        String temp2=jsonResponse.getString("email"+i);
                        String[] tempList = {temp,temp2};
                        studentList.add(tempList);
                    }

                    studentListProcess();

                } catch (JSONException e) {
                    Toast.makeText(AttandanceSwipeMethodActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        };

        AttandanceStudentListRequest attandanceStudentListRequest= new AttandanceStudentListRequest(selectedCourse,response1Listener);
        RequestQueue queue = Volley.newRequestQueue(AttandanceSwipeMethodActivity.this);
        queue.add(attandanceStudentListRequest);



    }

    private void studentListProcess(){


        /*
        a1.clear();
        swipeCardAdapter.notifyDataSetChanged();
        */

        for (String[] student: studentList) {

            //Student Data


            // a1.add(new SwipeCard("MERTTTALEEE","CSE800"));

            a1.add(new SwipeCard(student[0],selectedCourse,student[1]));

        }
        swipeCardAdapter.notifyDataSetChanged();
        //a1.add(new SwipeCard("Mertaleeey","CSE900"));



    }

}
