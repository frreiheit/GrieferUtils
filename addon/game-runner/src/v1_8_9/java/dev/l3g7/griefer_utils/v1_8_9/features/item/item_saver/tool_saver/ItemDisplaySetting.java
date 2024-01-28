/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.v1_8_9.features.item.item_saver.tool_saver;

import dev.l3g7.griefer_utils.laby4.settings.types.CategorySettingImpl;
import net.minecraft.item.ItemStack;

class ItemDisplaySetting extends CategorySettingImpl { // TODO: ListEntrySetting

	final String name;
	final ItemStack stack;

	public ItemStack getStack() {
		return stack;
	}

	ItemDisplaySetting(String name, ItemStack stack) {
		this.name = name;
		this.stack = stack;
	}
/*
TODO:
	private final IconStorage iconStorage = new IconStorage();

	ItemDisplaySetting(String name, ItemStack stack) {
		super(true, false, false);
		container = FileProvider.getSingleton(ToolSaver.class).enabled;
		name(name);
		this.name = name;
		this.stack = stack;
		icon(stack);
	}

	@Override
	public int getObjectWidth() {
		return 0;
	}

	@Override
	public IconStorage getIconStorage() {
		return iconStorage;
	}

	public ItemStack getStack() {
		return stack;
	}

	@Override
	protected void onChange() {
		FileProvider.getSingleton(ToolSaver.class).onChange();
	}
*/
}