package com.swp.petlog.app;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://128.199.106.86/SignUp.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String u_id, String u_pw, String u_name, String u_nickname,String u_bdy, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", u_id);
        map.put("userPassword", u_pw);
        map.put("userName", u_name);
        map.put("userNick", u_nickname);
        map.put("userBdy", u_bdy);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
