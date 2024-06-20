package com.github.kylo33.allitemsmod.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderItemBackgroundEvent extends Event {
    public final ItemStack stack;
    public final int x;
    public final int y;

    public RenderItemBackgroundEvent(ItemStack stack, int x, int y) {
        this.stack = stack;
        this.x = x;
        this.y = y;
    }
}
