package com.example.gldng.attendancewatchsheet;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddUserRequest extends StringRequest {
    private static final String Register_request_url = "http://awsheet.cf/connect/register.php";
    private Map<String, String> params;

    public AddUserRequest(String mailAddress, String name, String surname, String password, Response.Listener<String> listener) {
        super(Method.POST, Register_request_url, listener, null);
        params = new HashMap<>();
        params.put("mailAddress", mailAddress);
        params.put("name", name);
        params.put("surname", surname);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
