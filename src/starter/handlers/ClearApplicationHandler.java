package handlers;

import services.ClearApplicationService;
import responses.ClearApplicationResponse;
import spark.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class ClearApplicationHandler {
    private Response res;

    public ClearApplicationHandler(Request req, Response res) {
        this.res = res;
    }

    public String getResponse() {
        ClearApplicationService clearApplicationService
                = new ClearApplicationService();
        ClearApplicationResponse clearApplicationResponse
                = clearApplicationService.clearApplication();

        res.status(clearApplicationResponse.getCode());
        res.type("application/json");
        return new Gson().toJson(toMap(clearApplicationResponse));
    }

    // toMap - a helper function to convert a ClearApplicationResponse object
    //          into a map that can be converted to Json.
    public Map<String, Object> toMap(
            ClearApplicationResponse clearApplicationResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("message", clearApplicationResponse.getMessage());
        newMap.put("code", clearApplicationResponse.getCode());
        return newMap;
    }
}
