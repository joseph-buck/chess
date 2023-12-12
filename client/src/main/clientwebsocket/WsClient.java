package clientwebsocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class WsClient extends Endpoint {
    public Session session;

    public WsClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            //TODO: THIS onMessage FUNCTION NEEDS TO ACTUALLY DO STUFF
            @Override
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}