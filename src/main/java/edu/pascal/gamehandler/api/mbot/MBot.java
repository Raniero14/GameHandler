package edu.pascal.gamehandler.api.mbot;

import edu.pascal.gamehandler.api.mbot.manager.BotManager;
import edu.pascal.gamehandler.api.utils.path.MovePack;
import edu.pascal.gamehandler.api.utils.path.PathFinder;
import edu.pascal.gamehandler.game.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Setter
public class MBot {

    private final BotManager api;
    private final String host;
    private final int port;
    private final BlockingQueue<String> commands;
    private Thread inputThread,commandThread;
    private String lastCommand;
    private Player player;
    private boolean busy;
    private Socket socket;
    private PrintWriter out;
    private InputStream inputStream;
    private int currentX,currentY,orientation;


    public MBot(BotManager api, String host, int port) {
        this.api = api;
        this.host = host;
        this.port = port;
        commands = new LinkedBlockingQueue<>();
        connect();
        inputThread = new Thread(this::checkInput);
        commandThread = new Thread(this::checkQueue);
        inputThread.start();
        commandThread.start();
    }

    public void checkInput() {
        BufferedReader input = new BufferedReader(
                new InputStreamReader(inputStream));
        System.out.println(inputStream);
        while (!socket.isClosed()) {
            try {
                System.out.println("siui");
                String ack = input.readLine();
                System.out.println("siui2");
                System.out.println(ack);
                if(ack.equals("fatto")) {
                    player.getGameData().getMatch().broadcastMovement(lastCommand);
                } else if(ack.equals("paired") || ack.equals("unpaired")) {
                    System.out.println("arrivato");
                    busy = false;
                    return;
                } else {
                    player.getGameData().getMatch().setColor(ack);
                }
                if(commands.isEmpty()) {
                    player.getGameData().getMatch().setRobotMoving(false);
                }
                busy = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void checkQueue() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String command = commands.take();
                while (busy) {}
                out.print(command);
                out.flush();
                lastCommand = command;
                busy = true;
            }
        } catch (InterruptedException ex) {
            //si ignora
        }
    }

    public void setPaired() {

        commands.add("setpaired");
    }

    public void unpair() {
        commands.add("unpair");
    }

    public void dispatchMovement(int x,int y) {
        MovePack movePack = PathFinder.findPath(currentX,currentY,x,y,orientation);
        commands.addAll(movePack.getMoves());
        currentX = x;
        currentY = y;
        orientation = movePack.getOrientation();
    }

    public void connect() {
        try {
            socket = new Socket(host,port);
            inputStream = socket.getInputStream();
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
