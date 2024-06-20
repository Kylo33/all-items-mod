package com.github.kylo33.allitemsmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardUtils {
    public static final int SIDEBAR_SCORE_SLOT = 1;

    private static String objective = "";
    private static final List<String> scoreboard = new ArrayList<>();

    @SubscribeEvent
    public void onTick(TickEvent tickEvent) {
        if (Minecraft.getMinecraft().theWorld == null) return;
        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(SIDEBAR_SCORE_SLOT);
        if (objective == null) return;
        List<String> scoreList = getScoreList(scoreboard, objective);

        if (!getScoreboard().equals(scoreList) || !getObjective().equals(objective.getDisplayName())) {
            ScoreboardUtils.objective = objective.getDisplayName();
            getScoreboard().clear();
            getScoreboard().addAll(scoreList);
            MinecraftForge.EVENT_BUS.post(new ScoreboardUpdateEvent(getObjective(), getScoreboard()));
        }
    }

    private static @NotNull List<String> getScoreList(Scoreboard scoreboard, ScoreObjective objective) {
        List<String> scoreList = scoreboard.getSortedScores(objective)
                .stream()
                .limit(15)
                .map(score -> {
                    String scoreString = ScorePlayerTeam.formatPlayerName(
                            scoreboard.getPlayersTeam(score.getPlayerName()),
                            score.getPlayerName());
                    StringBuilder builder = new StringBuilder();
                    // Remove the emojis
                    for (char c: scoreString.toCharArray()) {
                        if (Minecraft.getMinecraft().fontRendererObj.getCharWidth(c) > 0 || c == '§')
                            builder.append(c);
                    }
                    return builder.toString();
                }).collect(Collectors.toList());
        Collections.reverse(scoreList);
        return scoreList;
    }

    public List<String> getScoreboard() {
        return scoreboard;
    }

    public String getObjective() {
        return objective;
    }
}
