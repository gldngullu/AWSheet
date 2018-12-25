package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordRequest extends StringRequest {

    private static final String forgetpass_request_url ="http://awsheet.cf/connect/forgetpass.php";
    private Map<String,String> params ;

    public ForgotPasswordRequest (String mail,String secretq,Response.Listener<String> listener){
        super(Request.Method.POST,forgetpass_request_url,listener,null);
        params= new HashMap<>();
        params.put("mailAdress",mail);
        params.put("secretq",secretq);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
