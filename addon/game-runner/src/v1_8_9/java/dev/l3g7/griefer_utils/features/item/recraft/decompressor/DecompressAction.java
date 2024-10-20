/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.features.item.recraft.decompressor;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.l3g7.griefer_utils.features.item.recraft.RecraftAction;

public class DecompressAction extends RecraftAction {

	final Ingredient ingredient;

	public DecompressAction(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public JsonElement toJson() {
		return new JsonPrimitive(ingredient.toLong());
	}

	public static DecompressAction fromJson(JsonElement element) {
		long value = element.getAsLong();
		return new DecompressAction(Ingredient.fromLong(value));
	}

}
