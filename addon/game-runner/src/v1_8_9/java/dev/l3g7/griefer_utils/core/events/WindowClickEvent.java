/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.core.events;

import dev.l3g7.griefer_utils.core.api.event_bus.Event;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static dev.l3g7.griefer_utils.core.util.MinecraftUtil.player;

public class WindowClickEvent extends Event {

	public final int windowId;
	public final int slotId;
	public final int mouseButtonClicked;
	public final int mode;
	public ItemStack itemStack;

	public WindowClickEvent(int windowId, int slotId, int mouseButtonClicked, int mode) {
		this.windowId = windowId;
		this.slotId = slotId;
		this.mouseButtonClicked = mouseButtonClicked;
		this.mode = mode;

		if (slotId == -999)
			return;

		List<Slot> slots = player().openContainer.inventorySlots;
		if (slotId < 0 || slotId >= slots.size())
			return;

		itemStack = slots.get(slotId).getStack();
	}

	@Mixin(PlayerControllerMP.class)
	private static class MixinPlayerControllerMP {

		@Inject(method = "windowClick", at = @At("HEAD"), cancellable = true)
		public void injectWindowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> cir) {
			if (new WindowClickEvent(windowId, slotId, mouseButtonClicked, mode).fire().isCanceled())
				cir.cancel();
		}

	}

}
