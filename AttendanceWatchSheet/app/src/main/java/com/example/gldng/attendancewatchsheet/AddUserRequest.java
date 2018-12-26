package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddUserRequest extends StringRequest {
    private static final String addUser_Request_Url = "http://awsheet.cf/connect/add_Instructor.php";
    private Map<String, String> params;

    public AddUserRequest(String mailAddress, String name, String surname, String password, Integer userType, Response.Listener<String> listener) {
        super(Method.POST, addUser_Request_Url, listener, null);
        params = new HashMap<>();
        params.put("mailAdress", mailAddress);
        params.put("name", name);
        params.put("surname", surname);
        params.put("password", password);
        params.put("userType", String.valueOf(userType));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
