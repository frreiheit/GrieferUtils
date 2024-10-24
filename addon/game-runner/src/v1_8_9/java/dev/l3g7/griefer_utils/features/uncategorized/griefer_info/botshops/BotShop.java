/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.features.uncategorized.griefer_info.botshops;

import com.google.gson.JsonObject;
import dev.l3g7.griefer_utils.core.api.misc.Citybuild;
import dev.l3g7.griefer_utils.features.chat.BetterSwitchCommand;
import dev.l3g7.griefer_utils.features.uncategorized.griefer_info.BigChestUtil;
import dev.l3g7.griefer_utils.features.uncategorized.griefer_info.gui.GuiBigChest;
import dev.l3g7.griefer_utils.features.uncategorized.griefer_info.gui.GuiBigChest.TextureItem;
import dev.l3g7.griefer_utils.core.util.ItemUtil;
import dev.l3g7.griefer_utils.core.util.MinecraftUtil;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

import static dev.l3g7.griefer_utils.core.util.MinecraftUtil.mc;

public class BotShop {

	public static final List<BotShop> BOT_SHOPS = new ArrayList<>();

	public static BotShop fromJson(JsonObject object) {
		String name = object.get("name").getAsString();
		Citybuild cb = Citybuild.getCitybuild(object.get("cb").getAsString());
		boolean buying = object.get("ankauf").getAsInt() == 1;
		boolean selling = object.get("verkauf").getAsInt() == 1;

		return new BotShop(name, cb, buying, selling);
	}

	public final String name;
	public final Citybuild cb;
	public final boolean buying;
	public final boolean selling;

	private BotShop(String name, Citybuild cb, boolean buying, boolean selling) {
		this.name = name;
		this.cb = cb;
		this.buying = buying;
		this.selling = selling;
	}

	public boolean matchesFilter(String cb, boolean showBuying, boolean showSelling) {
		if (cb != null && !this.cb.matches(cb))
			return false;

		return (showBuying && buying) || (showSelling && selling);
	}

	public void addItemStack(GuiBigChest chest, int id, boolean isCbFiltered) {
		String name = "§6§n" + this.name;
		if (!isCbFiltered)
			name = String.format("§e[%s] %s", MinecraftUtil.getCitybuildAbbreviation(cb.getName()), name);

		String texture = "wallets/";
		if (buying) texture += "in";
		if (selling) texture += "out";
		texture += "going";

		chest.addTextureItem(id, new TextureItem(texture, 14, ItemUtil.createItem(Blocks.command_block, 0, name)), () -> {
			BetterSwitchCommand.sendOnCitybuild("/p h " + this.name, cb);
			mc().displayGuiScreen(null);
		});
	}

	@Override
	public String toString() {
		return name + BigChestUtil.toAbbreviation(cb);
	}

}
