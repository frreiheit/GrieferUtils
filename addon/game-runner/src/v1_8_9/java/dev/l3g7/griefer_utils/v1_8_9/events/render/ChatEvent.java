/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.v1_8_9.events.render;

import dev.l3g7.griefer_utils.api.event.event_bus.Event;
import net.minecraft.util.IChatComponent;

public abstract class ChatEvent extends Event {

	public static class ChatLineInitEvent extends ChatEvent {

		public final IChatComponent component;

		public ChatLineInitEvent(IChatComponent component) {
			this.component = component;
		}

	}

	public static class ChatMessageAddEvent extends ChatEvent {

		public final IChatComponent component;

		public ChatMessageAddEvent(IChatComponent component) {
			this.component = component;
		}

	}
/*

	// TODO: @Mixin(value = GuiChatAdapter.class, remap = false)
	private static class MixinGuiChatAdapter {

		@Inject(method = "setChatLine", at = @At(value = "INVOKE", target = "Lnet/labymod/ingamechat/renderer/ChatRenderer;getVisualWidth()I"))
		public void postChatLineInitEvent(IChatComponent component, int chatLineId, int updateCounter, boolean refresh, boolean secondChat, String room, Integer highlightColor, CallbackInfo ci) {
			new ChatEvent.ChatLineInitEvent(component).fire();

			if (!refresh)
				new ChatEvent.ChatMessageAddEvent(component).fire();
		}

	}*/

}