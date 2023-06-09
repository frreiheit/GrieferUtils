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

package dev.l3g7.griefer_utils.event.events.network;

import dev.l3g7.griefer_utils.mixin.minecraft.MixinNetHandlerPlayClient;
import dev.l3g7.griefer_utils.mixin.minecraft.MixinNetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent extends Event {

	public final Packet<?> packet;

	private PacketEvent(Packet<?> packet) {
		this.packet = packet;
	}

	@Cancelable
	public static class PacketReceiveEvent extends PacketEvent {

		private PacketReceiveEvent(Packet<?> packet) {
			super(packet);
		}

		/**
		 * Triggered by {@link MixinNetworkManager}.
		 */
		public static boolean shouldReceivePacket(Packet<?> packet) {
			return !MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(packet));
		}

	}

	@Cancelable
	public static class PacketSendEvent extends PacketEvent {

		public PacketSendEvent(Packet<?> packet) {
			super(packet);
		}

		/**
		 * Triggered by {@link MixinNetHandlerPlayClient}.
		 */
		public static boolean shouldSendPacket(Packet<?> packet) {
			return !MinecraftForge.EVENT_BUS.post(new PacketSendEvent(packet));
		}
	}

}
