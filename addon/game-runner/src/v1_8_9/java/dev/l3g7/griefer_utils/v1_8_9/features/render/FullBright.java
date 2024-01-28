/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.v1_8_9.features.render;

import com.google.gson.JsonPrimitive;
import dev.l3g7.griefer_utils.api.event.event_bus.EventListener;
import dev.l3g7.griefer_utils.api.file_provider.Singleton;
import dev.l3g7.griefer_utils.api.misc.config.Config;
import dev.l3g7.griefer_utils.settings.types.KeySetting;
import dev.l3g7.griefer_utils.settings.types.SwitchSetting;
import dev.l3g7.griefer_utils.v1_8_9.events.TickEvent;
import dev.l3g7.griefer_utils.features.Feature;

import static dev.l3g7.griefer_utils.v1_8_9.util.MinecraftUtil.mc;

/**
 * Turns the gamma to 10.
 */
@Singleton
public class FullBright extends Feature {

	private static final String GAMMA_PATH = "render.full_bright.old_gamma_value";

	private final KeySetting key = KeySetting.create()
		.name("Taste")
		.icon("key")
		.description("Die Taste, mit der FullBright an-/ausgeschalten wird.")
		.pressCallback(pressed -> {
			if (pressed) {
				SwitchSetting enabled = ((SwitchSetting) getMainElement());
				enabled.set(!enabled.get());
			}
		});

	@MainElement
	private final SwitchSetting enabled = SwitchSetting.create()
		.name("FullBright")
		.description("Stellt den Gammawert auf 10.")
		.icon("light_bulb")
		.subSettings(key)
		.callback(active -> {
			if (active) {
				float gamma = mc().gameSettings.gammaSetting;
				if (gamma <= 1)
					Config.set(GAMMA_PATH, new JsonPrimitive(gamma));

				return;
			}

			if (Config.has(GAMMA_PATH))
				mc().gameSettings.gammaSetting = Config.get(GAMMA_PATH).getAsFloat();
		});

	@EventListener
	public void onPlayerTick(TickEvent.ClientTickEvent event) {
		mc().gameSettings.gammaSetting = 10;
	}

}