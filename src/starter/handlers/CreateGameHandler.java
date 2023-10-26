package handlers;

import services.CreateGame;
import services.requests.CreateGameRequest;
import services.responses.CreateGameResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class CreateGameHandler {
    private Response res;
    private Map reqBody;
    private String reqHeader;

    private String gameName;

    public CreateGameHandler(Request req, Response res) {
        this.res = res;
        this.reqBody = new Gson().fromJson(req.body(), Map.class);
        this.reqHeader = req.headers("Authorization");
    }

    public String getResponse() {
        parseRequestBody();
        CreateGame createGameService = new CreateGame();
        CreateGameResponse createGameResponse = createGameService.createGame(
                new CreateGameRequest(this.gameName, this.reqHeader));

        res.status(createGameResponse.getCode());
        return new Gson().toJson(toMap(createGameResponse));
    }

    private void parseRequestBody() {
        try {
            this.gameName = (String) this.reqBody.get("gameName");
        } catch (Exception ex) {
            this.gameName = "";
        }
    }

    private Map<String, Object> toMap(CreateGameResponse createGameResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("gameID", createGameResponse.getGameID());
        newMap.put("message", createGameResponse.getMessage());
        return newMap;
    }
}
