/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.labymod.laby3.settings.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.l3g7.griefer_utils.core.api.reflection.Reflection;
import dev.l3g7.griefer_utils.labymod.laby3.settings.Laby3Setting;
import dev.l3g7.griefer_utils.core.settings.types.NumberSetting;
import net.labymod.gui.elements.ModTextField;
import net.labymod.settings.elements.NumberElement;

public class NumberSettingImpl extends NumberElement implements Laby3Setting<NumberSetting, Integer>, NumberSetting {

	private final ExtendedStorage<Integer> storage = new ExtendedStorage<>(JsonPrimitive::new, JsonElement::getAsInt, 0);

	public NumberSettingImpl() {
		super("§cNo name set", null, 0);
		addCallback(this::set);
	}

	@Override
	public ExtendedStorage<Integer> getStorage() {
		return storage;
	}

	@Override
	public NumberSetting placeholder(String placeholder) {
		ModTextField textField = Reflection.get(this, "textField");
		textField.setPlaceHolder(placeholder);
		return this;
	}

	@Override
	public NumberSetting min(int min) {
		setMinValue(min);
		set(getCurrentValue());
		return this;
	}

	@Override
	public NumberSetting max(int max) {
		setMaxValue(max);
		set(getCurrentValue());
		return this;
	}

}
