package edu.pascal.seafight.api.room;

import edu.pascal.seafight.api.utils.game.Tickable;
import edu.pascal.seafight.game.GameController;
import edu.pascal.seafight.game.player.Player;
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
