/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 *
 * Copyright 2020-2022 L3g7
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

package dev.l3g7.griefer_utils.features.render;

import dev.l3g7.griefer_utils.event.EventListener;
import dev.l3g7.griefer_utils.event.events.DisplayNameGetEvent;
import dev.l3g7.griefer_utils.event.events.network.TabListEvent;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.file_provider.Singleton;
import dev.l3g7.griefer_utils.settings.ElementBuilder.MainElement;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import net.minecraft.util.IChatComponent;

/**
 * Colors the player display name to match the selected prefix.
 */
@Singleton
public class NameTagPrefixSync extends Feature {

	@MainElement
	private final BooleanSetting enabled = new BooleanSetting()
		.name("Nametag mit Prefix")
		.description("Färbt den Namen über dem Kopf, dass er zum ausgewählten Prefix passt.")
		.icon("rainbow_name");

	@EventListener
	public void onDisplayNameRender(DisplayNameGetEvent event) {
		IChatComponent component = TabListEvent.getCachedName(event.player.getUniqueID());
		if(component != null)
			event.displayName = component;
	}

}
