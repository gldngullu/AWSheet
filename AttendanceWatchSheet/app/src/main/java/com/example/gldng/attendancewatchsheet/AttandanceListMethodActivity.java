package com.example.gldng.attendancewatchsheet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttandanceListMethodActivity extends AppCompatActivity implements SpinnerHelper{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_listmethod);


        //adding data to table
        TableLayout tableLayout = findViewById(R.id.attandaceListTable);

        //row creation
        TableRow row = new TableRow(getApplicationContext());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);

        //col creation
        TextView col1 = new TextView(getApplicationContext());
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.weight = 4;
        col1.setText("b");
        col1.setLayoutParams(params1);
        CheckBox col2 = new CheckBox(getApplicationContext());
        col2.setGravity(Gravity.RIGHT);
        params2.weight = 1;
        col2.setText("a");
        col2.setLayoutParams(params2);

        //append col to row
        row.addView(col1);
        row.addView(col2);

        //append row to table
        tableLayout.addView(row);

        //spinnerCreation
        spinnerBuilder();
    }

    @Override
    public void spinnerBuilder() {

        //spinner
        final Spinner spinner = (Spinner) findViewById(R.id.courseSelectSpinner);

        String[] courses = new String[]{
                "Select a course",
                "a",
                "b"
        };

        final List<String> courseList = new ArrayList<String>(Arrays.asList(courses));

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
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
