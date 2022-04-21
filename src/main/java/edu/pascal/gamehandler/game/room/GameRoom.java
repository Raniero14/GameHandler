package edu.pascal.gamehandler.game.room;

import edu.pascal.gamehandler.api.room.Room;
import edu.pascal.gamehandler.api.utils.TimeUtils;
import edu.pascal.gamehandler.api.utils.game.GameStatus;
import edu.pascal.gamehandler.game.GameController;
import edu.pascal.gamehandler.game.match.Match;
import edu.pascal.gamehandler.game.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class GameRoom extends Room<List<Match>> {


    private LinkedList<Player> waiting;
    private GameStatus status;
    private int timer;

    public GameRoom(String roomId,GameController api) {
        super(roomId,api);
        status = GameStatus.WAITING;
        waiting = new LinkedList<>();
    }

    @Override
    public List<Match> supply() {
        if(waiting.size() % 2 != 0) {
            waiting.pollLast().sendMessage("Non abbiamo trovato un player per te");
        }
        List<Match> matches = new ArrayList<>();
        while (!waiting.isEmpty()) {
            Player player = waiting.poll();
            Player player2 = waiting.poll();
            matches.add(new Match(api.getMatchManager(),player,player2));
        }
        return matches;
    }

    @Override
    public void handleJoin(Player player) {
        waiting.add(player);
        if(waiting.size() >= 2 && status != GameStatus.COUNTDOWN) {
            status = GameStatus.COUNTDOWN;
            timer = TimeUtils.multiplyByTickDelay(20, TimeUnit.SECONDS);
        }
    }

    @Override
    public void handleQuit(Player player) {
        waiting.remove(player);
        if(waiting.size() == 2 && status != GameStatus.COUNTDOWN) {
            status = GameStatus.WAITING;
            broadcastMessage("Countdown cancellato per mancanza di players");

        }
    }

    public void broadcastMessage(String message) {
        for(Player player : waiting) {
            player.sendMessage(message);
        }
    }

    @Override
    public void tick(int currentTick) {
        if(status == GameStatus.COUNTDOWN) {
            timer -= 1;
            if(timer == 0) {
                api.getMatchManager().getMatches().addAll(supply());
                api.getGameRoomManager().deleteRoom(getRoomId());
            } else if(timer % 20 == 0 && timer / 20 <= 10) {
                broadcastMessage("Il gioco inizierà tra " + (timer / 20));
            }
        }
    }
}
