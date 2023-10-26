package services.responses;

import com.google.gson.annotations.Expose;
import models.*;

import java.util.*;


/**
 * RegisterResponse --- Class for storing data of a register response.
 */
public class RegisterResponse {
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

    public void setUsername(String username) {
        registerResponse.replace("username", username);
    }

    public void setAuthToken(String authToken) {
        registerResponse.replace("authToken", authToken);
    }

    public void setMessage(String message) {
        registerResponse.replace("message", message);
    }

    public void setCode(Integer code) {
        registerResponse.put("code", code);
    }
}
