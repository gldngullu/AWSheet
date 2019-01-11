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

public class SelectCourse extends AppCompatActivity implements NavigationMenuActions {

    private final String CourseNamesSpinnerURL = "http://awsheet.cf/connect/allcourses.php";
    private final String AssistantNamesSpinnerURL = "http://awsheet.cf/connect/allassistants.php";

    String CourseSelectRequestAssistantName;
    String examnameRequest = "";
    String CourseSelectRequestCourseName = "";
    String CourseSelectRequestSyllabus = "";
    String ExamGrade = "";
    ArrayAdapter<String> adaptor;
    private Spinner courselist;
    private Spinner assistantlist;
    private EditText syllabusText;
    private Button GradeAdd;
    private Button Createbtn;
    private Button Cancelbtn;
    private EditText gradeText;
    private EditText gradePercent;
    private ArrayList<String> Coursenames;
    private ArrayList<String> Assistantnames;
    private JSONArray resultCourseName;
    private JSONArray resultAssistantName;
    private ListView listvi;
    private TextView GooddayText;
    private int sum = 0;
    private boolean isBig = false;
    private boolean isSameName = false;
    private OnItemSelectedListener Listener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String validationForCreateButton1 = courselist.getSelectedItem().toString();
            String validationForCreateButton2 = assistantlist.getSelectedItem().toString();
            Createbtn.setEnabled(!validationForCreateButton1.isEmpty() && !validationForCreateButton2.isEmpty());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private JSONArray Nothing;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        navBarBuilder();
        GooddayText = (TextView) findViewById(R.id.GoodDayText);

        GooddayText.setText("Good Day " + getIntent().getStringExtra("email") + ", Sir!");
        Coursenames = new ArrayList<>();
        Assistantnames = new ArrayList<>();
        listvi = (ListView) findViewById(R.id.listvi);
        syllabusText = (EditText) findViewById(R.id.syllabusss);
        GradeAdd = (Button) findViewById(R.id.gradeAdder);
        Createbtn = (Button) findViewById(R.id.createbtn);
        Cancelbtn = (Button) findViewById(R.id.cancelbtn);
        gradeText = (EditText) findViewById(R.id.gradeText);
        gradePercent = (EditText) findViewById(R.id.percent);
        courselist = (Spinner) this.findViewById(R.id.courselist);
        assistantlist = (Spinner) this.findViewById(R.id.assistantlist);
        Createbtn.setEnabled(true);

        getDataToSpinner(CourseNamesSpinnerURL);
        getDataToSpinner(AssistantNamesSpinnerURL);

        adaptor = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listvi.setAdapter(adaptor);


        AlertDialog.Builder alert =
                new AlertDialog.Builder(SelectCourse.this);

        listvi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert =
                        new AlertDialog.Builder(SelectCourse.this);
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
                ExamGrade = gradePercent.getText().toString();
                String examname = gradeText.getText().toString();
                isBig = false;
                isSameName = false;
                int exm = Integer.parseInt(ExamGrade);
                for (int i = 0; i < adaptor.getCount(); i++) {//CONTROLS IS THERE ANY SAME NAME
                    if (adaptor.getItem(i).equals(examname)) {
                        isSameName = true;
                    }
                }
                if (!isSameName) {
                    if (sum + exm > 100 || sum + exm < 0 || exm > 100 || exm < 0) {
                        Toast.makeText(SelectCourse.this, "YOU MUST ENTER BETWEEN 0 AND 100", Toast.LENGTH_LONG).show();
                        isBig = true;
                    }
                    if (!isBig) {
                        Add_Course_Exam(examname);
                        sum += exm;
                    }
                }
            }
        });

        Createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(SelectCourse.this, Courses_Instructor.class);
                back.putExtra("email", getIntent().getStringExtra("email"));
                CourseSelectRequestCourseName = courselist.getSelectedItem().toString();
                CourseSelectRequestAssistantName = assistantlist.getSelectedItem().toString();
                CourseSelectRequestSyllabus = syllabusText.getText().toString();
                ExamGrade = gradePercent.getText().toString();
                String emailIns = getIntent().getStringExtra("email");
                if (CourseSelectRequestCourseName.isEmpty()) {
                    Toast.makeText(SelectCourse.this, "YOU MUST CHOOSE A COURSE NAME", Toast.LENGTH_LONG).show();
                }
                if (CourseSelectRequestAssistantName.isEmpty()) {
                    Toast.makeText(SelectCourse.this, "YOU MUST USE ASSISTANT NAME", Toast.LENGTH_LONG).show();
                } else {
                    String SelectCourseURL="http://awsheet.cf/connect/SelectCourse.php?asism="+CourseSelectRequestAssistantName+"&insm="+emailIns+"&coursecode="+CourseSelectRequestCourseName+"&syllabus="+CourseSelectRequestSyllabus;
                    sendQuary(SelectCourseURL);
                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                int success = jsonResponse.getInt("success");
                                if (success == 1) {
                                    Log.d("successful", "YEY , QUARY WORKED");
                                    Toast.makeText(SelectCourse.this, "YEY , QUERY WORKED !", Toast.LENGTH_LONG).show();
                                } else {
                                    Log.d("unsuccessful", "TUH , QUARY DOES NOT WORKED");
                                    Toast.makeText(SelectCourse.this, "TUH , QUERY DOES NOT WORKED !", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };
                    //*--------------------WARNING------------------*//
                    // String InstructorMail="mertalikoprulu@gmail.com"; // THIS PART HAS TO GET FROM INTENT FROM INSTRUCTOR MAIN PAGE !!!!!!!!
                    // -------------------- DONE -------------------- //

                    CourseSelectRequest courseSelectionRequest = new CourseSelectRequest(emailIns, CourseSelectRequestAssistantName, CourseSelectRequestCourseName, CourseSelectRequestSyllabus, responselistener);
                    RequestQueue queue = Volley.newRequestQueue(SelectCourse.this);
                    for (int i =0;i<adaptor.getCount();i++) {// THIS LOOP IS FOR ACTUALY CAUSES FROM MANY MIDTERM COULD BE ADDED SO WE NEED TO ADD MIDTERM FROM ADAPTOR ONE BY ONE
                        //String MidtermUrl="http://awsheet.cf/connect/gradeAdder.php?examN=";
                        //sendQuary(MidtermUrl);
                    }
                    queue.add(courseSelectionRequest);
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
                finish();

            }
        });


    }

    public void sendQuary (String URL){
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    Nothing = j.getJSONArray("result");
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
        StringRequest stringRequest1 = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultAssistantName = j.getJSONArray("result");
                            loadAssistantName(resultAssistantName);
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
        requestQueue.add(stringRequest1);
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
        courselist.setAdapter(new ArrayAdapter<String>(SelectCourse.this, android.R.layout.simple_spinner_dropdown_item, Coursenames));
    }

    private void loadAssistantName(JSONArray j) {
        System.out.println(j.toString());
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                //System.out.println(json.getString("assistantMail"));
                Assistantnames.add(json.getString("assistantMail"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        assistantlist.setAdapter(new ArrayAdapter<String>(SelectCourse.this, android.R.layout.simple_spinner_dropdown_item, Assistantnames));
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
                        intent = new Intent(SelectCourse.this, Courses_Instructor.class);
                        break;
                    case R.id.navigation_courses:
                        intent = new Intent(SelectCourse.this, Courses_Instructor.class);
                        break;
                    case R.id.navigation_scan:
                        intent = new Intent(SelectCourse.this, AttandanceSelectorActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(SelectCourse.this, HomeActivityInstructor.class);
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
