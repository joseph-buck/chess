package handlers;

import services.JoinGameService;
import requests.JoinGameRequest;
import responses.JoinGameResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class JoinGameHandler {
    private Response res;

    private String reqHeader;
    private Map reqBody;

    private String playerColor;
    private Integer gameID;

    public JoinGameHandler(Request req, Response res) {
        this.res = res;
        this.reqHeader = req.headers("Authorization");
        this.reqBody = new Gson().fromJson(req.body(), Map.class);
    }

    public String getResponse() {
        parseRequestBody();
        JoinGameService joinGameService = new JoinGameService();
        JoinGameRequest newRequest = new JoinGameRequest(this.playerColor,
                this.gameID, this.reqHeader);
        JoinGameResponse joinGameResponse
                = joinGameService.joinGame(newRequest);

        res.status(joinGameResponse.getCode());
        return new Gson().toJson(toMap(joinGameResponse));
    }

    // parseRequestBody - a helper function to extract values from the reqBody
    //                      Map and handle cases where the desired keys are
    //                      absent from the Map.
    private void parseRequestBody() {
        try {
            this.playerColor = (String) reqBody.get("playerColor");
            this.gameID = ((Double) reqBody.get("gameID")).intValue();
        } catch (Exception ex) {
            this.playerColor = "";
            this.gameID = null;
        }
    }

    // toMap - a helper function to convert a JoinGameResponse object into a
    //          map that can be converted to Json.
    private HashMap<String, Object> toMap(JoinGameResponse joinGameResponse) {
        HashMap<String, Object> newMap = new HashMap<>();
        newMap.put("message", joinGameResponse.getMessage());
        newMap.put("code", joinGameResponse.getCode());
        return newMap;
    }
}
