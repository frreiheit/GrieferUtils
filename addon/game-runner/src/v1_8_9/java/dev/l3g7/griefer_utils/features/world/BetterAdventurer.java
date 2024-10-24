/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.features.world;

import dev.l3g7.griefer_utils.core.api.event_bus.EventListener;
import dev.l3g7.griefer_utils.core.api.file_provider.Singleton;
import dev.l3g7.griefer_utils.core.settings.types.SwitchSetting;
import dev.l3g7.griefer_utils.core.events.GuiModifyItemsEvent;
import dev.l3g7.griefer_utils.core.events.ItemTooltipEvent;
import dev.l3g7.griefer_utils.core.events.MessageEvent;
import dev.l3g7.griefer_utils.core.events.WindowClickEvent;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.features.item.AutoTool;
import dev.l3g7.griefer_utils.features.item.item_info.info_suppliers.ItemCounter;
import dev.l3g7.griefer_utils.core.util.ItemUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.l3g7.griefer_utils.core.util.MinecraftUtil.mc;

@Singleton
public class BetterAdventurer extends Feature {

	private static final Pattern SELL_PATTERN = Pattern.compile("^§r§8\\[§r§6Adventure§r§8] §r§7Du hast §r§e\\d+§r§e .*§r§7 abgegeben. Status: §r§e(\\d+)§r§8/§r§6(\\d+)§r$");
	private ItemStack clickedStack = null;

	private final SwitchSetting displayMissing = SwitchSetting.create()
		.name("Fehlende Items anzeigen")
		.description("Zeigt unter Adventure-Items sowie beim Abgeben an, wie viel noch fehlt.")
		.icon(ItemUtil.createItem(Items.diamond_shovel, 0, true))
		.defaultValue(true);

	private final SwitchSetting coinAmount = SwitchSetting.create()
		.name("Coin-Anzeige fixen")
		.description("Setzt die Anzahl des Coin-Anzeige-Items auf die Anzahl der Coins.")
		.icon(ItemUtil.createItem(new ItemStack(Items.fire_charge, 128, 0), true, null))
		.defaultValue(true);

	@MainElement
	private final SwitchSetting enabled = SwitchSetting.create()
		.name("Adventurer verbessern")
		.description("Verbessert den Adventurer.")
		.icon(ItemUtil.createItem(Items.fire_charge, 0, true))
		.subSettings(displayMissing, coinAmount);

	@EventListener
	public void onTooltip(ItemTooltipEvent e) {
		GuiScreen screen = mc().currentScreen;
		if (!(screen instanceof GuiContainer))
			return;

		if (displayMissing.get()) {
			try {
				List<String> adventureToolTip = getMissingItems(e.itemStack);
				if (adventureToolTip != null)
					e.toolTip.addAll(adventureToolTip);
			} catch (NumberFormatException nfe) {
				System.out.println(nfe.getMessage());
			}
		}
	}

	@EventListener
	private void onGuiSetItemsEvent(GuiModifyItemsEvent event) {
		boolean isAdventurer = event.getTitle().startsWith("§6Adventure-Jobs");
		if (!coinAmount.get() || (!isAdventurer && !event.getTitle().startsWith("§6Shop")))
			return;

		ItemStack stack = event.getItem(isAdventurer ? 40 : 49);
		if (stack == null)
			return;

		if (stack.getItem() != Items.fire_charge)
			return;

		if (EnchantmentHelper.getEnchantments(stack).isEmpty())
			return;

		String lore = ItemUtil.getLoreAtIndex(stack, 0);
		if (!lore.startsWith("§7Anzahl: §e"))
			return;

		try {
			String amount = lore.substring("§7Anzahl: §e".length());
			amount = amount.substring(0, amount.indexOf(isAdventurer ? '§' : ' '));
			stack.stackSize = Integer.parseInt(amount);
		} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
			System.err.println(lore);
		}
	}

	@EventListener
	private void onClick(WindowClickEvent event) {
		if (!displayMissing.get())
			return;

		if (ItemUtil.getLastLore(event.itemStack).equals("§7Klicke, um die Materialien aus deinem Inventar zu liefern."))
			clickedStack = event.itemStack;
	}

	@EventListener
	private void onMessageModify(MessageEvent.MessageModifyEvent event) {
		Matcher matcher = SELL_PATTERN.matcher(event.original.getFormattedText());
		if (!matcher.matches() || clickedStack == null)
			return;

		String sold = matcher.group(1);
		String total = matcher.group(2);
		int missing = Integer.parseInt(total) - Integer.parseInt(sold);
		event.message.appendText(String.format("§7 (Fehlend: §e%s§7)", ItemCounter.formatAmount(missing, clickedStack.getMaxStackSize())));
	}

	private List<String> getMissingItems(ItemStack itemStack) {
		NBTTagCompound tag = itemStack.getTagCompound();

		List<String> toolTip = new ArrayList<>();
		toolTip.add("§r");

		if (tag != null && tag.hasKey("adventure")) {
			NBTTagCompound adventureTag = tag.getCompoundTag("adventure");
			int missingItems = adventureTag.getInteger("adventure.req_amount") - adventureTag.getInteger("adventure.amount");

			toolTip.add("Fehlende Items: " + ItemCounter.formatAmount(missingItems, 64));
			return toolTip;
		}

		List<String> lore = ItemUtil.getLore(itemStack);

		if (lore.size() != 8 && lore.size() != 9)
			return null;

		if (!lore.get(0).startsWith("§7Status: "))
			return null;

		String task = lore.get(4);
		String searchedText = lore.size() == 8 ? "§7Baue mit dem Werkzeug §e" : "§7Liefere §e";

		if (!task.startsWith(searchedText))
			return null;

		String amount = task.substring(searchedText.length());
		amount = amount.substring(0, amount.indexOf('§'));
		toolTip.add("Benötigte Items: " + ItemCounter.formatAmount(Integer.parseInt(amount), AutoTool.isTool(itemStack) ? 64 : itemStack.getMaxStackSize()));
		return toolTip;
	}
}
