import dataAccess.DataAccessException;
import dataAccess.Database;
import handlers.*;

import spark.*;


public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Set up the database
        new Database().initDatabase();

        // Specify the port to listen on
        Spark.port(8080);

        // Specify the location of external files to search
        Spark.externalStaticFileLocation(
                "/home/joseph/Desktop/semester-2023-fall/cs240/chess/web");

        // Methods to receive HTTP inputs and call the correct functions
        Spark.delete("/db", this::clearApplication);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
    }

    private Object clearApplication(Request request, Response response) {
        ClearApplicationHandler clearApplicationHandler
                = new ClearApplicationHandler(request, response);
        return clearApplicationHandler.getResponse();
    }

    private Object register(Request request, Response response) {
        RegisterHandler registerHandler = new RegisterHandler(request, response);
        return registerHandler.getResponse();
    }

    private Object login(Request request, Response response) {
        LoginHandler loginHandler = new LoginHandler(request, response);
        return loginHandler.getResponse();
    }

    private Object logout(Request request, Response response) {
        LogoutHandler logoutHandler = new LogoutHandler(request, response);
        return logoutHandler.getResponse();
    }

    private Object listGames(Request request, Response response) {
        ListGamesHandler listGamesHandler = new ListGamesHandler(request, response);
        return listGamesHandler.getResponse();
    }

    private Object createGame(Request request, Response response) {
        CreateGameHandler createGameHandler
                = new CreateGameHandler(request, response);
        return createGameHandler.getResponse();
    }

    private Object joinGame(Request request, Response response) {
        JoinGameHandler joinGameHandler = new JoinGameHandler(request, response);
        return joinGameHandler.getResponse();
    }
}
