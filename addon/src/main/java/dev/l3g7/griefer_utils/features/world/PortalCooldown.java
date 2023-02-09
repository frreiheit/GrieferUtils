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

package dev.l3g7.griefer_utils.features.world;

import dev.l3g7.griefer_utils.event.EventListener;
import dev.l3g7.griefer_utils.event.events.network.ServerEvent.ServerQuitEvent;
import dev.l3g7.griefer_utils.event.events.network.ServerEvent.ServerSwitchEvent;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.file_provider.Singleton;
import dev.l3g7.griefer_utils.settings.ElementBuilder.MainElement;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.player;

@Singleton
public class PortalCooldown extends Feature {

	@MainElement
	private final BooleanSetting enabled = new BooleanSetting()
		.name("Portal-Cooldown")
		.description("Zeigt dir den 12s-Cooldown beim Betreten des Portalraums in der XP-Leiste an.")
		.icon("hourglass");

	private long timeoutEnd = 0;

	@EventListener(triggerWhenDisabled = true)
	public void onMessage(ClientChatReceivedEvent event) {
		if (event.message.getFormattedText().equals("§r§8[§r§6GrieferGames§r§8] §r§fDu bist im §r§5Portalraum§r§f. Wähle deinen Citybuild aus.§r")) {
			timeoutEnd = 12 * 20;
		}
	}

	@EventListener(triggerWhenDisabled = true)
	public void onServerSwitch(ServerSwitchEvent event) {
		timeoutEnd = 0;
	}

	@EventListener(triggerWhenDisabled = true)
	public void onServerQuit(ServerQuitEvent event) {
		timeoutEnd = 0;
	}

	@EventListener
	public void onTick(TickEvent.ClientTickEvent event) {
		if (player() == null || event.phase == TickEvent.Phase.START)
			return;

		if (timeoutEnd >= 0) {
			player().experienceLevel = (int) Math.ceil(timeoutEnd / 20f);
			player().experience = timeoutEnd / 240f;
			timeoutEnd--;
		}
	}

}