package handlers;

import models.AuthToken;
import services.Logout;
import services.responses.LogoutResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class LogoutHandler {
    private Response res;
    private String reqHeader;

    private AuthToken authToken;

    public LogoutHandler(Request req, Response res) {
        this.res = res;
        this.reqHeader = req.headers("Authorization");
    }

    public String getResponse() {
        Logout logoutService = new Logout();
        LogoutResponse logoutResponse = logoutService.logout(reqHeader);

        //TODO: Delete LoginRequest class
        System.out.println(logoutResponse.getCode());
        res.status(logoutResponse.getCode());
        return new Gson().toJson(toMap(logoutResponse));
    }

    private Map<String, Object> toMap(LogoutResponse logoutResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("message", logoutResponse.getMessage());
        return newMap;
    }
}
