package com.github.kylo33.allitemsmod.util;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tracks the items that have been bought by the player during the current game.
 */
public class ShopItemTracker {
    /**
     * The items that have been bought by the player during the current game.
     */
    public static final Set<String> purchasedItems = new HashSet<>();
    public static final Set<String> NOT_NEEDED_ITEMS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("Permanent Chainmail Armor", "Permanent Iron Armor"))
    );

    private static final Pattern purchasePattern = Pattern.compile("^You purchased (.+?)(?: \\(\\+1 Silver Coin \\[\\d+]\\))?$");

    @SubscribeEvent
    public void onChat(BedwarsChatEvent event) {
        Matcher matcher = purchasePattern.matcher(event.message.getUnformattedText());
        if (matcher.matches())
            purchasedItems.add(matcher.group(1));
    }

    @SubscribeEvent
    public void onBedwarsGameStart(BedwarsGameStartEvent event) {
        purchasedItems.clear();
    }
}
