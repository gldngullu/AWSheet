package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetInstructorCoursesRequest extends StringRequest  {

        private static final String GetCourses = "http://awsheet.cf/connect/takenCourseInstructor.php";
        private Map<String, String> params;

        public GetInstructorCoursesRequest(String emailIns,Response.Listener<String> listener) {
            super(Method.POST, GetCourses, listener, null);
            params = new HashMap<>();
            params.put("mail", emailIns);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }

    }

