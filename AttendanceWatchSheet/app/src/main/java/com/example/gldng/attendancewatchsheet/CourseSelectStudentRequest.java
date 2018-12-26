
package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class CourseSelectStudentRequest extends StringRequest {
        private static final String SelectCourse_request_URL = "http://awsheet.cf/connect/courseSelectInnstr.php";
        private Map<String, String> params;

        public CourseSelectStudentRequest(String Stumail, String coursecode, Response.Listener<String> listener) {
            super(Method.POST, SelectCourse_request_URL, listener, null);
            params = new HashMap<>();
            params.put("AmailAdress", Stumail);
            params.put("coursecode", coursecode);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }

    }
