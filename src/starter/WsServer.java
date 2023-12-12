import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import org.glassfish.grizzly.utils.Pair;
import webSocketMessages.serverMessages.serverSpecificMessages.LoadGameMessage;
import webSocketMessages.serverMessages.serverSpecificMessages.PlayerJoinedNotification;
import webSocketMessages.userCommands.userGameCommands.JoinPlayerCommand;

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
        if (Objects.equals((String) messageMap.get("commandType"), "JOIN_PLAYER")) {
            JoinPlayerCommand joinPlayerCommand = new Gson().fromJson(message, JoinPlayerCommand.class);
            this.checkInGameSessions(session, joinPlayerCommand.getJoinGameRequest().getGameID());
            joinPlayer(joinPlayerCommand, session);
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
        //TODO: execute commands to add the player to the game in the database
        //TODO: IF that was successful, proceed with the remainder of this function

        int gameID = joinPlayerCommand.getJoinGameRequest().getGameID();

        String username = "";
        models.Game game = null;
        ChessGame.TeamColor color = null;
        try {
            username = new AuthDAO().readToken(joinPlayerCommand.getAuthString()).getUsername();
            game = new GameDAO().findGame(gameID);
            color = ChessGame.TeamColor.valueOf(joinPlayerCommand.getJoinGameRequest().getPlayerColor());
        } catch (DataAccessException ex) {
            System.out.println(ex);
        }

        /*// Add the player to the game in the database
        try {
            if ((color == ChessGame.TeamColor.WHITE) && (game.getWhiteUsername() == null)) {
                new GameDAO().setWhiteUsername(username, gameID);
            } else if ((color == ChessGame.TeamColor.BLACK) && (game.getBlackUsername() == null)) {
                new GameDAO().setBlackUsername(username, gameID);
            }
        } catch (DataAccessException ex) {
            System.out.println(ex);
        }*/

        
        // Sending the "load game" notification to the root session
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);
        try{
            rootSession.getRemote().sendString(new Gson().toJson(loadGameMessage));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Sending the "user joined" notification to all sessions in game except new session.
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
