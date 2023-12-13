import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.AuthToken;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import org.glassfish.grizzly.utils.Pair;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.serverSpecificMessages.LoadGameMessage;
import webSocketMessages.serverMessages.serverSpecificMessages.PlayerJoinedNotification;
import webSocketMessages.userCommands.userGameCommands.JoinPlayerCommand;
import webSocketMessages.serverMessages.serverSpecificMessages.ErrorMessage;
import static webSocketMessages.serverMessages.ServerMessage.ServerMessageType.*;

import java.io.IOException;
import java.util.*;


@WebSocket
// This is the handler for my websocket route
public class WsServer {

    private static final Map<Integer, Pair<Boolean, Set<Session>>> gameSessions = new HashMap<Integer, Pair<Boolean, Set<Session>>>();

   /* @OnWebSocketConnect
    public void onConnect(Session session) {


        // I need to know when to create a new element of the Map. There should be a unique pair for each
        // game in the database, but I can't access this structure at the time a game is made.
        //gameSessions.put(session);
    }*/

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        //TODO: Remove session from gameSessions
        //gameSessions.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        Map messageMap = new Gson().fromJson(message, Map.class);
        System.out.println(messageMap);
        if (Objects.equals((String) messageMap.get("commandType"), "JOIN_PLAYER")) {
            JoinPlayerCommand joinPlayerCommand = new Gson().fromJson(message, JoinPlayerCommand.class);
            int code = 0;
            //TODO: Change this so I just use GAME DAO to see if it was successful
            
            try {
                models.Game game = new GameDAO().findGame(joinPlayerCommand.getGameID());
                String color = (String) messageMap.get("playerColor");
                AuthToken authToken = new AuthDAO().readToken(joinPlayerCommand.getAuthString());

                if (authToken == null) {
                    code = 400;
                } else {
                    String username = authToken.getUsername();
                    if (game == null) {
                        code = 400;
                    } else {
                        if (color.compareTo("WHITE") == 0) {
                            if (game.getWhiteUsername() == null) {
                                code = 400;
                            } else if (game.getWhiteUsername().compareTo(username) == 0) {
                                code = 200;
                            } else {
                                code = 400;
                            }
                        } else if (color.compareTo("BLACK") == 0) {
                            if (game.getBlackUsername() == null) {
                                code = 400;
                            } else if (game.getBlackUsername().compareTo(username) == 0) {
                                code = 200;
                            } else {
                                code = 400;
                            }
                        }
                    }
                }

            } catch (DataAccessException ex) {
                System.out.println(ex);
            }

            if (code != 200) {//(joinPlayerCommand.getCode() != 200) {
                ErrorMessage errorMessage = new ErrorMessage(ERROR, joinPlayerCommand.getCode());
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            } else {
                this.checkInGameSessions(session, joinPlayerCommand.getGameID());
                joinPlayer(joinPlayerCommand, session);
            }
        }
        //TODO: Implement logging so I can debug the server easier
    }

    private boolean checkInGameSessions(Session session, int gameID) {
        // Returns true if the session is not already stored in gameSessions, and had to be added.
        if (gameSessions.containsKey(gameID)) {
            if (!gameSessions.get(gameID).getSecond().contains(session)) {
                Pair<Boolean, Set<Session>> oldPair = gameSessions.get(gameID);
                Set<Session> newSet = oldPair.getSecond();
                newSet.add(session);
                gameSessions.put(gameID, new Pair<>(oldPair.getFirst(), newSet));
            }
        } else {
            gameSessions.put(gameID, new Pair<>(false, new HashSet<>(Set.of(session))));
            return true;
        }
        return false;
    }

    private void joinPlayer(JoinPlayerCommand joinPlayerCommand, Session rootSession) {
        int gameID = joinPlayerCommand.getGameID();

        String username = "";
        models.Game game = null;
        ChessGame.TeamColor color = null;
        try {
            username = new AuthDAO().readToken(joinPlayerCommand.getAuthString()).getUsername();
            game = new GameDAO().findGame(gameID);
            color = joinPlayerCommand.getPlayerColor();
        } catch (DataAccessException ex) {
            System.out.println(ex);
        }

        // Sending the "load game" notification to the root session
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);
        try{
            rootSession.getRemote().sendString(new Gson().toJson(loadGameMessage));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Sending the "user joined" notification to all sessions in game except new session.
        //TODO: STILL NEED TO TEST THIS PART
        PlayerJoinedNotification playerJoinedNotification 
                = new PlayerJoinedNotification(joinPlayerCommand, username);
        for (Session session : gameSessions.get(gameID).getSecond()) {
            try {
                if (!session.equals(rootSession)) {
                    session.getRemote().sendString(new Gson().toJson(playerJoinedNotification));
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}
