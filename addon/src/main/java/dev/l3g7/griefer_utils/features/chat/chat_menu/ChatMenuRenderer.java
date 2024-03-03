/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 *
 * Copyright 2020-2024 L3g7
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

package dev.l3g7.griefer_utils.features.chat.chat_menu;

import net.labymod.utils.DrawUtils;
import net.labymod.utils.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;

import java.util.List;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.drawUtils;
import static dev.l3g7.griefer_utils.util.MinecraftUtil.mc;

public class ChatMenuRenderer {

	private final List<ChatMenuEntry> entries;
	private final String playerName;
	private final IChatComponent entireText;
	private final String titleText;
	private final DrawUtils utils = new DrawUtils();

	private int boxHeight;
	private final int boxWidth;
	private int x;
	private int y;
	private int hoveredEntry = -1;

	public ChatMenuRenderer(List<ChatMenuEntry> entries, String playerName, IChatComponent entireText) {
		this.entries = entries;
		this.playerName = playerName;
		this.titleText = "ChatMenü §a" + playerName;
		this.entireText = entireText;

		// Box size
		boxHeight = 16 + 15 * entries.size();
		boxWidth = Math.max(150, getWidth(titleText) + 16);

		if (!entries.isEmpty())
			boxHeight += 5;

		// Box position (upper left corner)
		x = getMouseX();
		y = getMouseY();

		// Make sure the box in inside the window
		if (utils.getWidth() - x < boxWidth)
			x = utils.getWidth() - boxWidth;

		if (utils.getHeight() - y < boxHeight)
			y = utils.getHeight() - boxHeight;
	}

	private void drawString(String text, float x, float y) {
		utils.getFontRenderer().drawString(text, x, y, 0xFFFFFFFF, false);
	}

	private int getWidth(String text) {
		return utils.getFontRenderer().getStringWidth(text);
	}

	public void render() {
		// Draw the box
		DrawUtils.drawRect(x, y, x + boxWidth, y + 16, 0xFF000000);
		DrawUtils.drawRect(x, y + 16, x + boxWidth, y + boxHeight, 0xFF060606);

		// Draw the entries
		int currentY = y;
		drawString(titleText, x + (boxWidth - getWidth(titleText)) / 2f, currentY += 4);
		currentY += 3;
		for (int i = 0; i < entries.size(); i++) {
			ChatMenuEntry entry = entries.get(i);
			String name = entry.name;

			// Cut name if it's too long
			if (getWidth(name) > boxWidth - 24) {
				while (getWidth(name + "...") > boxWidth - 24)
					name = name.substring(0, name.length() - 1);
				name = name + "...";
			}

			drawString(name, x + 21, currentY += 15);
			drawIcon(entry, x + 4, currentY - 2);

			// Draw frame if hovered
			if (i == hoveredEntry)
				utils.drawRectBorder(x + 2, currentY - 4, x + boxWidth - 4, currentY + 12, 0xFF00FF00, 1);
		}
	}

	private void drawIcon(ChatMenuEntry entry, int x, int y) {
		ItemStack stack;

		if (entry.icon instanceof ItemStack) {
			stack = ((ItemStack) entry.icon);
		} else if (entry.icon instanceof Material) {
			stack = ((Material) entry.icon).createItemStack();
		} else {
			entry.drawIcon(x, y, 12, 12);
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.75, 0.75, 1.0);
		double posScale = 1 / 0.75;
		drawUtils().drawItem(stack, x * posScale, y * posScale, null);
		GlStateManager.popMatrix();
	}

	public boolean onMouse() {
		hoveredEntry = -1;

		if (outOfBox())
			return false;

		int mouseY = getMouseY();

		for (int i = 0; i < entries.size(); i++) {
			int boxStart = y + 19 + 15 * i;
			int boxEnd = boxStart + 14;

			if (boxStart <= mouseY && mouseY <= boxEnd) {
				hoveredEntry = i;
				break;
			}
		}

		if (hoveredEntry == -1 || !Mouse.getEventButtonState() || Mouse.getEventButton() != 0)
			return false;

		// Trigger the consumer and close the gui
		entries.get(hoveredEntry).trigger(playerName, entireText);
		return true;
	}

	public boolean outOfBox() {
		int mouseX = getMouseX();
		int mouseY = getMouseY();
		return mouseX < x || mouseY < y || mouseX > x + boxWidth || mouseY > y + boxHeight;
	}

	public static int getMouseX() {
		return Mouse.getX() * new DrawUtils().getWidth() / mc().displayWidth;
	}

	public static int getMouseY() {
		DrawUtils drawUtils = new DrawUtils();
		return drawUtils.getHeight() - Mouse.getY() * drawUtils.getHeight() / mc().displayHeight - 1;
	}
}