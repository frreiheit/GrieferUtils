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

package dev.l3g7.griefer_utils.event.events;

import dev.l3g7.griefer_utils.core.event_bus.Event;
import net.labymod.core_implementation.mc18.gui.GuiChatAdapter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

public class ChatMessageLogEvent extends Event {

	public String message;

	public ChatMessageLogEvent(String message) {
		this.message = message;
	}


	@Mixin(value = GuiChatAdapter.class, remap = false)
	private static class MixinGuiChatAdapter {

		@Redirect(method = "setChatLine", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V"))
		public void log(Logger logger, String message) {
			ChatMessageLogEvent event = new ChatMessageLogEvent(message);

			if (!event.fire().isCanceled())
				logger.info(event.message);
		}

	}

}