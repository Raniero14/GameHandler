package edu.pascal.seafight.game.room.manager;

import edu.pascal.seafight.api.room.manager.RoomManager;
import edu.pascal.seafight.game.GameController;
import edu.pascal.seafight.game.player.Player;
import edu.pascal.seafight.game.room.GameRoom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameRoomManager extends RoomManager<GameRoom> {


    private final GameController api;

    @Override
    public GameRoom createRoom() {
        GameRoom gameRoom = new GameRoom("game-" + rooms.size(),api);
        rooms.put("game-" + rooms.size(),gameRoom);
        return gameRoom;
    }

    @Override
    public void deleteRoom(String id) {
        for (Player player : rooms.get(id).getWaiting()) {
            player.setCurrentRoom(null);
        }
        super.deleteRoom(id);

    }

    @Override
    public void tick(int currentTick) {
        for(GameRoom room : rooms.values()) {
            room.tick(currentTick);
        }
    }
}
