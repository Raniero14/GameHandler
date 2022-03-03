package edu.pascal.gamehandler.api.room.manager;

import edu.pascal.gamehandler.api.room.Room;
import edu.pascal.gamehandler.api.utils.game.Tickable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class RoomManager<T extends Room<?>> implements Tickable {

    public final Map<String,T> rooms = new HashMap<>();

    public abstract T createRoom();

    public void deleteRoom(String id) {
        rooms.remove(id);

    }

}
