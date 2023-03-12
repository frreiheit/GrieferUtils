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

package dev.l3g7.griefer_utils.event.events.render;

import net.labymod.ingamechat.renderer.ChatLine;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderChatEvent extends Event {

	public final ChatLine chatLine;
	public final int y;
	public final float alpha;

	public RenderChatEvent(ChatLine chatLine, int y, float alpha) {
		this.chatLine = chatLine;
		this.y = y;
		this.alpha = alpha;
	}

}
