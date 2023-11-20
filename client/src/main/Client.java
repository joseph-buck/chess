import responses.LoginResponse;
import ui.PostloginUI;
import ui.PreloginUI;
import static ui.EscapeSequences.*;


public class Client {
    public static void main(String[] args) {
        new Client().run();
    }

    private void run() {
        int loginStatus = 0;
        System.out.print(SET_BG_COLOR_DARK_GREY);

        while (loginStatus != 2) {
            PreloginUI preloginUI = new PreloginUI();
            loginStatus = preloginUI.run();

            PostloginUI postloginUI = new PostloginUI();
            if (loginStatus == 1) {
                postloginUI.setLoginResponse(new LoginResponse("","","",0));
                loginStatus = postloginUI.run();
            }
        }
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