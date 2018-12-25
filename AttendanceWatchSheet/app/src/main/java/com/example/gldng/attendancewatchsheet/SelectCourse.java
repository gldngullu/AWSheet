package com.example.gldng.attendancewatchsheet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
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
    private JSONArray resultCourseName;
    private JSONArray resultAssistantName;
    String CourseSelectRequestAssistantName;
    private ScrollView examLayout;
    String examnameRequest;
    String CourseSelectRequestCourseName;
    String CourseSelectRequestSyllabus;
    String ExamGrade;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        navBarBuilder();

        Coursenames = new ArrayList<>();
        syllabusText=(EditText)findViewById(R.id.syllabusss);
        GradeAdd=(Button)findViewById(R.id.gradeAdder);
        Createbtn=(Button)findViewById(R.id.createbtn);
        Cancelbtn=(Button)findViewById(R.id.cancelbtn);
        gradeText=(EditText) findViewById(R.id.gradeText);
        gradePercent=(EditText)findViewById(R.id.percent);
        examLayout =(ScrollView) findViewById(R.id.Midterm_View);
        courselist = (Spinner) this.findViewById(R.id.courselist);
        assistantlist = (Spinner) this.findViewById(R.id.assistantlist);
        Createbtn.setEnabled(false);
        examnameRequest="";
        ExamGrade="";
        CourseSelectRequestCourseName="";
        CourseSelectRequestSyllabus="";
        //
        getDataToSpinner();

        courselist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String course = courselist.getItemAtPosition(courselist.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(), course, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        assistantlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String assistants = assistantlist.getItemAtPosition(assistantlist.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(), assistants, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                ExamGrade=gradePercent.getText().toString();
                examnameRequest=Add_Course_Exam();
            }
        });

        Createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SelectCourse.this, Courses_Instructor.class);
                CourseSelectRequestCourseName = courselist.getSelectedItem().toString();
                CourseSelectRequestAssistantName = assistantlist.getSelectedItem().toString();
                CourseSelectRequestSyllabus = syllabusText.getText().toString();
                if(CourseSelectRequestCourseName.isEmpty()){
                    Toast.makeText(SelectCourse.this, "YOU MUST CHOOSE A COURSE NAME",Toast.LENGTH_LONG).show();
                }
                if(CourseSelectRequestAssistantName.isEmpty()){
                    Toast.makeText(SelectCourse.this, "YOU MUST USE ASSISTANT NAME",Toast.LENGTH_LONG).show();
                }
                else{
                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse  = new JSONObject(response);
                                int success = jsonResponse.getInt("success");
                                if(success==1){
                                    Log.d("successful","YEY , QUARY WORKED");
                                    Toast.makeText(SelectCourse.this, "YEY , QUERY WORKED !", Toast.LENGTH_LONG).show();
                                }else{
                                    Log.d("unsuccessful","TUH , QUARY DOES NOT WORKED");
                                    Toast.makeText(SelectCourse.this, "TUH , QUERY DOES NOT WORKED !", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };                                               //*--------------------WARNING------------------*//
                    // String InstructorMail="mertalikoprulu@gmail.com";// THIS PART HAS TO GET FROM INTENT FROM INSTRUCTOR MAIN PAGE !!!!!!!!
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    String emailIns=getIntent().getStringExtra("email");
                    CourseSelectRequest courseSelectionRequest = new CourseSelectRequest(emailIns,CourseSelectRequestAssistantName,CourseSelectRequestCourseName,CourseSelectRequestSyllabus,responselistener);
                    ExamAddRequest examAdditionRequest =  new ExamAddRequest(CourseSelectRequestCourseName,examnameRequest,ExamGrade,responselistener);
                    RequestQueue queue = Volley.newRequestQueue(SelectCourse.this);
                    queue.add(courseSelectionRequest);
                    queue.add(examAdditionRequest);
                    SelectCourse.this.startActivity(back);
                    finish();
                }
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


    private OnItemSelectedListener Listener=new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           String validationForCreateButton1 = courselist.getSelectedItem().toString();
            String validationForCreateButton2= assistantlist.getSelectedItem().toString();
            Createbtn.setEnabled(!validationForCreateButton1.isEmpty()&&!validationForCreateButton2.isEmpty());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void getDataToSpinner(){
        StringRequest stringRequest = new StringRequest("http://awsheet.cf/connect/allcourses.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultCourseName = j.getJSONArray("coursename");
                            loadCourseName(resultCourseName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

            public String Add_Course_Exam(){
              String exammname=gradeText.getText().toString();
               examLayout.addView(gradeText);
                                        return exammname;
    }

    private void loadCourseName(JSONArray j){
        System.out.println(j.toString());
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Coursenames.add(json.getString("coursecode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        courselist.setAdapter(new ArrayAdapter<String>(SelectCourse.this, android.R.layout.simple_spinner_dropdown_item, Coursenames));
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
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return false;
            }
        });
    }

}
