package edu.pascal.gamehandler.game.match.manager;

import edu.pascal.gamehandler.api.utils.game.GameCommand;
import edu.pascal.gamehandler.api.utils.game.Tickable;
import edu.pascal.gamehandler.game.GameController;
import edu.pascal.gamehandler.game.match.Match;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class MatchManager implements Tickable {

    private final GameController api;
    private final Queue<GameCommand> commands;
    private final List<Match> matches;


    public MatchManager(GameController api) {
        this.api = api;
        matches = new ArrayList<>();
        commands = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void tick(int currentTick) {
        while (!commands.isEmpty()) {
            GameCommand gameCommand  = commands.poll();
            Match match = gameCommand.getPlayer().getGameData().getMatch();
            if(match.getCurrentTurn().equals(gameCommand.getPlayer().getUuid())) {
                String[] array = gameCommand.getCommand().split(";");
                switch (array[0].toLowerCase(Locale.ROOT)) {
                    case "move":
                        int x = Integer.parseInt(array[1]);
                        int y = Integer.parseInt(array[2]);
                        match.moveToCell(gameCommand.getPlayer(),x,y);
                        break;
                    case "message":
                        break;
                }
            }
        }
        for(Match match : matches) {
            match.tick(currentTick);
        }
    }
}
