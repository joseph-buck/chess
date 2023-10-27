package services.responses;

import java.util.HashMap;
import java.util.Map;


/**
 * LoginResponse --- Class for storing the data of a login response
 */
public class LoginResponse {
    private Map<String, Object> loginResponse = new HashMap<>();

    public LoginResponse(String username, String authToken,
                         String message, Integer code) {
        loginResponse.put("username", username);
        loginResponse.put("authToken", authToken);
        loginResponse.put("message", message);
        loginResponse.put("code", code);
    }

    public Object getUsername() {
        return loginResponse.get("username");
    }

    public Object getAuthToken() {
        return loginResponse.get("authToken");
    }

    public Object getMessage() {
        return loginResponse.get("message");
    }

    public int getCode() {
        return (Integer) loginResponse.get("code");
    }

}
