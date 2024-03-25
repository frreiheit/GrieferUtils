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

package dev.l3g7.griefer_utils.features.world;

import dev.l3g7.griefer_utils.core.file_provider.FileProvider;
import dev.l3g7.griefer_utils.core.file_provider.Singleton;
import dev.l3g7.griefer_utils.core.reflection.Reflection;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.settings.ElementBuilder.MainElement;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import net.labymod.utils.Material;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.storage.MapData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Singleton
public class BuggedMapsFix extends Feature {

	@MainElement
	private final BooleanSetting enabled = new BooleanSetting()
		.name("Verbuggte Karten fixen")
		.description("Behebt, dass Karten das falsche Bild anzeigen.")
		.icon(Material.EMPTY_MAP);

	@Mixin(MapItemRenderer.class)
	private static class MixinMapItemRenderer {

		@Shadow
		@Final
		private Map<String, Object> loadedMaps;

		@Shadow
		@Final
		private TextureManager textureManager;

		@Inject(method = "updateMapTexture", at = @At("HEAD"))
	    private void injectUpdateMapTexture(MapData mapdataIn, CallbackInfo ci) {
			if (!FileProvider.getSingleton(BuggedMapsFix.class).isEnabled())
				return;

			Object loadedMap = loadedMaps.get(mapdataIn.mapName);
	    	if (loadedMap == null)
				return;

			textureManager.deleteTexture(Reflection.get(loadedMap, "location"));
			loadedMaps.remove(mapdataIn.mapName);
	    }

	}

}