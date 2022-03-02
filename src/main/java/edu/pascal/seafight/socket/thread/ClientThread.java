package edu.pascal.seafight.socket.thread;

import edu.pascal.seafight.GameServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class ClientThread extends Thread {

    private final GameServer api;
    private final Socket socket;
    private DataOutputStream output;
    private UUID uuid;

    @Override
    public void run() {
        try {
            output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            while (!socket.isClosed()) {
                if(input.available() == 0) {
                    continue;
                }
                int action = input.readInt();
                switch (action) {
                    //Chat
                    case 1:

                        break;
                    //Room list
                    case 2:

                        break;
                }
            }
        } catch (IOException e) {
            api.getGameController().getQuitQueue().add(uuid);
        }

    }
}
