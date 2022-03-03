package edu.pascal.gamehandler;


import edu.pascal.gamehandler.game.GameController;
import edu.pascal.gamehandler.websocket.WebSocketHandler;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;

@Getter
public class GameServer {

    private GameController gameController;
    private WebSocketHandler webSocketHandler;
    //private SocketHandler socketHandler;

    public GameServer(String[] args) {
        enable(args);
        Runtime.getRuntime().addShutdownHook(new Thread(this::disable));
    }

    public void enable(String[] args) {
        System.out.println("Inizializzando Game Controller da lab_salaradio");
        gameController = new GameController(this);
        System.out.println("Accendendo socket server...");
        webSocketHandler = new WebSocketHandler(this,new InetSocketAddress("0.0.0.0",8080));
        webSocketHandler.start();
    }


    @SneakyThrows
    public void disable() {
        webSocketHandler.stop();
        gameController.shutdown();
        System.out.println("Spegnendo server...");
    }


}
