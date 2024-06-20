package com.github.kylo33.allitemsmod.util;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypixelUtils {
    private static final String HYPIXEL_IP = "www.hypixel.net";
    private static final Pattern serverIdPattern = Pattern.compile("^(?:\\d{2}/?)+  ([Lm]).+$");
    private static final Pattern waitingPattern = Pattern.compile("^(?:Starting in|Waiting...)");

    private static boolean onHypixel = false;
    private static boolean inBedwarsArea = false;
    private static boolean inBedwarsGame = false;
    private static boolean bedwarsGameInProgress = false;

    @SubscribeEvent
    public void onScoreboardUpdate(ScoreboardUpdateEvent event) {
        if (event.getScoreboard().isEmpty()) return;
        onHypixel = StringUtils.unformat(event.getScoreboard().get(event.getScoreboard().size() - 1)).equals(HYPIXEL_IP);
        inBedwarsArea = onHypixel && StringUtils.unformat(event.getObjective()).equals("BED WARS");

        // Checks if the current server is a mini-game server (i.e. m193D instead of L32P)
        Matcher serverIdMatcher = serverIdPattern.matcher(StringUtils.unformat(event.getScoreboard().get(0)));
        inBedwarsGame = inBedwarsArea && serverIdMatcher.matches() && serverIdMatcher.group(1).equals("m");

        boolean gameWasInProgress = bedwarsGameInProgress;
        bedwarsGameInProgress = inBedwarsGame && !waitingPattern.matcher(event.getScoreboard().get(5)).matches();
        if (!gameWasInProgress && bedwarsGameInProgress)
            MinecraftForge.EVENT_BUS.post(new BedwarsGameStartEvent());
    }

    public static boolean isOnHypixel() {
        return onHypixel;
    }

    public static boolean isInBedwarsArea() {
        return inBedwarsArea;
    }

    public static boolean isInBedwarsGame() {
        return inBedwarsGame;
    }

    public static boolean isBedwarsGameInProgress() {
        return bedwarsGameInProgress;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (bedwarsGameInProgress)
            MinecraftForge.EVENT_BUS.post(new BedwarsChatEvent(event.message));
    }
}
