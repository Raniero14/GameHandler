package edu.pascal.gamehandler.api.thread;

import edu.pascal.gamehandler.game.GameController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WatchDogThread extends Thread {


    private final GameController controller;

    @Override
    public void run() {
        System.out.println("Watchdog thread attivato.");
        while (!Thread.interrupted()) {
            if(System.nanoTime() - controller.getLastTick() > 1e9) {
                System.out.println("Il tickloop thread è bloccato,sto forzando lo spegnimento del server...");
                System.exit(1);
            }
        }
    }
}
