package handlers;

import services.Register;
import services.requests.RegisterRequest;
import services.responses.RegisterResponse;

import spark.*;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;


public class RegisterHandler {
    private Request req;
    private Response res;

    private Map requestBody;

    private String username;
    private String password;
    private String email;


    public RegisterHandler(Request req, Response res) {
        this.req = req;
        this.res = res;
        this.requestBody = new Gson().fromJson(req.body(), Map.class);
    }

    public String getResponse() {
        parseRequestBody(this.req);
        Register registerService = new Register();
        RegisterResponse registerResponse = registerService.register(
                new RegisterRequest(username, password, email));

        res.status(registerResponse.getCode());
        return new Gson().toJson(toMap(registerResponse));

    }

    private void parseRequestBody(Request req) {
        this.username = (String) this.requestBody.get("username");
        this.password = (String) this.requestBody.get("password");
        this.email = (String) this.requestBody.get("email");
    }

    private Map<String, Object> toMap(RegisterResponse registerResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("username", registerResponse.getUsername());
        newMap.put("authToken", registerResponse.getAuthToken());
        newMap.put("message", registerResponse.getMessage());
        return newMap;
    }
}
