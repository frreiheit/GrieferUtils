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

package dev.l3g7.griefer_utils.core.injection.mixin;

import net.labymod.main.LabyMod;
import net.labymod.main.listeners.RenderTickListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderTickListener.class)
public class MixinRenderTickListener {

	/**
	 * Fix custom achievements not being rendered when in a gui in game.
	 */
	@Redirect(method = "drawMenuOverlay", at = @At(value = "INVOKE", target = "Lnet/labymod/main/LabyMod;isInGame()Z"), remap = false)
	public boolean redirectIsInGame(LabyMod instance) {
		return false;
	}

}