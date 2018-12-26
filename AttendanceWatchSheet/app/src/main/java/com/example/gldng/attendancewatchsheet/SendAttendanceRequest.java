package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendAttendanceRequest extends StringRequest {

    private static final String sendattendance_request_url ="http://awsheet.cf/connect/login.php";
    private Map<String,String> params ;

    public SendAttendanceRequest (String coursecode,String studentmail,Response.Listener<String> listener){
        super(Request.Method.POST,sendattendance_request_url,listener,null);
        params= new HashMap<>();
        params.put("coursecode",coursecode);
        params.put("studentmail",studentmail);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
