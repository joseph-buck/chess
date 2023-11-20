package handlers;

import services.LoginService;
import requests.LoginRequest;
import responses.LoginResponse;

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
        LoginService loginService = new LoginService();
        LoginResponse loginResponse = loginService.login(
                new LoginRequest(this.username, this.password));

        res.status(loginResponse.getCode());
        return new Gson().toJson(toMap(loginResponse));
    }

    // parseRequestBody - a helper function to extract values from the reqBody
    //                      Map and handle cases where the desired keys are
    //                      absent from the Map.
    private void parseRequestBody() {
        try {
            this.username = (String) this.reqBody.get("username");
            this.password = (String) this.reqBody.get("password");
        } catch (Exception ex) {
            this.username = "";
            this.password = "";
        }
    }

    // toMap - a helper function to convert a LoginResponse object into a
    //          map that can be converted to Json.
    private Map<String, Object> toMap(LoginResponse loginResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("username", loginResponse.getUsername());
        newMap.put("authToken", loginResponse.getAuthToken());
        newMap.put("message", loginResponse.getMessage());
        newMap.put("code", loginResponse.getCode());
        return newMap;
    }
}
