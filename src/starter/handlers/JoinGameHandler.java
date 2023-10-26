package handlers;

import models.*;
import services.JoinGame;
import services.requests.JoinGameRequest;
import services.responses.JoinGameResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class JoinGameHandler {
    //TODO: Remove the req and res objects?
    private Request req;
    private Response res;

    private String reqHeader;
    private Map reqBody;

    private String playerColor;
    private Integer gameID;

    public JoinGameHandler(Request req, Response res) {
        this.res = res;
        this.req = req;
        this.reqHeader = req.headers("Authorization");
        this.reqBody = new Gson().fromJson(req.body(), Map.class);
    }

    public String getResponse() {
        parseRequestBody();
        JoinGame joinGameService = new JoinGame();
        JoinGameRequest newRequest = new JoinGameRequest(this.playerColor,
                this.gameID, this.reqHeader);
        JoinGameResponse joinGameResponse
                = joinGameService.joinGame(newRequest);

        res.status(joinGameResponse.getCode());
        return new Gson().toJson(toMap(joinGameResponse));
    }

    private void parseRequestBody() {
        try {
            this.playerColor = (String) reqBody.get("playerColor");
            this.gameID = ((Double) reqBody.get("gameID")).intValue(); //gameId became a double
        } catch (Exception ex) {
            this.playerColor = "";
            this.gameID = null;
        }
    }

    private HashMap<String, Object> toMap(JoinGameResponse joinGameResponse) {
        HashMap<String, Object> newMap = new HashMap<>();
        newMap.put("message", joinGameResponse.getMessage());
        return newMap;
    }
}
