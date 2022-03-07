package edu.pascal.gamehandler.api.mbot;

import edu.pascal.gamehandler.api.mbot.manager.BotManager;
import edu.pascal.gamehandler.game.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@Getter
@Setter
public class MBot {

    private final BotManager api;
    private final String host;
    private final int port;
    private Player player;
    private boolean busy;
    private Socket connection;
    private PrintWriter out;
    private DataInputStream inputStream;


    public MBot(BotManager api, String host, int port) {
        this.api = api;
        this.host = host;
        this.port = port;
        connect();
    }


    public void dispatchMovement() {
        int currentX = player.getGameData().getActualX();
        int currentY = player.getGameData().getActualY();
    }

    public void connect() {
        try {
            connection = new Socket(host,port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
