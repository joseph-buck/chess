package handlers;

import services.LogoutService;
import responses.LogoutResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class LogoutHandler {
    private Response res;
    private String reqHeader;

    public LogoutHandler(Request req, Response res) {
        this.res = res;
        this.reqHeader = req.headers("Authorization");
    }

    public String getResponse() {
        LogoutService logoutService = new LogoutService();
        LogoutResponse logoutResponse = logoutService.logout(reqHeader);

        res.status(logoutResponse.getCode());
        return new Gson().toJson(toMap(logoutResponse));
    }

    // toMap - a helper function to convert a LogoutResponse object into a
    //          map that can be converted to Json.
    private Map<String, Object> toMap(LogoutResponse logoutResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("message", logoutResponse.getMessage());
        newMap.put("code", logoutResponse.getCode());
        return newMap;
    }
}
