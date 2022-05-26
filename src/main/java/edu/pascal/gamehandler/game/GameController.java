package edu.pascal.gamehandler.game;


import edu.pascal.gamehandler.GameServer;
import edu.pascal.gamehandler.api.mbot.manager.BotManager;
import edu.pascal.gamehandler.api.thread.WatchDogThread;
import edu.pascal.gamehandler.game.match.manager.MatchManager;
import edu.pascal.gamehandler.game.player.Player;
import edu.pascal.gamehandler.game.room.GameRoom;
import edu.pascal.gamehandler.game.room.manager.GameRoomManager;
import edu.pascal.gamehandler.websocket.wrapper.ConnectionWrapper;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class GameController {

    private final GameServer api;
    private boolean shutdown;
    private Thread mainThread;
    private MatchManager matchManager;
    private GameRoomManager gameRoomManager;
    private BotManager botManager;
    private GameRoom testRoom;
    private WatchDogThread watchDogThread;
    private int tick;
    private long lastTick;
    private final Map<UUID, Player> players;
    private final Queue<ConnectionWrapper> joinQueue;
    private final Queue<UUID> quitQueue;

    public GameController(GameServer api) {
        this.api = api;
        joinQueue = new ConcurrentLinkedQueue<>();
        quitQueue = new ConcurrentLinkedQueue<>();
        players = new HashMap<>();
        start();
    }

    public void start() {
        matchManager = new MatchManager(this);
        gameRoomManager = new GameRoomManager(this);
        testRoom = gameRoomManager.createRoom();
        botManager = new BotManager(api);
        watchDogThread = new WatchDogThread(this);
        mainThread = new Thread(this::tickLoop);
        mainThread.start();
        //watchDogThread.start();
    }

    public void tickLoop() {
        long currentTime,wait,catchupTime = 0;
        lastTick = System.currentTimeMillis();
        while (!shutdown) {
            tick++;
            currentTime = System.nanoTime();
            wait = 50000000 - (currentTime - lastTick) - catchupTime;
            if (wait > 0) {
                try {
                    Thread.sleep(wait / 1000000L);
                    catchupTime = 0;
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                catchupTime = Math.min(1000000000L, Math.abs(wait));
            }
            lastTick = currentTime;
            while (!joinQueue.isEmpty()) {
                ConnectionWrapper clientThread = joinQueue.poll();
                handleJoin(clientThread);
            }
            while (!quitQueue.isEmpty()) {
                UUID uuid = quitQueue.poll();
                handleQuit(uuid);
            }
            gameRoomManager.tick(tick);
            matchManager.tick(tick);
            //mettiamo 5 millisecondi come threshold
        }
        stop();
    }

    public void stop() {
        System.out.println("Spegnimento di tutte cose...");
        shutdown = true;
        mainThread.interrupt();

    }


    public void shutdown() {
        shutdown = true;
    }

    public Optional<Player> getPlayer(UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    public void handleQuit(UUID uuid) {
        getPlayer(uuid).ifPresent((player) -> {
            if(player.getCurrentRoom() != null) {
                player.getCurrentRoom().handleQuit(player);
            }
            if(player.getGameData() != null) {
                player.getGameData().getMatch().handleQuit(player);
            }
        });
        players.remove(uuid);
    }

    public void handleJoin(ConnectionWrapper wrapper) {
        UUID uuid = UUID.randomUUID();
        wrapper.setUuid(uuid);
        Player player = new Player(uuid,wrapper);
        player.getConnectionWrapper().getWebSocket().send("assign;" + uuid);
        players.put(uuid,player);
        player.joinRoom(testRoom);
    }

}
