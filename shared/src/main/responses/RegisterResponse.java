package responses;

import java.util.*;


/**
 * RegisterResponse --- Class for storing data of a register response.
 */
public class RegisterResponse extends Response {
    Map<String, Object> registerResponse = new HashMap<>();

    public RegisterResponse(String username, String authToken,
                            String message, Integer code) {
        registerResponse.put("username", username);
        registerResponse.put("authToken", authToken);
        registerResponse.put("message", message);
        registerResponse.put("code", code);
    }

    public Object getUsername() {
        return registerResponse.get("username");
    }

    public Object getAuthToken() {
        return registerResponse.get("authToken");
    }

    public Object getMessage() {
        return registerResponse.get("message");
    }

    public int getCode() {
        return (Integer) registerResponse.get("code");
    }

}
