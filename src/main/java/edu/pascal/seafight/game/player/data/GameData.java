package edu.pascal.seafight.game.player.data;


import edu.pascal.seafight.game.match.Match;
import edu.pascal.seafight.game.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

;

@Getter
@Setter
@RequiredArgsConstructor
public class GameData {

    private final Match match;
    private final int startX,startY;
    private int actualX,actualY;
    private byte[][] matrix = new byte[3][3];


    public void generateMap() {
        HashSet<Integer> ships = new HashSet<>();
        for(int i = 0;i < 4;i++) {
            int x = ThreadLocalRandom.current().nextInt(9);
            while (ships.contains(x)) {
                x = ThreadLocalRandom.current().nextInt(9);
            }
            ships.add(x);
        }
        for(int x : ships) {
            System.out.println(x);
            int row = x / 3;
            int col = x % 3;
            matrix[row][col] = (byte) ThreadLocalRandom.current().nextInt(5);
        }
    }

}
