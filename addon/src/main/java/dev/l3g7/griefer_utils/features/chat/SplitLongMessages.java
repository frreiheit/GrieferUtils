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

package dev.l3g7.griefer_utils.features.chat;

import dev.l3g7.griefer_utils.core.file_provider.Singleton;
import dev.l3g7.griefer_utils.core.reflection.Reflection;
import dev.l3g7.griefer_utils.event.EventListener;
import dev.l3g7.griefer_utils.event.events.MessageEvent.MessageSendEvent;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.settings.ElementBuilder.MainElement;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import dev.l3g7.griefer_utils.util.MinecraftUtil;
import net.labymod.utils.Material;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.event.GuiScreenEvent;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class SplitLongMessages extends Feature {

	@MainElement
	private final BooleanSetting enabled = new BooleanSetting()
		.name("Lange Nachrichten aufteilen")
		.description("Teilt Nachrichten, die das Zeichenlimit überschreiten, in mehrere Nachrichten auf.\n" +
			"Funktioniert im öffentlichen Chat sowie mit /msg und /r.")
		.icon(Material.SHEARS);

	@EventListener
	public void onGuiKeyboardInput(GuiScreenEvent.KeyboardInputEvent.Post event) {
		if (!(event.gui instanceof GuiChat))
			return;

		GuiTextField inputField = Reflection.get(event.gui, "inputField");
		inputField.width = 563;

		String text = inputField.getText();
		if (!(text.startsWith("/msg ") || text.startsWith("/r ") || !text.startsWith("/"))) {
			inputField.setMaxStringLength(100);
			return;
		}

		inputField.setMaxStringLength(Integer.MAX_VALUE);
	}

	@EventListener
	public void onSend(MessageSendEvent event) {
		String text = event.message;

		if (text.length() <= 100)
			return;

		int index = text.startsWith("/msg ") ? text.indexOf(' ') + 1 : 0;
		index = text.startsWith("/") ? text.indexOf(' ', index) + 1 : 0;

		String message = text.substring(index);
		String prefix = text.substring(0, index);

		cutUp(message, 100 - prefix.length()).stream().map(s -> prefix + s).forEach(MinecraftUtil::send);
		event.setCanceled(true);
	}

	private static List<String> cutUp(String string, int length) {
		if (length == 0)
			throw new IllegalArgumentException("Required text length is 0!");

		List<String> messages = new ArrayList<>();
		String[] words = string.split(" ");
		StringBuilder text = new StringBuilder();

		for (String word : words) {
			while (word.length() > length) {
				if (length(text) >= length) {
					messages.add(text.toString());
					text = new StringBuilder();
				}

				int index = length;
				if (length(text) > 0)
					index = Math.max(0, index - length(text));

				String part = word.substring(0, index);
				word = word.substring(index);

				if (length(text) > 0)
					text.append(" ");

				text.append(part);
			}

			if (length(text) + word.length() > length) {
				messages.add(text.toString());
				text = new StringBuilder();
			}

			if (length(text) > 0)
				text.append(" ");

			text.append(word);
		}

		if (length(text) > 0)
			messages.add(text.toString());

		return messages;
	}

	private static int length(StringBuilder stringBuilder) {
		if (stringBuilder.length() == 0)
			return 0;

		return stringBuilder.length() + 1;
	}

}