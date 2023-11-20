package requests;

import java.util.HashMap;
import java.util.Map;

public class ListGamesRequest extends Request {
    Map<String, Object> listGameRequest = new HashMap<>();

    public ListGamesRequest(String authToken) {
        listGameRequest.put("authToken", authToken);
    }

    public String getAuthToken() {
        return (String) this.listGameRequest.get("authToken");
    }

    @Override
    public Map getMap() {
        return listGameRequest;
    }
}
