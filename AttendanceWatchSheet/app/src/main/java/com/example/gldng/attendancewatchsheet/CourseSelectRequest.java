package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CourseSelectRequest extends StringRequest {
    private static final String SelectCourse_request_URL = "http://awsheet.cf/connect/courseSelectInnstr.php";
    private Map<String, String> params;

    public CourseSelectRequest(String ImailAdress, String AmailAdress, String coursecode, String syllabus, Response.Listener<String> listener) {
        super(Method.POST, SelectCourse_request_URL, listener, null);
        params = new HashMap<>();
        System.out.println(ImailAdress+AmailAdress+syllabus+coursecode);
        params.put("AmailAdress", AmailAdress);
        params.put("ImailAdress", ImailAdress);
        params.put("coursecode", coursecode);
        params.put("syllabus", syllabus);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
