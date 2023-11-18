package requests;

import java.util.HashMap;
import java.util.Map;


/**
 * RegisterRequest -- Class for storing register request data.
 */
public class RegisterRequest {
    Map<String, Object> registerRequest = new HashMap<>();

    public RegisterRequest(Object username, Object password, Object email) {
        registerRequest.put("username", username);
        registerRequest.put("password", password);
        registerRequest.put("email", email);
    }

    public Object getUsername() {
        return registerRequest.get("username");
    }

    public Object getPassword() {
        return registerRequest.get("password");
    }

    public Object getEmail() {
        return registerRequest.get("email");
    }

}
