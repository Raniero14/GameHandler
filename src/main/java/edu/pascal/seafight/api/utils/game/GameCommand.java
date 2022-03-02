package edu.pascal.seafight.api.utils.game;

import edu.pascal.seafight.game.match.Match;
import edu.pascal.seafight.game.player.Player;
import lombok.Data;

import java.util.UUID;

@Data
public class GameCommand {

    private final Player player;
    private final Match match;
    private final int x,y;

}
