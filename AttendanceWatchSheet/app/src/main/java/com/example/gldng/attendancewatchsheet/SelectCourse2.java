package com.example.gldng.attendancewatchsheet;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class SelectCourse2 extends AppCompatActivity implements NavigationMenuActions {

    private final String CourseNamesSpinnerURL = "http://awsheet.cf/connect/AllCoursesStudentCanTake.php";
    String CourseSelectRequestStudentCourseName = "";
    String ExamGrade = "";
    ArrayAdapter<String> adaptor;
    private Spinner courselist;
    private Button Enrollbtn;
    private Button Cancelbtn;
    private EditText gradeText;
    private EditText gradePercent;
    private ArrayList<String> Coursenames;
    private ArrayList<String> Assistantnames;
    private JSONArray resultCourseName;
    private JSONArray resultAssistantName;
    private ListView listvi;
    private TextView GooddayText;
    private OnItemSelectedListener Listener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String validationForCreateButton1 = courselist.getSelectedItem().toString();
            Enrollbtn.setEnabled(!validationForCreateButton1.isEmpty());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course2);
        navBarBuilder();
        GooddayText = (TextView) findViewById(R.id.GoodDayText);
        GooddayText.setText("Good Day " + getIntent().getStringExtra("email") + ", Sir!");
        Coursenames = new ArrayList<>();
        listvi = (ListView) findViewById(R.id.listvi);
        Enrollbtn = (Button) findViewById(R.id.createbtn);
        Cancelbtn = (Button) findViewById(R.id.cancelbtn);
        gradeText = (EditText) findViewById(R.id.gradeText);
        gradePercent = (EditText) findViewById(R.id.percent);
        courselist = (Spinner) this.findViewById(R.id.courselist);
        Enrollbtn.setEnabled(true);
        getDataToSpinner(CourseNamesSpinnerURL);


        adaptor = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listvi.setAdapter(adaptor);


        AlertDialog.Builder alert =
                new AlertDialog.Builder(SelectCourse2.this);

        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert =
                        new AlertDialog.Builder(SelectCourse2.this);
                String outAlert = gradeText.getText().toString() + " has " + ExamGrade + " %.";

                alert.setMessage(outAlert)
                        .setCancelable(false)
                        .setPositiveButton("Ok !", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.create().show();
            }
        });


        courselist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String course = courselist.getItemAtPosition(courselist.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(), course, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Enrollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SelectCourse2.this, Courses_Instructor.class);
                CourseSelectRequestStudentCourseName = courselist.getSelectedItem().toString();
                if (CourseSelectRequestStudentCourseName.isEmpty()) {
                    Toast.makeText(SelectCourse2.this, "YOU MUST CHOOSE A COURSE NAME", Toast.LENGTH_LONG).show();
                } else {
                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                int success = jsonResponse.getInt("success");
                                if (success == 1) {
                                    Log.d("successful", "YEY , QUARY WORKED");
                                    Toast.makeText(SelectCourse2.this, "YEY , QUERY WORKED !", Toast.LENGTH_LONG).show();
                                } else {
                                    Log.d("unsuccessful", "TUH , QUARY DOES NOT WORKED");
                                    Toast.makeText(SelectCourse2.this, "TUH , QUERY DOES NOT WORKED !", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    String emailStu = getIntent().getStringExtra("email");
                    CourseSelectStudentRequest courseSelectionRequest = new CourseSelectStudentRequest(emailStu,CourseSelectRequestStudentCourseName, responselistener);
                    RequestQueue queue = Volley.newRequestQueue(SelectCourse2.this);
                    queue.add(courseSelectionRequest);

                    SelectCourse2.this.startActivity(back);
                    finish();
                }
            }
        });

        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SelectCourse2.this, Courses_Instructor.class);
                SelectCourse2.this.startActivity(back);
                finish();
            }
        });

    }

    private void getDataToSpinner(String URL) {
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    resultCourseName = j.getJSONArray("result");
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

    public void Add_Course_Exam(String exammname) {
        // ExamGrade = gradePercent.getText().toString();
        adaptor.add(exammname);
    }

    private void loadCourseName(JSONArray j) {
        System.out.println(j.toString());
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                // System.out.println(json.getString("coursecode"));
                Coursenames.add(json.getString("coursecode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        courselist.setAdapter(new ArrayAdapter<String>(SelectCourse2.this, android.R.layout.simple_spinner_dropdown_item, Coursenames));
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
                        intent = new Intent(SelectCourse2.this, HomeActivity.class);
                        break;
                    case R.id.navigation_courses:
                        intent = new Intent(SelectCourse2.this, Courses_Student.class);
                        break;
                    case R.id.navigation_scan:
                        intent = new Intent(SelectCourse2.this, AttandanceQrreaderActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(SelectCourse2.this, HomeActivityInstructor.class);
                        break;
                }
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
                return false;
            }
        });
    }

}
