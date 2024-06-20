package com.github.kylo33.allitemsmod.util;

import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BedwarsChatEvent extends Event {
    public final IChatComponent message;

    public BedwarsChatEvent(IChatComponent message) {
        this.message = message;
    }
}
