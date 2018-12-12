package com.example.gldng.attendancewatchsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AttandanceListMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_listmethod);

        TableLayout tableLayout = findViewById(R.id.attandaceListTable);
        TableRow row = new TableRow(getApplicationContext());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
        TextView col1 = new TextView(getApplicationContext());
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.weight = 4;
        col1.setText("b");
        col1.setLayoutParams(params1);
        TextView col2 = new TextView(getApplicationContext());
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.weight = 1;
        col2.setText("a");
        col2.setLayoutParams(params2);
        row.addView(col1);
        row.addView(col2);
        tableLayout.addView(row);

    }
}
