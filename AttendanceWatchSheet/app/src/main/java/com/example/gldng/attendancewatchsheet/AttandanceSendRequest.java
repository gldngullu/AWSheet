package com.example.gldng.attendancewatchsheet;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AttandanceSendRequest extends StringRequest {

    private static final String Login_request_url ="http://awsheet.cf/connect/attendancesubmit.php";
    private Map<String,String> params ;

    public AttandanceSendRequest (JSONObject attandance, String courseCode, String weekno, Response.Listener<String> listener){
        super(Request.Method.POST,Login_request_url,listener,null);
        params= new HashMap<>();
        Iterator<String> keys = attandance.keys();
        while (keys.hasNext()){
            try {
                String key = keys.next();
                params.put(key, attandance.getBoolean(key) ? "1" : "0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        params.put("CourseCode",courseCode);
        params.put("weekNo",weekno);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
