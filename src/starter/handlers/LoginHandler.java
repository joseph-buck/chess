package handlers;

import services.Login;
import services.requests.LoginRequest;
import services.responses.LoginResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class LoginHandler {
    private Response res;
    private Map reqBody;

    private String username;
    private String password;

    public LoginHandler(Request req, Response res) {
        this.res = res;
        this.reqBody = new Gson().fromJson(req.body(), Map.class);
    }

    public String getResponse() {
        parseRequestBody();
        Login loginService = new Login();
        LoginResponse loginResponse = loginService.login(
                new LoginRequest(this.username, this.password));

        res.status(loginResponse.getCode());
        return new Gson().toJson(toMap(loginResponse));
    }

    private void parseRequestBody() {
        try {
            this.username = (String) this.reqBody.get("username");
            this.password = (String) this.reqBody.get("password");
        } catch (Exception ex) {
            this.username = "";
            this.password = "";
        }
    }

    private Map<String, Object> toMap(LoginResponse loginResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("username", loginResponse.getUsername());
        newMap.put("authToken", loginResponse.getAuthToken());
        newMap.put("message", loginResponse.getMessage());
        return newMap;
    }
}
