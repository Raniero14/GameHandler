package edu.pascal.gamehandler.api.connection;

import edu.pascal.gamehandler.api.utils.ConnectionDetails;
import lombok.Getter;

@Getter
public abstract class ConnectionHandler {


    private final ConnectionDetails details;


    public ConnectionHandler(ConnectionDetails details) {
        this.details = details;
        start();
    }

    public abstract void start();

    public abstract void close();

}
