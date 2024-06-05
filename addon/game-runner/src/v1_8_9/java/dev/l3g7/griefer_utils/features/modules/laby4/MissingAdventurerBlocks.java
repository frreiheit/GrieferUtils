/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.features.modules.laby4;

import dev.l3g7.griefer_utils.core.api.bridges.Bridge;
import dev.l3g7.griefer_utils.core.api.file_provider.Singleton;
import dev.l3g7.griefer_utils.features.Feature.MainElement;
import dev.l3g7.griefer_utils.core.settings.types.SwitchSetting;
import dev.l3g7.griefer_utils.features.modules.Laby4Module;
import dev.l3g7.griefer_utils.features.modules.TempMissingAdventurerBlocksBridge;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static dev.l3g7.griefer_utils.core.api.misc.Constants.DECIMAL_FORMAT_98;
import static dev.l3g7.griefer_utils.core.util.MinecraftUtil.player;

@Bridge
@Singleton
public class MissingAdventurerBlocks extends Laby4Module implements TempMissingAdventurerBlocksBridge {

	private TextLine missingBlocksLine;

	@MainElement
	private final SwitchSetting enabled = SwitchSetting.create()
		.name("Fehlende Adv. Blöcke")
		.description("Zeigt dir an, wie viele Blöcke mit dem in der Hand gehaltenen Adventure-Werkzeug noch abgebaut werden müssen.")
		.icon(Items.fire_charge);

	@Override
	public boolean isVisibleInGame() {
		return getMissingBlocks() != -1;
	}

	@Override
	protected void createText() {
		missingBlocksLine = createLine("Fehlende Blöcke", "0");
	}

	@Override
	public void onTick(boolean isEditorContext) {
		missingBlocksLine.updateAndFlush(DECIMAL_FORMAT_98.format(Math.max(getMissingBlocks(), 0)));
	}

	private int getMissingBlocks() {
		return player() == null ? -1 : getMissingBlocks(player().getHeldItem());
	}

	public int getMissingBlocks(ItemStack stack) {
		if (stack == null)
			return -1;

		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null || !tag.hasKey("adventure"))
			return -1;

		NBTTagCompound adventureTag = tag.getCompoundTag("adventure");
		return adventureTag.getInteger("adventure.req_amount") - adventureTag.getInteger("adventure.amount");
	}

}
