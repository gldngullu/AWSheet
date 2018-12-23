package com.example.gldng.attendancewatchsheet;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SelectCourse extends AppCompatActivity implements NavigationMenuActions {

    private Spinner courselist;
    private Spinner assistantlist;
    private EditText syllabusText;
    private Button GradeAdd;
    private Button Createbtn;
    private Button Cancelbtn;
    private EditText gradeText;
    private EditText gradePercent;
    private String CourseNamesSpinnerURL="http://awsheet.cf/connect/allcourses.php";
    private String AssistantNamesSpinnerURL="http://awsheet.cf/connect/allassistants.php" ;
    private ArrayList<String> Coursenames;
    private ArrayList<String> Assistantnames;
    private ScrollView examLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        Coursenames= new ArrayList<>();
        syllabusText.findViewById(R.id.syllabusLine);
        GradeAdd.findViewById(R.id.gradeAdder);
        Createbtn.findViewById(R.id.createbtn);
        Cancelbtn.findViewById(R.id.cancelbtn);
        gradeText.findViewById(R.id.gradeText);
        gradePercent.findViewById(R.id.percent);
        examLayout= findViewById(R.id.Midterm_View);
        courselist = (Spinner) this.findViewById(R.id.courselist);
        assistantlist = (Spinner) this.findViewById(R.id.assistantlist);
        loadSpinnerData(CourseNamesSpinnerURL);
        loadSpinnerData(AssistantNamesSpinnerURL);
        courselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String course= courselist.getItemAtPosition(courselist.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),course,Toast.LENGTH_LONG).show();
            }


        });
        assistantlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String assistants= assistantlist.getItemAtPosition(assistantlist.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),assistants,Toast.LENGTH_LONG).show();


            }
        });


       // ArrayAdapter<CharSequence> arrayAdapterForDays =
       //         ArrayAdapter.createFromResource(this, R.array.CourseList, android.R.layout.simple_spinner_item);
       // ArrayAdapter<CharSequence> arrayAdapterForHours =
        //        ArrayAdapter.createFromResource(this, R.array.CourseList, android.R.layout.simple_spinner_item);
        //courselist.setAdapter(arrayAdapterForDays);
        //assistantlist.setAdapter(arrayAdapterForHours);

        GradeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Course_Exam();
            }
        });

        Createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SelectCourse.this, Courses_Instructor.class);
                String selectedcoursename;


                SelectCourse.this.startActivity(back);


            }
        });


        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SelectCourse.this, Courses_Instructor.class);
                SelectCourse.this.startActivity(back);

            }
        });
    }

            private  void loadSpinnerData(String url){
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getInt("success")==1){
                                JSONArray jsonArray=jsonObject.getJSONArray("Name");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    String country=jsonObject1.getString("Country");
                                    Coursenames.add(country);
                                }
                            }
                            courselist.setAdapter(new ArrayAdapter<String>(SelectCourse.this, android.R.layout.simple_spinner_dropdown_item, Coursenames));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);
            }

            public void Add_Course_Exam(){


               examLayout.addView(gradeText);
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
                        intent = new Intent(SelectCourse.this,Courses_Instructor.class);
                        break;
                    case R.id.navigation_courses:
                        intent = new Intent(SelectCourse.this,Courses_Instructor.class);
                        break;
                    case R.id.navigation_scan:
                        intent = new Intent(SelectCourse.this,AttandanceSelectorActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(SelectCourse.this,HomeActivityInstructor.class);
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
    }

}
