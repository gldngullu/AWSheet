package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SpinnerDataRequest extends StringRequest {

    private static final String Register_request_url = "http://awsheet.cf/connect/takenCourseInstructor.php";
    private Map<String, String> params;

    public SpinnerDataRequest(String mail,Response.Listener<String> listener){
        super(Method.POST, Register_request_url, listener, null);
        params = new HashMap<>();
        params.put("mailAddress", mail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
