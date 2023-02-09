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

package dev.l3g7.griefer_utils.util;

import dev.l3g7.griefer_utils.util.misc.ChatQueue;
import dev.l3g7.griefer_utils.util.misc.Vec3d;
import dev.l3g7.griefer_utils.util.reflection.Reflection;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.DrawUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A utility class for simplified access to parts of Minecraft (and LabyMod).
 */
public class MinecraftUtil {

	public static Minecraft       mc()              { return Minecraft.getMinecraft(); }
	public static EntityPlayerSP  player()          { return mc().thePlayer; }
	public static String          name()            { return mc().getSession().getUsername(); }
	public static GameSettings    settings()        { return mc().gameSettings; }
	public static TextureManager  textureManager()  { return mc().getTextureManager(); }
	public static ArrayList<SettingsElement> path() { return Reflection.get(mc().currentScreen, "path"); }
	public static File            assetsDir()       { return Reflection.get(mc(), "fileAssets"); }
	public static WorldClient     world()           { return mc().theWorld; }

	public static ItemStack[]     armorInventory()  { return inventory().armorInventory; }
	public static InventoryPlayer inventory()       { return player().inventory; }

	public static int             screenWidth()     { return new ScaledResolution(mc()).getScaledWidth(); }
	public static int             screenHeight()    { return new ScaledResolution(mc()).getScaledHeight(); }
	public static float           partialTicks()    { return labyMod().getPartialTicks(); }

	public static LabyMod         labyMod()         { return LabyMod.getInstance(); }
	public static DrawUtils       drawUtils()       { return labyMod().getDrawUtils(); }

	public static UUID uuid() {
		return mc().getSession().getProfile().getId();
	}

	public static AxisAlignedBB axisAlignedBB(Vec3d a, Vec3d b) {
		return new AxisAlignedBB(a.x, a.y, a.z, b.x, b.y, b.z);
	}

	public static Block blockAt(Vec3d pos) {
		return world().getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock();
	}

	public static Vec3d pos(Entity e) {
		return new Vec3d(e.posX, e.posY, e.posZ);
	}

	public static Vec3d renderPos() {
		RenderManager renderManager = mc().getRenderManager();
		double x = Reflection.get(renderManager, "renderPosX");
		double y = Reflection.get(renderManager, "renderPosY");
		double z = Reflection.get(renderManager, "renderPosZ");
		return new Vec3d(x, y, z);
	}

	public static void display(String message) {
		labyMod().displayMessageInChat(message);
	}
	public static void display(String format, Object... args) {
		display(String.format(format, args));
	}

	public static void displayAchievement(String title, String description) {
		displayAchievement("https://grieferutils.l3g7.dev/icon/64x64/", title, description);
	}

	public static void displayAchievement (String iconUrl, String title, String description) {
		labyMod().getGuiCustomAchievement().displayAchievement(iconUrl, title, description);
	}

	public static void send(String message) {
		ChatQueue.send(message);
	}

	public static void send(String format, Object... args) {
		send(String.format(format, args));
	}

	public static void suggest(String message) {
		mc().displayGuiScreen(new GuiChat(message));
	}

	public static void suggest(String format, Object... args) {
		suggest(String.format(format, args));
	}

}