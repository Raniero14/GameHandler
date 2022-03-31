package edu.pascal.gamehandler.game.player;

import edu.pascal.gamehandler.api.room.Room;
import edu.pascal.gamehandler.game.player.data.GameData;
import edu.pascal.gamehandler.websocket.wrapper.ConnectionWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class Player {

    private final UUID uuid;
    private final ConnectionWrapper connectionWrapper;
    //GameData è nullo se il giocatore non è in nessuna partita
    private Room<?> currentRoom;
    private GameData gameData;

    public void sendMessage(String str) {
        connectionWrapper.getWebSocket().send("message;" + str);
    }

    public void joinRoom(Room<?> room) {
        currentRoom = room;
        room.handleJoin(this);
    }

    public boolean isInMatch() {
        return gameData != null;
    }

}
