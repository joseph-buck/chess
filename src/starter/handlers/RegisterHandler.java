package handlers;

import services.RegisterService;
import services.requests.RegisterRequest;
import services.responses.RegisterResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class RegisterHandler {
    private Response res;
    private Map<String, Object> reqBody;

    private String username;
    private String password;
    private String email;

    public RegisterHandler(Request req, Response res) {
        this.res = res;
        this.reqBody = new Gson().fromJson(req.body(), Map.class);
    }

    public String getResponse() {
        parseRequestBody();
        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse = registerService.register(
                new RegisterRequest(username, password, email));

        res.status(registerResponse.getCode());
        return new Gson().toJson(toMap(registerResponse));
    }

    // parseRequestBody - a helper function to extract values from the reqBody
    //                      Map and handle cases where the desired keys are
    //                      absent from the Map.
    private void parseRequestBody() {
        try {
            this.username = (String) this.reqBody.get("username");
            this.password = (String) this.reqBody.get("password");
            this.email = (String) this.reqBody.get("email");
        } catch (Exception ex) {
            this.username = "";
            this.password = "";
            this.email = "";
        }
    }

    // toMap - a helper function to convert a LoginResponse object into a
    //          map that can be converted to Json.
    private Map<String, Object> toMap(RegisterResponse registerResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("username", registerResponse.getUsername());
        newMap.put("authToken", registerResponse.getAuthToken());
        newMap.put("message", registerResponse.getMessage());
        return newMap;
    }
}
