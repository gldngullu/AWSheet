package com.example.gldng.attendancewatchsheet;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttandanceListMethodActivity extends AppCompatActivity implements SpinnerHelper,NavigationMenuActions{

    private String selectedCourse;
    private JSONArray studentListResponse;
    private ArrayList<String> studentList;
    private ArrayList<String> courseInsList;
    private ArrayList<String> studentEmailInfo;
    private JSONObject attandanceResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_listmethod);

        studentList = new ArrayList<>();
        courseInsList = new ArrayList<>();
        studentEmailInfo= new ArrayList<>();
        attandanceResult = new JSONObject();

        //spinnerCreation
        spinnerBuilder();

        navBarBuilder();

        studentListBuilder();

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

                for (String email:studentEmailInfo) {
                    Toast.makeText(AttandanceListMethodActivity.this,email,Toast.LENGTH_LONG);
                    AttandanceSendRequest attandanceSendRequest= new AttandanceSendRequest(email,"CSE101",listener );
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(attandanceSendRequest);
                }


            }
        });

    }

    @Override
    public void spinnerBuilder() {

        //spinner
        final Spinner spinner = (Spinner) findViewById(R.id.courseSelectSpinner);

        //final List<String> courseList = new ArrayList<>();

        Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int count = jsonObject.getInt("count");
                    for (int i = 0; i < count ; i++){
                        String temp = jsonObject.getString("courses"+i);
                        courseInsList.add(temp);
                    }

                }catch (JSONException e){e.printStackTrace();}
            }
        };

        // int socketTimeout = 30000;
        // RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        // stringRequest.setRetryPolicy(policy);
        String email = getIntent().getStringExtra("email");
        //Toast.makeText(AttandanceListMethodActivity.this,email,Toast.LENGTH_LONG);
        SpinnerDataRequest spinnerDataRequest = new SpinnerDataRequest(email,listener );
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(spinnerDataRequest);


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,courseInsList){
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
                        studentList.add(temp);
                        studentEmailInfo.add(temp2);
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
                        col1.setText(studentList.get(j));
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

        AttandanceStudentListRequest attandanceStudentListRequest= new AttandanceStudentListRequest("CSE101",response1Listener);
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

        for (int i = 0 ; i < tableLayout.getChildCount();i++){
            View child = tableLayout.getChildAt(i);
            if(child instanceof TableRow){
                TableRow row = (TableRow) child;
                for (int j=0;j < row.getChildCount();j++){
                    View colChild = row.getChildAt(j);
                    if(colChild instanceof TextView){
                        TextView col = (TextView) colChild;
                        try {
                            attandanceResult.put("name"+i,"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(colChild instanceof CheckBox){
                        CheckBox col = (CheckBox) colChild;
                        try {
                            attandanceResult.put("name"+i,col.isChecked());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }
}
