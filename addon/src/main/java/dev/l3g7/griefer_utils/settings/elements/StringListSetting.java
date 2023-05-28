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

package dev.l3g7.griefer_utils.settings.elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import dev.l3g7.griefer_utils.settings.ElementBuilder;
import dev.l3g7.griefer_utils.settings.ValueHolder;
import dev.l3g7.griefer_utils.settings.elements.components.EntryAddSetting;
import net.labymod.core.LabyModCore;
import net.labymod.gui.elements.ModTextField;
import net.labymod.settings.LabyModModuleEditorGui;
import net.labymod.settings.PreviewRenderer;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.drawUtils;

public class StringListSetting extends ControlElement implements ElementBuilder<StringListSetting>, ValueHolder<StringListSetting, List<String>> {

	private final Storage<List<String>> storage = new Storage<>(list -> {
		JsonArray array = new JsonArray();
		list.forEach(s -> array.add(new JsonPrimitive(s)));
		return array;
	}, elem -> {
		List<String> list = new ArrayList<>();
		elem.getAsJsonArray().forEach(e -> list.add(e.getAsString()));
		return list;
	}, new ArrayList<>());

	public StringListSetting() {
		super("§cEs gab einen Fehler!", null);
		setSettingEnabled(true);
	}

	private SettingsElement container = this;

	@Override
	public Storage<List<String>> getStorage() {
		return storage;
	}

	public void setContainer(SettingsElement container) {
		this.container = container;
	}

	@Override
	public StringListSetting config(String configKey) {
		ValueHolder.super.config(configKey);

		ArrayList<SettingsElement> settings = new ArrayList<>();
		for (String entry : get())
			settings.add(new StringDisplaySetting(entry));

		settings.add(new StringAddSetting());
		getSettings().remove(this);
		container.getSubSettings().addAll(settings);

		return this;
	}

	private List<SettingsElement> getSettings() {
		return container.getSubSettings().getElements();
	}

	private class StringDisplaySetting extends ControlElement {

		private final String data;
		private boolean deleteHovered = false;

		public StringDisplaySetting(String entry) {
			super("§cUnknown name", new IconData(Material.PAPER));
			data = entry;
		}

		public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			if (!deleteHovered)
				return;

			getSettings().remove(this);
			get().remove(data);
			save();
			getStorage().callbacks.forEach(c -> c.accept(get()));
			mc.currentScreen.initGui(); // Update settings
		}

		@Override
		public void draw(int x, int y, int maxX, int maxY, int mouseX, int mouseY) {
			setDisplayName(data);
			super.draw(x, y, maxX, maxY, mouseX, mouseY);
			drawUtils().drawRectangle(x - 1, y, x, maxY, 0x78787878);

			if (!mouseOver)
				return;

			mc.getTextureManager().bindTexture(new IconData("labymod/textures/misc/blocked.png").getTextureIcon());
			deleteHovered = mouseX > maxX - 19 && mouseX < maxX - 6 && mouseY > y + 5 && mouseY < y + 18;
			drawUtils().drawTexture(maxX - 16 - (deleteHovered ? 4 : 3), y + (deleteHovered ? 3.5 : 4.5), 256, 256, deleteHovered ? 16 : 14, deleteHovered ? 16 : 14);
		}

	}

	private class StringAddSetting extends EntryAddSetting {

		StringAddSetting() {
			super("Text hinzufügen");
			callback(() -> Minecraft.getMinecraft().displayGuiScreen(new StringAddSetting.AddPlayerGui(Minecraft.getMinecraft().currentScreen)));
		}

		private class AddPlayerGui extends GuiScreen {

			private final GuiScreen backgroundScreen;
			private ModTextField inputField;

			public AddPlayerGui(GuiScreen backgroundScreen) {
				this.backgroundScreen = backgroundScreen;
				MinecraftForge.EVENT_BUS.register(this);
			}

			public void initGui() {
				super.initGui();
				backgroundScreen.width = width;
				backgroundScreen.height = height;
				if (backgroundScreen instanceof LabyModModuleEditorGui)
					PreviewRenderer.getInstance().init(StringAddSetting.AddPlayerGui.class);

				inputField = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), width / 2 - 150, height / 4 + 45, 300, 20);
				inputField.setFocused(true);
				inputField.setMaxStringLength(100);
				buttonList.add(new GuiButton(0, width / 2 - 105, height / 4 + 85, 100, 20, "Abbrechen"));
				buttonList.add(new GuiButton(1, width / 2 + 5, height / 4 + 85, 100, 20, "Hinzufügen"));
			}

			public void drawScreen(int mouseX, int mouseY, float partialTicks) {
				backgroundScreen.drawScreen(0, 0, partialTicks);
				drawRect(0, 0, width, height, Integer.MIN_VALUE);

				inputField.drawTextBox();

				super.drawScreen(mouseX, mouseY, partialTicks);
			}

			public void updateScreen() {
				backgroundScreen.updateScreen();
				inputField.updateCursorCounter();
			}

			protected void actionPerformed(GuiButton button) throws IOException {
				super.actionPerformed(button);
				switch (button.id) {
					case 1:
						getSettings().add(getSettings().indexOf(StringAddSetting.this), new StringDisplaySetting(inputField.getText()));
						get().add(inputField.getText());
						save();
						getStorage().callbacks.forEach(c -> c.accept(get()));
						// Fall-through
					case 0:
						Minecraft.getMinecraft().displayGuiScreen(backgroundScreen);
						backgroundScreen.initGui(); // Update settings
				}
			}

			protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
				super.mouseClicked(mouseX, mouseY, mouseButton);
				inputField.mouseClicked(mouseX, mouseY, mouseButton);
			}

			protected void keyTyped(char typedChar, int keyCode) {
				if (keyCode == 1) // ESC
					Minecraft.getMinecraft().displayGuiScreen(backgroundScreen);

				inputField.textboxKeyTyped(typedChar, keyCode);
			}
		}

	}

}