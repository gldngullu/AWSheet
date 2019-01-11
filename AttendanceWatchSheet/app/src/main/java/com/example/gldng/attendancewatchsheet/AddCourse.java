package com.example.gldng.attendancewatchsheet;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class AddCourse extends Fragment implements View.OnClickListener {

    EditText courseName;
    EditText courseCode;
    String weeklySchedule;
    ImageButton addScheduleButton;
    Spinner spinnerForDays,spinnerForHours;
    ListView scheduleHoursListView;
    static ArrayList<String> StringsForListView = new ArrayList<>();
    //static ArrayList<String> allCourses = new ArrayList<>();
    ArrayAdapter<String> dataAdapter;
    private String allCoursesDefined = "http://awsheet.cf/connect/reallyAllCourses.php";
    //private JSONArray resultCourseName;
    private boolean isSuccessful = false;
    private Button createCourseButton;
    boolean isEmpty;


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        spinnerForDays = (Spinner) getView().findViewById(R.id.SpinnerForDays);
        spinnerForHours = (Spinner) getView().findViewById(R.id.SpinnerForHours);
        ArrayAdapter<CharSequence> arrayAdapterForDays =
                ArrayAdapter.createFromResource(this.getContext(), R.array.Days, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForHours =
                ArrayAdapter.createFromResource(this.getContext(), R.array.Hours, android.R.layout.simple_spinner_item);
        spinnerForDays.setAdapter(arrayAdapterForDays);
        spinnerForHours.setAdapter(arrayAdapterForHours);
        courseName = getView().findViewById(R.id.courseName);
        courseCode = getView().findViewById(R.id.courseCode);
        scheduleHoursListView = getView().findViewById(R.id.scheduleHoursListView);
        addScheduleButton = getView().findViewById(R.id.addScheduleButton);
        createCourseButton = getView().findViewById(R.id.createCourseButton);
        createCourseButton.setEnabled(false);
        dataAdapter = new ArrayAdapter<String>
                (this.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, StringsForListView);
        scheduleHoursListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                StringsForListView.remove(position);
                dataAdapter.notifyDataSetChanged();
                return true;
            }
        });

        addScheduleButton.setOnClickListener(this);
        courseCode.addTextChangedListener(addCourseTextWatcher);
        courseName.addTextChangedListener(addCourseTextWatcher);
    }

    @Override
    public void onClick(View v) {
        AddDataInListView(v);
    }

    private TextWatcher addCourseTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String courseNameText = courseName.getText().toString().trim();
            String courseCodeText = courseCode.getText().toString().trim();
            createCourseButton.setEnabled(!courseNameText.isEmpty() && !courseCodeText.isEmpty() && !isListViewEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public boolean isListViewEmpty(){
        isEmpty = false;
        if(dataAdapter.getCount() == 0)
            isEmpty = true;
        return isEmpty;
    }

    public String getWeeklySchedule(){
        String weeklySchedule= "";
        ArrayList<String> scheduleInfo = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        sb.append(weeklySchedule);
        for (String string : this.StringsForListView) {
            String[] splitStr = string.split("\\s+");
            switch (splitStr[0]){
                case "Monday":
                    sb.append("M");
                    break;
                case "Tuesday":
                    sb.append("T");
                    break;
                case "Wednesday":
                    sb.append("W");
                    break;
                case "Thursday":
                    sb.append("Th");
                    break;
                case "Friday":
                    sb.append("F");
                    break;
            }
            sb.append(splitStr[1]);
            scheduleInfo.add(sb.toString());
            sb.delete(0, sb.length());
        }
        Collections.sort(scheduleInfo.subList(1, scheduleInfo.size()));
        for (int i = 0 ; i < scheduleInfo.size(); i++){
            weeklySchedule += scheduleInfo.get(i);
        }
        return weeklySchedule;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment, container, false);
        return view;
    }

    public boolean addNewCourse(){
            final String courseNameText = courseName.getText().toString();
            final String courseCodeText = courseCode.getText().toString();
            weeklySchedule = getWeeklySchedule();
            final String weeklyScheduleText = weeklySchedule;

            Response.Listener<String> responselistener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse  = new JSONObject(response);
                        int success = jsonResponse.getInt("success");
                        if(success == 1){
                            //Toast.makeText(AddCourse.this.getContext(), "This hour is already on the schedule", Toast.LENGTH_SHORT).show();
                            isSuccessful = true;
                        }
                        else
                            isSuccessful = false;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            AddCourseRequest addCourseRequest = new AddCourseRequest(courseNameText, courseCodeText, weeklyScheduleText, responselistener);
            RequestQueue queue = Volley.newRequestQueue(AddCourse.this.getContext());
            queue.add(addCourseRequest);

            return isSuccessful;
    }

    public void AddDataInListView(View view) {
        int ButtonId = view.getId();
        switch (ButtonId) {
            case R.id.addScheduleButton:
                ListView listView = getView().findViewById(R.id.scheduleHoursListView);
                Spinner spinnerdays = getView().findViewById(R.id.SpinnerForDays);
                Spinner spinnerhours = getView().findViewById(R.id.SpinnerForHours);
                String data = spinnerdays.getSelectedItem().toString() + " " + spinnerhours.getSelectedItem().toString();
                if(StringsForListView.contains(data)){
                    Toast.makeText(this.getContext(), "This hour is already on the schedule", Toast.LENGTH_SHORT).show();
                } else if(StringsForListView.size() == 8){
                    Toast.makeText(this.getContext(), "Reached to maximum course hours",Toast.LENGTH_SHORT).show();
                } else{
                    StringsForListView.add(data);
                }
                listView.setAdapter(dataAdapter);
                break;
        }
    }


}




