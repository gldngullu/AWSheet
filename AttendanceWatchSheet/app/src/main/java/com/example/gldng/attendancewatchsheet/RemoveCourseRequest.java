package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RemoveCourseRequest extends StringRequest{
    private static final String removeCourse_Request_Url = "http://awsheet.cf/connect/remove_course.php";
    private Map<String, String> params;

    public RemoveCourseRequest(String courseCode, Response.Listener<String> listener) {
        super(Request.Method.GET, removeCourse_Request_Url, listener, null);
        params = new HashMap<>();
        params.put("courseCode", courseCode);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
