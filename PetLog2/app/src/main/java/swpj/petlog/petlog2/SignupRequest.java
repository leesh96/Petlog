package swpj.petlog.petlog2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends StringRequest {
    final static private String URL="http://128.199.106.86/SignupTest.php";
    private Map<String, String>map;

    public SignupRequest(String u_id, String u_pw, String u_name, String u_nickname, String u_bdy, Response.Listener<String>listener) {
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("u_id", u_id);
        map.put("u_pw", u_pw);
        map.put("u_name", u_name);
        map.put("u_nickname", u_nickname);
        map.put("u_bdy", u_bdy);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
