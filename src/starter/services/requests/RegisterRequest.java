package services.requests;

import java.util.HashMap;
import java.util.Map;


/**
 * RegisterRequest -- Class for storing register request data.
 */
public class RegisterRequest {
    Map<String, Object> registerRequest = new HashMap<>();

    /**
     * Constructor - Initializes fields to user defined values.
     * @param username User defined username.
     * @param password User defined password.
     * @param email User defined email.
     */
    public RegisterRequest(String username, String password, String email) {
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

    public void setUsername(String username) {
        registerRequest.put("username", username);
    }

    public void setPassword(String password) {
        registerRequest.put("password", password);
    }

    public void setEmail(String email) {
        registerRequest.put("email", email);
    }
}
