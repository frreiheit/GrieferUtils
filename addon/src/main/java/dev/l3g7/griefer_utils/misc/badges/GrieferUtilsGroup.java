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

package dev.l3g7.griefer_utils.misc.badges;

import dev.l3g7.griefer_utils.features.uncategorized.settings.Badges;
import net.labymod.main.LabyMod;
import net.labymod.user.group.EnumGroupDisplayType;
import net.labymod.user.group.LabyGroup;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.drawUtils;
import static net.labymod.user.group.EnumGroupDisplayType.ABOVE_HEAD;
import static net.labymod.user.group.EnumGroupDisplayType.BESIDE_NAME;

public class GrieferUtilsGroup extends LabyGroup {

	public static String icon = "icon";

	private final Color color_with_labymod;
	private final Color color_without_labymod;
	private final String title;

	public GrieferUtilsGroup() {
		this(0xFFFFFF, 0xFFFFFF, null);
	}

	public GrieferUtilsGroup(int color_with_labymod, int color_without_labymod, String title) {
		this.color_with_labymod = new Color(color_with_labymod);
		this.color_without_labymod = new Color(color_without_labymod);
		this.title = title;
	}

	@Override
	public String getDisplayTag() {
		return "§f§lGrieferUtils " + title;
	}

	@Override
	public void renderBadge(double x, double y, double width, double height, boolean small) {
		if (!Badges.showBadges()) {
			super.renderBadge(x, y, width, height, small);
			return;
		}

		Color color = LabyMod.getSettings().revealFamiliarUsers ? color_with_labymod : color_without_labymod;

		if (icon.equals("icon"))
			GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);

		GlStateManager.enableBlend();
		drawUtils().bindTexture("griefer_utils/icons/" + icon + ".png");
		drawUtils().drawTexture(x, y, 255, 255, 8, 8, 1.1f);
		GlStateManager.color(1f, 1f, 1f);
	}

	@Override
	public EnumGroupDisplayType getDisplayType() {
		return title == null ? BESIDE_NAME : ABOVE_HEAD;
	}
}
