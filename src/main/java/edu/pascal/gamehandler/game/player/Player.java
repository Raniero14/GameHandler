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

    }

    public void joinRoom(Room<?> room) {
        currentRoom = room;
        room.handleJoin(this);
    }


    /*
    public void sendMessage(String message) {
       if(!connectionThread.getSocket().isClosed()) {
           try {
               //ID
               connectionThread.getOutput().writeInt(1);
               //Messaggio
               connectionThread.getOutput().writeUTF(message);
               connectionThread.getOutput().flush();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    public void sendMap(int[] ships) {
        if(!connectionThread.getSocket().isClosed()) {
            try {
                //ID
                connectionThread.getOutput().writeInt(2);
                //Convertiamo la matrice in un array per inviarlo
                byte[] array = new byte[ships.length];
                for(int i  = 0;i < ships.length;i++) {
                    array[i] = (byte) ships[i];
                }
                connectionThread.getOutput().write(array,0,array.length);
                connectionThread.getOutput().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMap(int x,int y,byte type) {
        if(!connectionThread.getSocket().isClosed()) {
            try {
                //ID
                connectionThread.getOutput().writeInt(3);
                connectionThread.getOutput().writeInt(x);
                connectionThread.getOutput().writeInt(y);
                connectionThread.getOutput().writeByte(type);
                connectionThread.getOutput().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     */



}
