package edu.pascal.seafight.game.match.manager;

import edu.pascal.seafight.api.utils.game.GameCommand;
import edu.pascal.seafight.api.utils.game.Tickable;
import edu.pascal.seafight.game.GameController;
import edu.pascal.seafight.game.match.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
            GameCommand command  = commands.poll();
            if(command.getMatch().getCurrentTurn().equals(command.getPlayer().getUuid())) {
                command.getMatch().fireCell(command.getPlayer(),command.getX(),command.getY());
            }
        }
        for(Match match : matches) {
            match.setTimer(match.getTimer() - 50);
            if(match.getTimer() == 0) {
                match.switchTurn();
            }
        }
    }
}
