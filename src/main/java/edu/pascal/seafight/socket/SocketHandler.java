package edu.pascal.seafight.socket;

import edu.pascal.seafight.GameServer;
import edu.pascal.seafight.api.connection.ConnectionHandler;
import edu.pascal.seafight.api.utils.ConnectionDetails;
import edu.pascal.seafight.game.player.Player;
import edu.pascal.seafight.socket.thread.ClientThread;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SocketHandler extends ConnectionHandler {

    private GameServer api;
    private ServerSocket serverSocket;
    private Thread connectionThread;
    private Map<InetAddress, ClientThread> clientMap;


    public SocketHandler(GameServer api, ConnectionDetails details) {
        super(details);
        this.api = api;
    }

    @SneakyThrows
    @Override
    public void start() {
        serverSocket = new ServerSocket(getDetails().getPort());
        clientMap = new HashMap<>();
        acceptConnections();
        System.out.println("Socket server accesso,sto acettando le connessioni");
    }

    @Override
    public void close() {
        for(Player player : api.getGameController().getPlayers().values()) {
            player.sendMessage("Spegnimento del server...");
            try {
                player.getConnectionThread().getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptConnections() {
        connectionThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket socket = getServerSocket().accept();
                    ClientThread clientThread = new ClientThread(api,socket);
                    clientThread.start();
                    clientMap.put(socket.getInetAddress(),clientThread);
                    api.getGameController().getJoinQueue().add(clientThread);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        connectionThread.start();
    }


}
