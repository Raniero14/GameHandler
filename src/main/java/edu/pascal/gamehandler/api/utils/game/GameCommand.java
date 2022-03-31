package edu.pascal.gamehandler.api.utils.game;

import edu.pascal.gamehandler.game.match.Match;
import edu.pascal.gamehandler.game.player.Player;
import lombok.Data;

@Data
public class GameCommand {

    private final Player player;
    private final String command;

}
