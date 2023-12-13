package serverfacade;

import clientwebsocket.WsClient;
import com.google.gson.Gson;
import requests.*;
import responses.*;
import webSocketMessages.userCommands.userGameCommands.JoinPlayerCommand;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ServerFacade {
    // Register
    public RegisterResponse register(RegisterRequest regReq) {
        try {
            URL url = new URL("http://localhost:8080/user");
            Map responseMap = makePostRequest(url, regReq);
            //RegisterResponse regRes = getResponse(). Turn this stuff into a general function?
            if (responseMap != null) {
                String username = (String) regReq.getUsername();
                String authToken = (String) responseMap.get("authToken");
                String message = (String) responseMap.get("message");
                Integer code = (responseMap.get("code") == null)? 200
                        : (int) responseMap.get("code");
                return new RegisterResponse(username, authToken, message, code);
            } else {
                return new RegisterResponse(null, null, null, 403);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Login
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            URL url = new URL("http://localhost:8080/session");
            Map responseMap = makePostRequest(url, loginRequest);
            if (responseMap != null) {
                String username = (String) loginRequest.getUsername();
                String authToken = (String) responseMap.get("authToken");
                String message = (String) responseMap.get("message");
                Integer code = (responseMap.get("code") == null)? 200
                        : (int) responseMap.get("code");
                return new LoginResponse(username, authToken, message, code);
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Create game
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8080/game");

            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", createGameRequest.getAuthToken());

            try (var outputStream = connection.getOutputStream()) {
                Map createGameRequestMap = createGameRequest.getMap();
                createGameRequestMap.remove("authToken");
                var jsonBody = new Gson().toJson(createGameRequestMap);
                outputStream.write(jsonBody.getBytes());
            }
            connection.connect();
            try (var inputStream = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Map responseMap = new Gson().fromJson(inputStreamReader, Map.class);
                if (responseMap != null) {
                    int gameID;
                    if (responseMap.get("gameID") != null) {
                        gameID = ((Double) responseMap.get("gameID")).intValue();
                    } else {
                        gameID = 0;
                    }
                    String message = (String) responseMap.get("message");
                    int code;
                    if (responseMap.get("code") != null) {
                        code = ((Double) responseMap.get("code")).intValue();
                    } else {
                        code = 0;
                    }
                    return new CreateGameResponse(gameID, message, code);
                } else {
                    return new CreateGameResponse(0,
                            connection.getResponseMessage(),
                            connection.getResponseCode());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
            try {
                assert connection != null;
                return new CreateGameResponse(0,
                        connection.getResponseMessage(),
                        connection.getResponseCode());
            } catch (IOException ex1) {
                return null;
            }
        }
    }

    // List games
    public ListGamesResponse listGames(String authToken) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8080/game");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", authToken);
            connection.setDoOutput(false);
            connection.connect();
            try (var inputStream = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Map responseMap = new Gson().fromJson(inputStreamReader, Map.class);
                if (responseMap != null) {
                    ArrayList games = (ArrayList) responseMap.get("games");
                    String message = (responseMap.get("message") == null)? ""
                            : (String) responseMap.get("message");
                    int code = connection.getResponseCode();
                    return new ListGamesResponse(games, message, code);
                } else {
                    return new ListGamesResponse(new ArrayList<>(),
                            connection.getResponseMessage(),
                            connection.getResponseCode());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
            try {
                assert connection != null;
                return new ListGamesResponse(new ArrayList<>(),
                        connection.getResponseMessage(),
                        connection.getResponseCode());
            } catch (IOException ex1) {
                return null;
            }
        }
    }

    // Join game and Observe game
    /*public WsClient joinGame(JoinGameRequest joinGameRequest) {
        WsClient ws = null;
        try {
            JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(joinGameRequest);
            ws = new WsClient();
            ws.send(new Gson().toJson(joinPlayerCommand));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ws;
    }*/
    public JoinGameResponse joinGame(JoinGameRequest joinGameRequest) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8080/game");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", joinGameRequest.getAuthToken());

            connection.setDoOutput(true);

            try (var outputStream = connection.getOutputStream()) {
                Map joinGameRequestMap = joinGameRequest.getMap();
                joinGameRequestMap.remove("authToken");
                var jsonBody = new Gson().toJson(joinGameRequestMap);
                outputStream.write(jsonBody.getBytes());
            }

            connection.connect();
            return new JoinGameResponse(connection.getResponseMessage(),
                    connection.getResponseCode());

        } catch (IOException ex) {
            System.out.println(ex);
            try {
                assert connection != null;
                return new JoinGameResponse(connection.getResponseMessage(),
                        connection.getResponseCode());
            } catch (IOException ex1) {
                return null;
            }
        }
    }

    // Logout
    public LogoutResponse logout(String authToken) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8080/session");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", authToken);
            connection.connect();
            return new LogoutResponse(connection.getResponseMessage(),
                    connection.getResponseCode());
            /*try (var inputStream = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Map responseMap = new Gson().fromJson(inputStreamReader, Map.class);
                if (responseMap == null) {
                    return null;
                } else if (!responseMap.isEmpty()) {
                    String message = (String) responseMap.get("message");
                    Integer code = ((Double) responseMap.get("code")).intValue();
                    return new LogoutResponse(message, code);
                } else {
                    return new LogoutResponse("", 200);
                }
            }*/
        } catch (IOException ex) {
            System.out.println(ex);
            try {
                assert connection != null;
                return new LogoutResponse(connection.getResponseMessage(),
                        connection.getResponseCode());
            } catch (IOException ex1) {
                return null;
            }
        }
    }

    public Map makePostRequest(URL url, Request httpReq) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            try (var outputStream = connection.getOutputStream()) {
                var jsonBody = new Gson().toJson(httpReq.getMap());
                outputStream.write(jsonBody.getBytes());
            }
            connection.connect();
            try (var inputStream = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                Map returnMap = new Gson().fromJson(inputStreamReader, Map.class);
                int responseCode = connection.getResponseCode();
                returnMap.put("code", responseCode);
                if (returnMap.isEmpty() || (returnMap == null)) {
                    returnMap = new HashMap<String, Object>();
                    returnMap.put("username", "");
                    returnMap.put("authToken", "");
                    returnMap.put("message", "");
                    returnMap.put("code", responseCode);
                }
                return returnMap;
            }
        } catch (IOException ex) {
            System.out.println(ex);

            try {
                connection.getResponseCode();
                Map returnMap = new HashMap<String, Object>();
                returnMap.put("username", "");
                returnMap.put("authToken", "");
                returnMap.put("message", connection.getResponseMessage());
                returnMap.put("code", connection.getResponseCode());
                return returnMap;
            } catch (IOException ex1) {
                return null;
            }


        }
        //TODO: Make sure to handle cases where the returned json string has different values
    }
}
