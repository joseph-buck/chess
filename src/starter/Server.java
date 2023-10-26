import handlers.*;

import models.*;

import com.google.gson.Gson;
import spark.*;
import java.util.*;


public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port to listen on
        Spark.port(8080);

        // Specify the location of external files to search
        Spark.externalStaticFileLocation(
                "/home/joseph/Desktop/semester-2023-fall/cs240/chess/web");

        // Calls methods to receive HTTP inputs and call the correct functions
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/db", this::clearApplication);
    }

    private Object register(Request request, Response response) {
        RegisterHandler registerHandler = new RegisterHandler(request, response);
        return registerHandler.getResponse();
    }

    private Object clearApplication(Request request, Response response) {
        ClearApplicationHandler clearApplicationHandler
                = new ClearApplicationHandler(request, response);
        //TODO: Change the clearApplication() function name to getResponse()
        return clearApplicationHandler.clearApplication();
    }

    private Object login(Request request, Response response) {
        LoginHandler loginHandler = new LoginHandler(request, response);
        return loginHandler.getResponse();
    }
}
