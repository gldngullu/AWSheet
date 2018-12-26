package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddCourseRequest extends StringRequest {
    private static final String addCourse_Request_Url = "http://awsheet.cf/connect/add_course.php";
    private Map<String, String> params;

    public AddCourseRequest(String courseName, String courseCode, String weeklySchedule, Response.Listener<String> listener) {
        super(Method.POST, addCourse_Request_Url, listener, null);
        params = new HashMap<>();
        params.put("courseName", courseName);
        params.put("courseCode", courseCode);
        params.put("weeklySchedule", weeklySchedule);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}