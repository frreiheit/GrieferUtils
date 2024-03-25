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

package dev.l3g7.griefer_utils.event.events.render;

import dev.l3g7.griefer_utils.core.event_bus.EventListener;
import dev.l3g7.griefer_utils.event.events.TickEvent.RenderTickEvent;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.mc;

/**
 * A RenderTickEvent called when no gui is opened.
 */
public class RenderWorldEvent extends RenderTickEvent {

	public RenderWorldEvent(float renderTickTime) {
		super(renderTickTime);
	}

	@EventListener
	private static void onRenderTick(RenderTickEvent event) {
		if (mc().currentScreen == null)
			new RenderWorldEvent(event.renderTickTime).fire();
	}

}