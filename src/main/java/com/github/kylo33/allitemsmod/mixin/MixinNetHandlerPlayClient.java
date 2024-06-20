package com.github.kylo33.allitemsmod.mixin;

import com.github.kylo33.allitemsmod.util.HypixelUtils;
import com.github.kylo33.allitemsmod.util.ShopOpenEvent;
import com.github.kylo33.allitemsmod.util.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.regex.Pattern;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    private static final Set<String> SHOP_INVENTORY_NAMES = new HashSet<>(Arrays.asList(
            "Quick Buy",
            "Blocks",
            "Melee",
            "Armor",
            "Tools",
            "Ranged",
            "Potions",
            "Utility"
    ));
    private static final Pattern costPattern = Pattern.compile("^Cost: \\d+ .+$");

    @Inject(
            method = "handleSetSlot",
            at = @At("RETURN"))
    public void onHandleSetSlot(S2FPacketSetSlot packetIn, CallbackInfo ci) {
        // If we're not on Hypixel or not in the shop, stop here.
        if (!(HypixelUtils.isOnHypixel() && Minecraft.getMinecraft().currentScreen instanceof GuiChest)) return;
        ContainerChest container = (ContainerChest) ((GuiChest) Minecraft.getMinecraft().currentScreen).inventorySlots;
        if (!SHOP_INVENTORY_NAMES.contains(container.getLowerChestInventory().getDisplayName().getUnformattedText())) return;

        // Stop here if we are not filling the last slot.
        int itemSlotIndex = packetIn.func_149173_d();
        if (container.getLowerChestInventory().getSizeInventory() - 1 != itemSlotIndex) return;

        // Create a ShopOpenEvent with a list of the actual purchasable items in the shop (i.e. not the navigation buttons)
        List<ItemStack> purchasableItemStacks = new ArrayList<>();

        for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++) {
            Slot slot = container.inventorySlots.get(i);
            if (!slot.getHasStack()) continue;
            ItemStack stack = slot.getStack();

            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound == null) continue;
            NBTTagList loreList = tagCompound.getCompoundTag("display").getTagList("Lore", new NBTTagString().getId());
            if (loreList.get(0) != null && costPattern.matcher(StringUtils.unformat(loreList.getStringTagAt(0))).matches())
                purchasableItemStacks.add(stack);
        }

        MinecraftForge.EVENT_BUS.post(new ShopOpenEvent(purchasableItemStacks));
    }
}
