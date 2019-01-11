package com.example.gldng.attendancewatchsheet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


    public class Courses_Student extends AppCompatActivity implements NavigationMenuActions{


        private final String URL= "http://awsheet.cf/connect/takenCourseStudent.php";
        private ListView course_list;
        private Button addCoursebtn;
        ArrayAdapter<String> adaptor;
        private ArrayList<String> Coursenames;
        private JSONArray InstCourses;
        String urlDropCourse;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_courses__instructor);
            addCoursebtn=(Button)findViewById(R.id.addcourse);
            course_list=(ListView) findViewById(R.id.course_list);
            Coursenames=new ArrayList<>();
            getDatatoList(URL);///URL BROKEN !!!
            navBarBuilder();

            Intent back = new Intent(Courses_Student.this, Courses_Student.class);
            Response.Listener<String> responselistener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        int success = jsonResponse.getInt("success");
                        if (success == 1) {
                            Log.d("successful", "YEY , QUARY WORKED");
                            Toast.makeText(Courses_Student.this, "YEY , QUERY WORKED !", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("unsuccessful", "TUH , QUARY DOES NOT WORKED");
                            Toast.makeText(Courses_Student.this, "TUH , QUERY DOES NOT WORKED !", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            String email = getIntent().getStringExtra("email");
            System.out.println(email);
            GetInstructorCoursesRequest coursereq= new GetInstructorCoursesRequest(email,responselistener);
            RequestQueue queue = Volley.newRequestQueue(Courses_Student.this);
            queue.add(coursereq);

            AlertDialog.Builder alert =
                    new AlertDialog.Builder(Courses_Student.this);

            course_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected= course_list.getItemAtPosition(position).toString();
                  urlDropCourse = "http://awsheet.cf/connect/dropCourseStu.php?CourseCode="+selected;
                    AlertDialog.Builder alert =
                            new AlertDialog.Builder(Courses_Student.this);
                    String outAlert="Would you like to Remove it ! ";

                    alert.setMessage(outAlert)
                            .setCancelable(true)
                            .setNegativeButton("Remove Course", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //REMOVE COURSE REQUEST!
                                    removeCourse(urlDropCourse);
                                    Intent CourseRemove = new Intent(Courses_Student.this, Courses_Student.class);
                                    CourseRemove.putExtra("email", getIntent().getStringExtra("email"));
                                    Courses_Student.this.startActivity(CourseRemove);
                                    finish();
                                }
                            })
                            .setPositiveButton("Ok !", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alert.create().show();
                }
            });


            addCoursebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent CourseAddition = new Intent(Courses_Student.this,SelectCourse2.class);
                    CourseAddition.putExtra("email",getIntent().getStringExtra("email"));
                    Courses_Student.this.startActivity(CourseAddition);
                    finish();
                }
            });

        String Url1= "http://awsheet.cf/connect/takenCourseStudent.php?mailAdress="+email;
        System.out.println(Url1);
        getDatatoList(Url1);///URL FIXED !!!
    }

        public void removeCourse(String URL){
            StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    JSONObject j = null;
                    try {
                        j = new JSONObject(response);
                        InstCourses = j.getJSONArray("result");
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
        private void getDatatoList(String URL){
            StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    JSONObject j = null;
                    try {
                        j = new JSONObject(response);
                        InstCourses = j.getJSONArray("result");
                        loadCourses(InstCourses);
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

        private void loadCourses(JSONArray j){
            for (int i = 0; i < j.length(); i++) {
                try {
                    JSONObject json = j.getJSONObject(i);
                    // System.out.println(json.getString("coursecode"));
                    Coursenames.add(json.getString("coursecode"));
                    String a=json.getString("coursecode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            course_list.setAdapter(new ArrayAdapter<String>(Courses_Student.this, android.R.layout.simple_spinner_dropdown_item, Coursenames));

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
                            intent = new Intent(Courses_Student.this,HomeActivityInstructor.class);
                            break;
                        case R.id.navigation_courses_instructor:
                            return true;
                        case R.id.navigation_attend:
                            intent = new Intent(Courses_Student.this,AttandanceSelectorActivity.class);
                            break;
                        case R.id.navigation_calendar:
                            intent = new Intent(Courses_Student.this,HomeActivityInstructor.class);
                            break;
                    }
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    startActivity(intent);
                    finish();
                    return false;
                }
            });
        }
    }

