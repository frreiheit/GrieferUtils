/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.core.misc.gui.guis;


import dev.l3g7.griefer_utils.core.events.annotation_events.OnStartupComplete;
import dev.l3g7.griefer_utils.core.api.event_bus.EventListener;
import dev.l3g7.griefer_utils.core.api.event_bus.EventRegisterer;
import dev.l3g7.griefer_utils.core.api.event_bus.Priority;
import dev.l3g7.griefer_utils.core.events.GuiScreenEvent.GuiOpenEvent;
import dev.l3g7.griefer_utils.features.uncategorized.settings.Settings;
import dev.l3g7.griefer_utils.core.misc.TickScheduler;
import dev.l3g7.griefer_utils.core.misc.gui.elements.laby_polyfills.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import static dev.l3g7.griefer_utils.core.util.MinecraftUtil.mc;

public class ChangelogScreen extends GuiScreen {

	private static boolean completedStartup = false;
	private static boolean triggered = false;
	private static boolean triggeredByUser = false;
	private static String version = null;
	private static String changelog = null;

	private TextList textList;
	private GuiScreen previousScreen = mc().currentScreen;

	// Make sure the gui closes to the correct screen
	@EventListener(priority = Priority.LOWEST)
	public void onGuiOpen(GuiOpenEvent<?> event) {
		if (event.isCanceled() || event.gui instanceof ChangelogScreen)
			return;

		previousScreen = event.gui;
		event.cancel();
	}

	@OnStartupComplete
	private static void onStartUpComplete() {
		completedStartup = true;
		tryOpening();
	}

	public static void trigger(boolean triggeredByUser) {
		triggered = true;
		ChangelogScreen.triggeredByUser = triggeredByUser;
		tryOpening();
	}

	public static boolean hasData() {
		return version != null;
	}

	public static void setData(String version, String changelog) {
		ChangelogScreen.version = version;
		ChangelogScreen.changelog = changelog;
		tryOpening();
	}

	private static void tryOpening() {
		TickScheduler.runAfterRenderTicks(() -> {
			if (triggered && completedStartup && version != null && !(mc().currentScreen instanceof ChangelogScreen))
				mc().displayGuiScreen(new ChangelogScreen());
		}, 1);
	}

	public ChangelogScreen() {
		EventRegisterer.register(this);
	}

	public void initGui() {
		super.initGui();

		textList = new TextList(mc, width, height, 64, height - 42, fontRendererObj);
		textList.addEntry("");
		textList.addEntries(changelog);
		textList.addEntry("");

		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 + 4 + 75, height - 28, 150, 20, "Schließen"));
	}

	public void closeGui() {
		EventRegisterer.unregister(this);
		mc.displayGuiScreen(previousScreen);
	}

	private boolean isLeftButtonHovered(int mouseX, int mouseY) {
		return mouseX > this.width / 2 - 205 && mouseX < this.width / 2 - 54 && mouseY > this.height - 28 && mouseY < this.height - 8;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawBackground(0);
		textList.drawScreen(mouseX, mouseY, partialTicks);

		String text = "§nGrieferUtils - Changelog - " + version;

		// Title
		GlStateManager.scale(1.5, 1.5, 1.5);
		drawCenteredString(fontRendererObj, text, width / 3, 15, 0xffffff);
		GlStateManager.scale(1/1.5, 1/1.5, 1/1.5);

		// Icon
		int textWidth = fontRendererObj.getStringWidth(text);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("griefer_utils", "icons/icon.png"));
		DrawUtils.drawTexture(width / 2d - textWidth * 0.75 - 29, 18, 256, 256, 20, 20);

		// Left button
		if (!triggeredByUser) {
			text = "§"+(isLeftButtonHovered(mouseX, mouseY) ? 'c' : '7') + "Nicht nochmal anzeigen";
			DrawUtils.drawString(text, width / 2d - 186, height - 22);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode != 1)
			super.keyTyped(typedChar, keyCode);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!triggeredByUser && isLeftButtonHovered(mouseX, mouseY)) {
			Settings.showUpdateScreen.set(false);
			closeGui();
			return;
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void handleMouseInput() {
		super.handleMouseInput();
		textList.handleMouseInput();
	}

	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		textList.mouseReleased(mouseX, mouseY, state);
	}

	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		closeGui();
	}

}
