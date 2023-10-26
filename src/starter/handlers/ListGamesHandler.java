package handlers;

import models.*;
import services.ListGames;
import services.responses.ListGamesResponse;

import com.google.gson.Gson;
import spark.*;

import java.util.*;


public class ListGamesHandler {
    private Response res;
    private String reqHeader;

    public ListGamesHandler(Request req, Response res) {
        this.res = res;
        this.reqHeader = req.headers("Authorization");
    }

    public String getResponse() {
        ListGames listGamesService = new ListGames();
        ListGamesResponse listGamesResponse = listGamesService.listGames(reqHeader);

        res.status(listGamesResponse.getCode());
        return new Gson().toJson(toMap(listGamesResponse));
    }

    private Map<String, Object> toMap(ListGamesResponse listGamesResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("games", listGamesResponse.getGames());
        newMap.put("message", listGamesResponse.getMessage());
        return newMap;
    }
}
