package edu.pascal.seafight.api.mbot.manager;

import edu.pascal.seafight.GameServer;
import edu.pascal.seafight.api.mbot.MBot;

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




}
