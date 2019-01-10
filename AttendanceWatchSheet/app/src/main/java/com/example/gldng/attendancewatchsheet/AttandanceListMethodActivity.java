package com.example.gldng.attendancewatchsheet;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttandanceListMethodActivity extends AppCompatActivity implements SpinnerHelper,NavigationMenuActions{

    private String selectedCourse;
    private JSONArray studentListResponse;
    private ArrayList<String[]> studentList;
    private List<String> courseInsList;
    private ArrayList<String> studentEmailInfo;
    private JSONObject attandanceResult;
    private Spinner courseSelectSpinner;
    private Spinner weekNoSpinner;
    private Response.Listener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_listmethod);

        studentList = new ArrayList<String[]>();
        courseInsList = new ArrayList<String>();
        studentEmailInfo= new ArrayList<>();
        attandanceResult = new JSONObject();

        //spinnerCreation
        courseSelectSpinner = (Spinner) findViewById(R.id.courseSelectSpinner);
        spinnerBuilder();
        weekNoSpinner = (Spinner) findViewById(R.id.weekNoSpinner);
        ArrayAdapter<CharSequence> arrayAdapterForWeekNo =
                ArrayAdapter.createFromResource(this, R.array.WeekNo, android.R.layout.simple_spinner_item);
        weekNoSpinner.setAdapter(arrayAdapterForWeekNo);
        navBarBuilder();



        Button submitButton = (Button) findViewById(R.id.SendButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttandanceData();
                Toast.makeText(AttandanceListMethodActivity.this,"Successfully sended.",Toast.LENGTH_LONG);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse  = new JSONObject(response);
                            int success = jsonResponse.getInt("success");
                            if(success == 1){
                                Toast.makeText(AttandanceListMethodActivity.this,"Successfully added",Toast.LENGTH_LONG);
                            }
                            else
                                Toast.makeText(AttandanceListMethodActivity.this,"Unsuccessful",Toast.LENGTH_LONG);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                    //Toast.makeText(AttandanceListMethodActivity.this,email,Toast.LENGTH_LONG);
                    AttandanceSendRequest attandanceSendRequest= new AttandanceSendRequest(attandanceResult,selectedCourse,listener);
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(attandanceSendRequest);


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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AttandanceListMethodActivity.this,android.R.layout.simple_spinner_dropdown_item,this.courseInsList);
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
        TableLayout tableLayout = findViewById(R.id.attandaceListTable);
        tableLayout.removeViews(1,tableLayout.getChildCount()-1);
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
                    TableLayout tableLayout = findViewById(R.id.attandaceListTable);



                    for (int j = 0 ; j < count; j++){
                        //row creation
                        TableRow row = new TableRow(getApplicationContext());
                        row.setPadding(20,0,20,0);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
                        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);

                        TextView col1 = new TextView(getApplicationContext());
                        TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
                        params1.weight = 4;
                        String[] tempNameList = studentList.get(j);
                        col1.setText(tempNameList[0]);
                        col1.setLayoutParams(params1);
                        CheckBox col2 = new CheckBox(getApplicationContext());
                        col2.setGravity(Gravity.CENTER_HORIZONTAL);
                        params2.weight = 1;
                        col2.setLayoutParams(params2);

                        //append col to row
                        row.addView(col1);
                        row.addView(col2);

                        //append row to table
                        tableLayout.addView(row);


                    }
                    //Toast.makeText(AttandanceListMethodActivity.this,"successfull respond",Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(AttandanceListMethodActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        };

        AttandanceStudentListRequest attandanceStudentListRequest= new AttandanceStudentListRequest(selectedCourse,response1Listener);
        RequestQueue queue = Volley.newRequestQueue(AttandanceListMethodActivity.this);
        queue.add(attandanceStudentListRequest);


        /*
        //col creation
        TextView col1 = new TextView(getApplicationContext());
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.weight = 4;
        for (String student: studentList ) {
            //row creation
            TableRow row = new TableRow(getApplicationContext());
            row.setPadding(20,0,20,0);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);

            col1.setText(student);
            col1.setLayoutParams(params1);
            CheckBox col2 = new CheckBox(getApplicationContext());
            col2.setGravity(Gravity.RIGHT);
            params2.weight = 1;
            col2.setLayoutParams(params2);

            //append col to row
            row.addView(col1);
            row.addView(col2);

            //append row to table
            tableLayout.addView(row);


        }
        */
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
                        intent = new Intent(AttandanceListMethodActivity.this,HomeActivityInstructor.class);
                        break;
                    case R.id.navigation_courses_instructor:
                        intent = new Intent(AttandanceListMethodActivity.this,Courses_Instructor.class);
                        break;
                    case R.id.navigation_attend:
                        intent = new Intent(AttandanceListMethodActivity.this,AttandanceSelectorActivity.class);
                        break;
                    case R.id.navigation_calendar:
                        intent = new Intent(AttandanceListMethodActivity.this,HomeActivityInstructor.class);
                        break;
                }
                intent.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(intent);
                return false;
            }
        });
    }

    public void setAttandanceData(){

        TableLayout tableLayout = findViewById(R.id.attandaceListTable);

        for (int i = 1 ; i < tableLayout.getChildCount();i++){
            View child = tableLayout.getChildAt(i);
            if(child instanceof TableRow){
                TableRow row = (TableRow) child;
                String emailAttandae = null;
                for (int j=0;j < row.getChildCount();j++){
                    View colChild = row.getChildAt(j);
                    if(j == 0){
                        TextView col = (TextView) colChild;
                        emailAttandae = "";
                        for (String[] tempList:studentList) {
                            if(tempList[0].equals(col.getText()))
                                emailAttandae = tempList[1]; 
                        }
                    }
                    else {
                        CheckBox col = (CheckBox) colChild;
                        try {
                            attandanceResult.put(emailAttandae,col.isChecked());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }
}
