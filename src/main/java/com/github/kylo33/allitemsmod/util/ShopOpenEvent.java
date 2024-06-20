package com.github.kylo33.allitemsmod.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Collection;
import java.util.Collections;

public class ShopOpenEvent extends Event {

    public final Collection<ItemStack> itemStacks;

    public ShopOpenEvent(Collection<ItemStack> itemStacks) {
        this.itemStacks = Collections.unmodifiableCollection(itemStacks);
    }
}
