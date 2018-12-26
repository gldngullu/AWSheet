package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AttandanceSendRequest extends StringRequest {

    private static final String Login_request_url ="http://awsheet.cf/connect/attendancesubmit.php";
    private Map<String,String> params ;

    public AttandanceSendRequest (String mail,String courseCode,Response.Listener<String> listener){
        super(Request.Method.POST,Login_request_url,listener,null);
        params= new HashMap<>();
        params.put("Student_Mail_Adress",mail);
        params.put("CourseCode",courseCode);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
