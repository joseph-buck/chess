package services.requests;


import java.util.*;

/**
 * LoginRequest --- Class for storing login request information.
 */
public class LoginRequest {
    private Map<String, Object> loginRequest = new HashMap<>();

    public LoginRequest(Object username, Object password) {
        loginRequest.put("username", username);
        loginRequest.put("password", password);
    }

    public String getUsername() {
        return (String) loginRequest.get("username");
    }

    public String getPassword() {
        return (String) loginRequest.get("password");
    }

    public void setUsername(String username) {
        loginRequest.replace("username", username);
    }

    public void setPassword(String password) {
        loginRequest.replace("password", password);
    }
}