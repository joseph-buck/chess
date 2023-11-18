package handlers;

import services.CreateGameService;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

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
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(
                new CreateGameRequest(this.gameName, this.reqHeader));

        res.status(createGameResponse.getCode());
        return new Gson().toJson(toMap(createGameResponse));
    }

    // parseRequestBody - a helper function to extract values from the reqBody
    //                      Map and handle cases where the desired key is
    //                      absent from the Map.
    private void parseRequestBody() {
        try {
            this.gameName = (String) this.reqBody.get("gameName");
        } catch (Exception ex) {
            this.gameName = "";
        }
    }

    // toMap - a helper function to convert a CreateGameResponse object into a
    //          map that can be converted to Json.
    private Map<String, Object> toMap(CreateGameResponse createGameResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("gameID", createGameResponse.getGameID());
        newMap.put("message", createGameResponse.getMessage());
        return newMap;
    }
}
