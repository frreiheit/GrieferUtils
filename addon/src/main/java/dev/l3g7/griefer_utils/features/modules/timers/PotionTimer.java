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

package dev.l3g7.griefer_utils.features.modules.timers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import dev.l3g7.griefer_utils.core.file_provider.Singleton;
import dev.l3g7.griefer_utils.core.reflection.Reflection;
import dev.l3g7.griefer_utils.core.util.Util;
import dev.l3g7.griefer_utils.event.EventListener;
import dev.l3g7.griefer_utils.features.Module;
import dev.l3g7.griefer_utils.misc.ServerCheck;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import dev.l3g7.griefer_utils.settings.elements.DropDownSetting;
import dev.l3g7.griefer_utils.settings.elements.NumberSetting;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.input.Mouse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.player;
import static net.labymod.ingamegui.enums.EnumModuleFormatting.SQUARE_BRACKETS;

@Singleton
public class PotionTimer extends Module {

	private final DropDownSetting<KeyMode> keyModeSetting = new DropDownSetting<>(KeyMode.class)
		.name("Design")
		.icon("wooden_board")
		.config("modules.orb_potion_timer.design")
		.defaultValue(KeyMode.TEXT_AND_ICON)
		.stringProvider(KeyMode::getName);

	private final NumberSetting warnTime = new NumberSetting()
		.name("Warn-Zeit für Fly Tränke (s)")
		.icon("labymod:buttons/exclamation_mark")
		.config("modules.orb_potion_timer.warn_time");

	private final BooleanSetting hide = new BooleanSetting()
		.name("Verstecken, wenn nichts getrunken")
		.description("Ob das Modul versteckt werden soll, wenn derzeit kein Orbtrank aktiv ist.")
		.icon("blindness")
		.config("modules.orb_potion_timer.hide");

	private final Map<String, PotionData> potions = ImmutableMap.of(
		"break_potion", new PotionData("Break"),
		"fly_potion", new PotionData("Fly")
	);

	public PotionTimer() {
		super("Orbtränke", "Zeigt dir an, wie lange aktivierte Fly/Break Tränke noch anhalten.", "potion-timer", new ControlElement.IconData(Material.FEATHER));
	}

	@Override
	public boolean isShown() {
		return super.isShown() && (!hide.get() || potions.values().stream().anyMatch(p -> p.expirationDate >= System.currentTimeMillis()));
	}

	@Override
	public void fillSubSettings(List<SettingsElement> list) {
		super.fillSubSettings(list);
		list.add(keyModeSetting);
		list.add(warnTime);
		list.add(hide);
	}

	@EventListener
	public void onMouse(GuiScreenEvent.MouseInputEvent event) {
		if (!Mouse.getEventButtonState() || !(event.gui instanceof GuiChest))
			return;

		int button = Mouse.getEventButton();
		if (button != 0 && button != 1)
			return;

		IInventory lowerChestInventory = Reflection.get(event.gui, "lowerChestInventory");
		String title = lowerChestInventory.getDisplayName().getFormattedText();
		if (!title.startsWith("§6Möchtest du den Trank benutzen?"))
			return;

		GuiChest gui = (GuiChest) event.gui;
		if (gui.inventorySlots.getSlot(12) != gui.getSlotUnderMouse())
			return;

		ItemStack heldItem = player().getHeldItem();
		if (heldItem == null || !heldItem.hasTagCompound())
			return;

		NBTTagCompound tag = heldItem.getTagCompound();
		for (Map.Entry<String, PotionData> entry : potions.entrySet()) {
			if (tag.hasKey(entry.getKey())) {
				entry.getValue().expirationDate = System.currentTimeMillis() + 15 * 60 * 1000;
				break;
			}
		}
	}

	@Override
	public String[] getKeys() {
		if (!ServerCheck.isOnCitybuild())
			return getDefaultValues();

		// Get names as Strings
		List<String> keys = potions.values().stream()
			.filter(d -> d.expirationDate >= System.currentTimeMillis())
			.map(PotionData::getDisplayName)
			.collect(Collectors.toList());

		keys.add(0, "Orbtränke");
		return keys.toArray(new String[0]);
	}

