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

package dev.l3g7.griefer_utils.event.events.network;

import dev.l3g7.griefer_utils.core.event_bus.Event;
import dev.l3g7.griefer_utils.core.event_bus.EventListener;
import dev.l3g7.griefer_utils.core.event_bus.Priority;
import dev.l3g7.griefer_utils.event.events.annotation_events.OnEnable;
import dev.l3g7.griefer_utils.event.events.network.PacketEvent.PacketReceiveEvent;
import net.labymod.main.LabyMod;
import net.labymod.utils.ServerData;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

/**
 * An event related to the server connection.
 */
public class ServerEvent extends Event {

	private static boolean hotfix_brandPacketReceived = true, // TODO
		hotfix_watching = true; // only listen to first MC|Brand packet after quit to avoid multiple detections when replaying the packet

	public static class ServerSwitchEvent extends ServerEvent {

		@EventListener
		private static void onPacket(PacketReceiveEvent<S3FPacketCustomPayload> event) {
			if (event.packet.getChannelName().equals("MC|Brand"))
				new ServerSwitchEvent().fire();
		}

	}

	public static class ServerJoinEvent extends ServerEvent {

		@OnEnable
		private static void register() {
			LabyMod.getInstance().getEventManager().registerOnJoin(data -> new ServerJoinEvent().fire());
		}

	}

	public static class GrieferGamesJoinEvent extends ServerEvent {

		@EventListener(priority = Priority.HIGHEST)
		private static void onPacketReceive(PacketReceiveEvent<S3FPacketCustomPayload> event) {
			if (hotfix_watching && event.packet.getChannelName().equals("MC|Brand")) {
				hotfix_brandPacketReceived = true;
				hotfix_watching = false;
			}

			else if (hotfix_brandPacketReceived && event.packet.getChannelName().equals("mysterymod:mm")) {
				hotfix_brandPacketReceived = false;
				new GrieferGamesJoinEvent().fire();
			}
		}

	}

	public static class ServerQuitEvent extends ServerEvent {

		public final ServerData data;

		private ServerQuitEvent(ServerData data) {
			this.data = data;
		}

		@OnEnable
		private static void register() {
			LabyMod.getInstance().getEventManager().registerOnQuit(data -> {
				hotfix_brandPacketReceived = false;
				hotfix_watching = true;
				new ServerQuitEvent(data).fire();
			});
		}

	}

}
