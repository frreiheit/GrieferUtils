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

package dev.l3g7.griefer_utils.features.uncategorized.settings;

import dev.l3g7.griefer_utils.core.auto_update.ReleaseInfo.ReleaseChannel;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import dev.l3g7.griefer_utils.settings.elements.DropDownSetting;
import dev.l3g7.griefer_utils.util.ItemUtil;
import net.minecraft.init.Blocks;

import static dev.l3g7.griefer_utils.core.auto_update.ReleaseInfo.ReleaseChannel.STABLE;

public class AutoUpdateSettings {

	public static final BooleanSetting showUpdateScreen = new BooleanSetting()
		.name("Update-Screen anzeigen")
		.description("Ob ein Update-Screen angezeigt werden soll, wenn GrieferUtils geupdatet wurde.")
		.config("settings.auto_update.show_screen")
		.icon(ItemUtil.createItem(Blocks.stained_glass_pane, 0, true))
		.defaultValue(true);

	public static final DropDownSetting<ReleaseChannel> releaseChannel = new DropDownSetting<>(ReleaseChannel.class)
		.name("Version")
		.description("Ob auf die neuste stabile oder die Beta-Version geupdatet werden soll.")
		.config("settings.auto_update.release_channel")
		.icon("file")
		.defaultValue(STABLE);

	public static final BooleanSetting enabled = new BooleanSetting()
		.name("Automatisch updaten")
		.description("Updatet GrieferUtils automatisch auf die neuste Version.")
		.config("settings.auto_update.enabled")
		.icon("arrow_circle")
		.defaultValue(true)
		.subSettings(showUpdateScreen, releaseChannel);

}