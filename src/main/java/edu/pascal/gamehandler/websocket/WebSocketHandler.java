package edu.pascal.gamehandler.websocket;

import edu.pascal.gamehandler.GameServer;
import edu.pascal.gamehandler.websocket.wrapper.ConnectionWrapper;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSocketHandler extends WebSocketServer {


    private GameServer api;
    private Map<InetSocketAddress,ConnectionWrapper> socketMap;

    public WebSocketHandler(GameServer api, InetSocketAddress address) {
        super(address);
        this.api = api;
        socketMap = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        api.getGameController().getJoinQueue().add(new ConnectionWrapper(conn));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if(socketMap.containsKey(conn.getRemoteSocketAddress())) {
            ConnectionWrapper wrapper =  socketMap.get(conn.getRemoteSocketAddress());
            if(wrapper.getUuid() != null) {
                api.getGameController().getQuitQueue().add(wrapper.getUuid());
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }
}
