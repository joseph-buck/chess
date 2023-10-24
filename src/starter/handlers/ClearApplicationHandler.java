package handlers;

import services.ClearApplication;
import services.responses.ClearApplicationResponse;
import spark.*;
import com.google.gson.Gson;


public class ClearApplicationHandler {
    private Request req;
    private Response res;

    private boolean success;

    public ClearApplicationHandler(Request req, Response res) {
        this.req = req;
        this.res = res;
        success = false;
    }

    public String clearApplication() {
        ClearApplication clearApplicationService = new ClearApplication();
        ClearApplicationResponse clearApplicationResponse
                = clearApplicationService.clearApplication();
        res.status(clearApplicationResponse.getCode());
        return new Gson().toJson(clearApplicationResponse);
    }

}
