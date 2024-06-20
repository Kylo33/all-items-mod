package com.github.kylo33.allitemsmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class ShopItemRenderUtils {
    private static final Set<ItemStack> shopStacks = new HashSet<>();

    @SubscribeEvent
    public void onShopOpen(ShopOpenEvent event) {
        shopStacks.clear();
        shopStacks.addAll(event.itemStacks);
    }

    @SubscribeEvent
    public void onRenderBackground(RenderItemBackgroundEvent event) {
        if (!shopStacks.contains(event.stack)) return;
        String stackDisplayName = getStackName(event.stack);
        if (ShopItemTracker.purchasedItems.contains(stackDisplayName) || ShopItemTracker.NOT_NEEDED_ITEMS.contains(stackDisplayName))
            return;

        int backgroundColor = 0xFF00FF00;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, Minecraft.getMinecraft().getRenderItem().zLevel - 1);
        Gui.drawRect(event.x, event.y, event.x + 16, event.y + 16, backgroundColor);
        GlStateManager.popMatrix();
    }

    private String getStackName(ItemStack stack) {
        return StringUtils.unformat(stack.getDisplayName());
    }
}
