package edu.pascal.gamehandler.game.match;

import edu.pascal.gamehandler.api.utils.TimeUtils;
import edu.pascal.gamehandler.game.player.Player;
import edu.pascal.gamehandler.game.player.data.GameData;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Match {

    private Player player1,player2;
    private UUID currentTurn;
    private int timer;

    public Match(Player player1,Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1.setGameData(new GameData(this,2,-1));
        player2.setGameData(new GameData(this,2,-1));
        player1.getGameData().generateMap();
        player1.getGameData().generateMap();
        currentTurn = player1.getUuid();
    }



    public void moveToCell(Player player,int x,int y) {
        //Request to MBot
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
    }

}
