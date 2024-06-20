package com.github.kylo33.allitemsmod.mixin;

import com.github.kylo33.allitemsmod.util.RenderItemBackgroundEvent;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem {

    @Inject(method = "renderItemIntoGUI", at = @At("RETURN"))
    public void onRenderItemIntoGUI_allitemsmod(ItemStack stack, int x, int y, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new RenderItemBackgroundEvent(stack, x, y));
    }
}
