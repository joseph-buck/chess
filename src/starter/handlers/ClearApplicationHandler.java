package handlers;

import services.ClearApplication;
import services.responses.ClearApplicationResponse;
import spark.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class ClearApplicationHandler {
    private Request req;
    private Response res;

    public ClearApplicationHandler(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    public String getResponse() {
        ClearApplication clearApplicationService = new ClearApplication();
        ClearApplicationResponse clearApplicationResponse
                = clearApplicationService.clearApplication();
        res.status(clearApplicationResponse.getCode());
        res.type("application/json");
        return new Gson().toJson(toMap(clearApplicationResponse));
    }

    public Map<String, Object> toMap(ClearApplicationResponse clearApplicationResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("message", clearApplicationResponse.getMessage());
        return newMap;
    }
}
