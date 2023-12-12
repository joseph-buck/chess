import org.eclipse.jetty.util.log.Log;
import requests.LoginRequest;
import responses.LoginResponse;
import responses.Response;
import ui.GameplayUI;
import ui.PostloginUI;
import ui.PreloginUI;

import java.util.HashMap;
import java.util.List;

import static ui.EscapeSequences.*;


public class Client {
    private Response loginResponse = null;
    List<HashMap<String, Object>> games;

    public static void main(String[] args) {
        new Client().run();
    }

    private void run() {
        int loginStatus = 0;
        System.out.print(SET_BG_COLOR_DARK_GREY);

        while (loginStatus != -1) {
            PreloginUI preloginUI = new PreloginUI();
            if (loginStatus == 0) {
                loginStatus = preloginUI.run();
                this.loginResponse = preloginUI.getLoginResponse();
            }

            PostloginUI postloginUI = new PostloginUI();
            if (loginStatus == 1) { // Logged in
                postloginUI.setLoginResponse(this.loginResponse);
                postloginUI.setGames(games);
                loginStatus = postloginUI.run();
                games = postloginUI.getGames();
            }

            if (loginStatus == 2) { // Logged in and in a game
                GameplayUI gameplayUI = new GameplayUI(loginStatus);
                loginStatus = gameplayUI.run();
            }

            if (loginStatus == 3) { // Logged in and observing a game
                GameplayUI gameplayUI = new GameplayUI(loginStatus);
                loginStatus = gameplayUI.run();
            }
        }
    }

    private List<HashMap<String, Object>> getGames() {
        return this.games;
    }
}











/*try {
            // Specify URL to send requests to
            URL url = new URL("http://localhost:8080/");

            // Open connection to URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();
            try (InputStream respBody = connection.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                // System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
                int data = inputStreamReader.read();
                String myString = "";
                while(data != -1) {
                    char theChar = (char) data;
                    myString += theChar;
                    data = inputStreamReader.read();
                }
                inputStreamReader.close();
                System.out.println(myString);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }*/