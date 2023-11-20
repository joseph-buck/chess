package handlers;

import services.ListGamesService;
import responses.ListGamesResponse;

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
        ListGamesService listGamesService = new ListGamesService();
        ListGamesResponse listGamesResponse
                = listGamesService.listGames(reqHeader);

        res.status(listGamesResponse.getCode());
        return new Gson().toJson(toMap(listGamesResponse));
    }

    // toMap - a helper function to convert a ListGameResponse object into a
    //          map that can be converted to Json.
    private Map<String, Object> toMap(ListGamesResponse listGamesResponse) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("games", listGamesResponse.getGames());
        newMap.put("message", listGamesResponse.getMessage());
        newMap.put("code", listGamesResponse.getCode());
        return newMap;
    }
}
