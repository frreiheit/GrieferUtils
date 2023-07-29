/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 *
 * Copyright 2020-2023 L3g7
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.l3g7.griefer_utils.features.item.inventory_tweaks.tweaks;

import dev.l3g7.griefer_utils.core.file_provider.Singleton;
import dev.l3g7.griefer_utils.core.misc.TickScheduler;
import dev.l3g7.griefer_utils.event.EventListener;
import dev.l3g7.griefer_utils.event.events.WindowClickEvent;
import dev.l3g7.griefer_utils.features.item.inventory_tweaks.InventoryTweaks;
import dev.l3g7.griefer_utils.settings.ElementBuilder.MainElement;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import dev.l3g7.griefer_utils.util.ItemUtil;
import net.labymod.utils.Material;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import org.lwjgl.input.Keyboard;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.mc;
import static dev.l3g7.griefer_utils.util.MinecraftUtil.player;

@Singleton
public class BetterShift extends InventoryTweaks.InventoryTweak {

	@MainElement
	private final BooleanSetting enabled = new BooleanSetting()
		.name("Besseres Shiften")
		.description("Ermöglicht das Verschieben von Items \"Shift + Klick\" in einigen Eingaben."
			+ "\n- Werkbank"
			+ "\n- Spieler-Crafting §o(Shift + Alt + Klick)"
			+ "\n- Dorfbewohner")
		.icon(Material.WORKBENCH);

	@EventListener
	public void onGuiCraftingClick(WindowClickEvent event) {
		if (!enabled.get() || !(mc().currentScreen instanceof GuiCrafting))
			return;

		if (event.mode != 1 || event.slotId <= 9)
			return;

		move(10, event);
	}

	@EventListener
	public void onGuiInvClick(WindowClickEvent event) {
		if (!enabled.get() || !(mc().currentScreen instanceof GuiInventory))
			return;

		if (event.mode != 1 || event.slotId <= 8 || ! Keyboard.isKeyDown(Keyboard.KEY_LMENU))
			return;

		move(5, event);
	}

	@EventListener
	public void onGuiVillagerClick(WindowClickEvent event) {
		if (!enabled.get() || !(mc().currentScreen instanceof GuiMerchant))
			return;

		if (event.mode != 1 || event.slotId <= 2)
			return;

		GuiMerchant screen = (GuiMerchant) mc().currentScreen;
		if (screen.getSlotUnderMouse() == null)
			return;

		ItemStack movedStack = screen.getSlotUnderMouse().getStack();
		if (movedStack == null)
			return;

		// Check if a slot is free
		Container slots = screen.inventorySlots;
		ItemStack firstStack = slots.getSlot(0).getStack();
		if (firstStack != null && slots.getSlot(1).getHasStack())
			return;

		boolean hasPossibleTrade = false;

		for (MerchantRecipe recipe : screen.getMerchant().getRecipes(player())) {
			if (firstStack != null) {
				// Check if the trade is possible with the provided given input
				if ((!firstStack.isItemEqual(recipe.getItemToBuy()) && !firstStack.isItemEqual(recipe.getSecondItemToBuy())) || !recipe.hasSecondItemToBuy())
					continue;
			}

			// Check if the moved stack is required in the trade
			if (!movedStack.isItemEqual(recipe.getItemToBuy())
				&& !movedStack.isItemEqual(recipe.getSecondItemToBuy()))
				continue;

			hasPossibleTrade = true;
		}

		if (!hasPossibleTrade)
			return;

		event.setCanceled(true);

		click(event.windowId, event.slotId);
		click(event.windowId, firstStack == null ? 0 : 1);
	}

	private void move(int end, WindowClickEvent event) {
		GuiContainer screen = (GuiContainer) mc().currentScreen;
		if (screen.getSlotUnderMouse() == null)
			return;

		ItemStack movedStack = screen.getSlotUnderMouse().getStack();
		if (movedStack == null)
			return;

		int targetSlot = -1;
		for (int i = 1; i < end; i++) {
			Slot slot = screen.inventorySlots.getSlot(i);
			if (!slot.getHasStack()) {
				targetSlot = i;
				break;
			}
		}

		if (targetSlot == -1)
			return;

		event.setCanceled(true);
		click(event.windowId, event.slotId);
		int finalTargetSlot = targetSlot;
		TickScheduler.runAfterClientTicks(() -> click(event.windowId, finalTargetSlot), requiresDelay(movedStack) ? 4 : 0);
	}

	private void click(int windowId, int slot) {
		mc().playerController.windowClick(windowId, slot, 0, 0, player());
	}

	private boolean requiresDelay(ItemStack stack) {
		if (stack.getItem() == Items.filled_map)
			return true;

		return ItemUtil.getLore(stack).size() >= 3 && ItemUtil.getLore(stack).get(0).equals("§r§7Du benötigst die neueste Version des Möbel-Addons");
	}

}