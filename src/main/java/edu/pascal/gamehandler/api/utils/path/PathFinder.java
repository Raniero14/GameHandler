package edu.pascal.gamehandler.api.utils.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PathFinder {

    public static MovePack findPath(int x, int y, int toX, int toY, int orientation) {
        List<String> moves = new ArrayList<>();
        int resolvedX = x;
        int resolvedY = y;
        int currentOrientation = orientation;
        while (resolvedX != toX) {
            if(toX < resolvedX) {
                while (currentOrientation != 1) {
                    if(currentOrientation < 1) {
                        currentOrientation++;
                        moves.add("sinistra");
                    } else {
                        currentOrientation--;
                        moves.add("destra");
                    }
                }
                moves.add("avanti");
                resolvedX--;
            } else if (toX > resolvedX) {
                while (currentOrientation != -1) {
                    if(currentOrientation < -1) {
                        currentOrientation++;
                        moves.add("sinistra");
                    } else {
                        currentOrientation--;
                        moves.add("destra");
                    }
                }
                moves.add("avanti");
                resolvedX++;
            }
        }
        while (resolvedY != toY) {
            if(toY < resolvedY) {
                while (currentOrientation != 0) {
                    System.out.println("loop: " + currentOrientation);
                    if(currentOrientation < 0) {
                        currentOrientation++;
                        moves.add("sinistra");
                    } else {
                        currentOrientation--;
                        moves.add("destra");
                    }
                }
                moves.add("avanti");
                resolvedY--;
            } else if (toY > resolvedY) {
                if(currentOrientation < 0) {
                    while (currentOrientation != -2) {
                        moves.add("destra");
                        currentOrientation--;
                    }
                    currentOrientation = Math.abs(currentOrientation);
                } else if(currentOrientation >= 0) {
                    while (currentOrientation != 2) {
                        moves.add("sinistra");
                        currentOrientation++;
                    }
                }
                moves.add("avanti");
                resolvedY++;
            }
        }
        return new MovePack(moves,currentOrientation);
    }
}
