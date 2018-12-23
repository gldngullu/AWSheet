package com.example.gldng.attendancewatchsheet;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExamAddRequest extends StringRequest {

    private static final String ExamAddReq ="http://awsheet.cf/connect/gradeAdder.php";
    private Map<String,String> params ;

    public ExamAddRequest(String coursecode,String ExamName,String PercentGrade,Response.Listener<String> listener ){
        super(Request.Method.POST,ExamAddReq,listener, null);
        params=new HashMap<>();
        params.put("ExamName",ExamName);
        params.put("PercentGrade",PercentGrade);
        params.put("coursecode",coursecode);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
