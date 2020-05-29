package swpj.petlog.petlog2.mypet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddMypetInfoRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://128.199.106.86/addMypet.php";
    private Map<String, String> map;

    public AddMypetInfoRequest(String PetName, String PetSex, String PetSpecies, String PetAge, String PetBday, String PetFace, String PetOwner, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("petName", PetName);
        map.put("petSex", PetSex);
        map.put("petSpecies", PetSpecies);
        map.put("petAge", PetAge);
        map.put("petBday", PetBday);
        map.put("petFace", PetFace);
        map.put("petOwner", PetOwner);

    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }

}
