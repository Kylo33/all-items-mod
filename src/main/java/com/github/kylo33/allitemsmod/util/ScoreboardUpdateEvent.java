package com.github.kylo33.allitemsmod.util;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Collections;
import java.util.List;

public class ScoreboardUpdateEvent extends Event {
    private final String objective;
    private final List<String> scoreboard;

    public ScoreboardUpdateEvent(String objective, List<String> scoreboard) {
        this.objective = objective;
        this.scoreboard = Collections.unmodifiableList(scoreboard);
    }

    public String getObjective() {
        return objective;
    }

    public List<String> getScoreboard() {
        return scoreboard;
    }
}
