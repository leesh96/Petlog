package com.swp.petlog.petsta;

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddPhotoRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://128.199.106.86/addPetstaPost.php";
    private Map<String, String> map;

    public AddPhotoRequest(String nickname,String contents, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("contents", contents);

    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}