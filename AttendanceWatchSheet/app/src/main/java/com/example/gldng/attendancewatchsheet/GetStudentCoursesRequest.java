package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class GetStudentCoursesRequest extends StringRequest{
    private static final String GetCourses = "http://awsheet.cf/connect/takenCourseStudent.php";
    private Map<String, String> params;

    public GetStudentCoursesRequest(String mail,Response.Listener<String> listener) {
        super(Request.Method.POST, GetCourses, listener, null);
        params = new HashMap<>();
        params.put("mailAdress",mail);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
