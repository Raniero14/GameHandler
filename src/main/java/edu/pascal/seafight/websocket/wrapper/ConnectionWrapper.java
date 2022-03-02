package edu.pascal.seafight.websocket.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.java_websocket.WebSocket;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class ConnectionWrapper {

    private UUID uuid;
    private final WebSocket webSocket;


}
