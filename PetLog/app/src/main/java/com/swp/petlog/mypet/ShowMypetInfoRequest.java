package com.swp.petlog.mypet;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ShowMypetInfoRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://128.199.106.86/showMypet.php";
    private Map<String, String> map;

    public ShowMypetInfoRequest (String u_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", u_id);

    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
