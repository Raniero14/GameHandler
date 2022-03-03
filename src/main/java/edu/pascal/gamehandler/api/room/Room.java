package edu.pascal.gamehandler.api.room;

import edu.pascal.gamehandler.api.utils.game.Tickable;
import edu.pascal.gamehandler.game.GameController;
import edu.pascal.gamehandler.game.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Room<T> implements Tickable {


    private final String roomId;
    public final GameController api;



    public abstract T supply();


    public abstract void handleJoin(Player player);

    public abstract void handleQuit(Player player);
}
