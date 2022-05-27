package edu.pascal.gamehandler.game.match.manager;

import edu.pascal.gamehandler.api.utils.game.GameCommand;
import edu.pascal.gamehandler.api.utils.game.Tickable;
import edu.pascal.gamehandler.game.GameController;
import edu.pascal.gamehandler.game.match.Match;
import edu.pascal.gamehandler.game.match.type.MatchReward;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class MatchManager implements Tickable {

    private final GameController api;
    private final Queue<GameCommand> commands;
    private final List<Match> matches;
    private final Map<String, MatchReward> rewardMap;


    public MatchManager(GameController api) {
        this.api = api;
        matches = new ArrayList<>();
        commands = new ConcurrentLinkedQueue<>();
        rewardMap = new HashMap<>();
        rewardMap.put("blue",new MatchReward("blue",1000));
        rewardMap.put("white",new MatchReward("white",1000));
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
                }
            }
        }
        for(Match match : matches) {
            match.tick(currentTick);
        }
    }
}
