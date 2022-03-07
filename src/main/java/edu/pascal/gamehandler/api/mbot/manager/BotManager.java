package edu.pascal.gamehandler.api.mbot.manager;

import edu.pascal.gamehandler.GameServer;
import edu.pascal.gamehandler.api.mbot.MBot;
import edu.pascal.gamehandler.game.player.Player;

import java.util.*;

public class BotManager {

    public GameServer server;
    private final Map<UUID, MBot> pairedBots;
    private final List<MBot> unpairedBots;

    public BotManager(GameServer handler) {
        this.server = handler;
        pairedBots = new HashMap<>();
        unpairedBots = new ArrayList<>();
    }


    public boolean pairBot(Player player) {
        if(unpairedBots.isEmpty()) {
            return false;
        }
        MBot mBot = unpairedBots.get(0);
        mBot.setPlayer(player);
        pairedBots.put(player.getUuid(),mBot);
        unpairedBots.remove(mBot);
        return true;
    }




}