	@Override
	public String[] getDefaultValues() {
		return new String[] {""};
	}

	@Override
	public String[] getValues() {
		if (!ServerCheck.isOnCitybuild())
			return getDefaultValues();

		// Get expirations dates as Strings
		List<String> values = potions.values().stream()
			.filter(d -> d.expirationDate >= System.currentTimeMillis())
			.map(PotionData::getFormattedTime)
			.collect(Collectors.toList());

		if (values.isEmpty())
			return getDefaultValues();

		// Warn if the fly potion end is less than the set amount of seconds away
		long flyPotionEnd = potions.get("fly_potion").expirationDate;
		if (flyPotionEnd > System.currentTimeMillis() && flyPotionEnd - System.currentTimeMillis() < warnTime.get() * 1000) {
			String s = Util.formatTime(flyPotionEnd, true);
			if (!s.equals("0s"))
				title("§c§l" + s);
		}

		values.add(0, "");

		return values.toArray(new String[0]);
	}

	@Override
	public void draw(double x, double y, double rightX) {
		super.draw(x, y, rightX);

		if (!ServerCheck.isOnCitybuild())
			return;

		if (keyModeSetting.get() == KeyMode.TEXT || !keyVisible)
			return;

		List<PotionData> data = potions.values().stream()
			.filter(d -> d.expirationDate >= System.currentTimeMillis())
			.collect(Collectors.toList());

		if (data.isEmpty())
			return;

		double singum = Math.signum(rightX);
		int fontHeight = mc.fontRendererObj.FONT_HEIGHT;

		double xDiff = 0;

		if (getDisplayFormatting() == SQUARE_BRACKETS)
			xDiff -= singum * mc.fontRendererObj.getStringWidth(new Text("[", 0, bold, italic, underline).getText());

		if (keyModeSetting.get() == KeyMode.ICON)
			xDiff += .5;

		// Add padding
		y += padding;
		if (rightX == -1)
			xDiff += padding;

		xDiff *= -singum;

		for (PotionData d : data) {
			y += fontHeight + 1;

			double actualX = rightX == -1 ? x : rightX - getDisplayTextWidth(d);
			actualX += xDiff;

			mc.getTextureManager().bindTexture(new ResourceLocation("griefer_utils/icons/booster/" + d.displayName.toLowerCase() + ".png"));
			LabyMod.getInstance().getDrawUtils().drawTexture(actualX, y, 256, 256, 7, 7);
		}

	}

	private double getDisplayTextWidth(PotionData d) {
		List<Text> texts = getDisplayFormatting().getTexts(d.getDisplayName(), ImmutableList.of(new Text(d.getFormattedTime(), 0)), 0, 0, 0, keyVisible, bold, italic, underline);
		String text = texts.stream().map(Text::getText).reduce(String::concat).orElseThrow(() -> new RuntimeException("PotionData has no text"));

		return mc.fontRendererObj.getStringWidth(text);
	}

	private void title(String title) {
		mc.ingameGUI.displayTitle("§cFly Trank", null, -1, -1, -1);
		mc.ingameGUI.displayTitle(null, title, -1, -1, -1);
		mc.ingameGUI.displayTitle(null, null, 0, 2, 3);
	}

	private class PotionData {

		private final String displayName;
		private long expirationDate = -1;

		public PotionData(String displayName) {
			this.displayName = displayName;
		}

		private String getFormattedTime() {
			return Util.formatTime(expirationDate);
		}

		private String getDisplayName() {
			KeyMode mode = keyModeSetting.get();
			String name = "";

			if (mode != KeyMode.TEXT)
				name += "  "; // Space for the icon
			if (mode != KeyMode.ICON)
				name += displayName;

			return name;
		}

	}

	private enum KeyMode {
		ICON("Icon"),
		TEXT("Text"),
		TEXT_AND_ICON("Text & Icon");

		private final String name;

		KeyMode(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}