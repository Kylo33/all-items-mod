package com.github.kylo33.allitemsmod;

import com.github.kylo33.allitemsmod.util.HypixelUtils;
import com.github.kylo33.allitemsmod.util.ShopItemRenderUtils;
import com.github.kylo33.allitemsmod.util.ScoreboardUtils;
import com.github.kylo33.allitemsmod.util.ShopItemTracker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "allitemsmod", useMetadata=true)
public class ExampleMod {
    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ShopItemTracker());
        MinecraftForge.EVENT_BUS.register(new HypixelUtils());
        MinecraftForge.EVENT_BUS.register(new ScoreboardUtils());
        MinecraftForge.EVENT_BUS.register(new ShopItemRenderUtils());
    }
}
