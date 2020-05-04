package swpj.petlog.petlog2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL="http://128.199.106.86/LoginTest.php";
    private Map<String, String>map;

    public LoginRequest(String u_id, String u_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("u_id", u_id);
        map.put("u_pw", u_pw);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
