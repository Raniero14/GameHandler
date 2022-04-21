package edu.pascal.gamehandler.game.match;

import edu.pascal.gamehandler.api.utils.Pathfinder;
import edu.pascal.gamehandler.api.utils.TimeUtils;
import edu.pascal.gamehandler.api.utils.game.Tickable;
import edu.pascal.gamehandler.game.match.manager.MatchManager;
import edu.pascal.gamehandler.game.match.type.MatchReward;
import edu.pascal.gamehandler.game.player.Player;
import edu.pascal.gamehandler.game.player.data.GameData;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Match implements Tickable {

    private final MatchManager manager;
    private Player player1,player2;
    private UUID currentTurn;
    private String color;
    private boolean robotMoving;
    private int timer;

    public Match(MatchManager manager,Player player1,Player player2) {
        this.manager = manager;
        this.player1 = player1;
        this.player2 = player2;
        player1.setGameData(new GameData(this,2,-1));
        player2.setGameData(new GameData(this,2,-1));
        player1.getGameData().generateMap();
        player1.getGameData().generateMap();
        currentTurn = player1.getUuid();
        player1.sendMessage("è il turno tuo");
    }



    public void moveToCell(Player player,int x,int y) {
        //Request to MBot
    }

    public void broadcastMovement(String movement) {
        player1.sendMovement(movement);
        player2.sendMovement(movement);
    }

    public void switchTurn() {
        currentTurn = currentTurn.equals(player1.getUuid()) ? player2.getUuid() : player1.getUuid();
        timer = TimeUtils.multiplyByTickDelay(11, TimeUnit.SECONDS);

    }

    public void endGame(Player winner) {
        winner.sendMessage("Hai vinto il match!!");
        player1.setGameData(null);
        player2.setGameData(null);
    }

    public void handleQuit(Player player) {
        endGame(player.getUuid() == player1.getUuid() ? player2 : player1);
    }

    @Override
    public void tick(int currentTick) {
        if(!robotMoving) {
            Player player = currentTurn.equals(player1.getUuid()) ? player1 : player2;
            if(color != null) {
                MatchReward reward = manager.getRewardMap().get(color);
                player.sendMessage("Hai trovato" + reward.getColor() + " guadagnato " + reward.getPoints());
                player.getGameData().setPoints(player.getGameData().getPoints() + reward.getPoints());
                player1.updateStats(player2.getGameData().getPoints());
                player2.updateStats(player1.getGameData().getPoints());
                color = null;
                switchTurn();
            } else {
                timer -= 50;
                if(timer == 0) {
                    switchTurn();
                }
            }
        }
    }
}
