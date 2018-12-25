package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordRequest extends StringRequest {

    private static final String resetpass_request_url ="http://awsheet.cf/connect/resetpass.php";
    private Map<String,String> params ;

    public ResetPasswordRequest (String mailAdress,String password,Response.Listener<String> listener){
        super(Request.Method.POST,resetpass_request_url,listener,null);
        params= new HashMap<>();
        params.put("mailAdress",mailAdress);
        params.put("password",password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
