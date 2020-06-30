package com.swp.petlog.app;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequestId extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://128.199.106.86/validateId.php";
    private Map<String,String> map;

    public ValidateRequestId(String userID, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
