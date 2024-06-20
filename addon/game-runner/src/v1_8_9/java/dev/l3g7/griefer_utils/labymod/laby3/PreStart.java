/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.labymod.laby3;

import dev.l3g7.griefer_utils.core.auto_update.AutoUpdater;
import dev.l3g7.griefer_utils.core.auto_update.UpdateImpl;
import dev.l3g7.griefer_utils.post_processor.processors.runtime.transpiler.Java17to8Transpiler;
import net.labymod.addon.AddonLoader;
import net.labymod.api.Constants;
import net.labymod.api.addon.exception.AddonLoadException;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static dev.l3g7.griefer_utils.core.auto_update.AutoUpdater.DELETION_MARKER;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@SuppressWarnings({"unused", "unchecked"})
public class PreStart implements IClassTransformer, UpdateImpl {

	public PreStart() throws IOException, ReflectiveOperationException {
		if (System.setProperty("griefer_utils_load_flag", "") != null)
			throw new Error("GrieferUtils wurde bereits geladen!");

		// Add Java17to8Transpiler before every other transformer
		Field field = LaunchClassLoader.class.getDeclaredField("transformers");
		field.setAccessible(true);
		List<IClassTransformer> transformers = (List<IClassTransformer>) field.get(Launch.classLoader);
		transformers.add(0, Java17to8Transpiler.INSTANCE);

		AutoUpdater.update(this);
		EarlyStart.start();
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		return basicClass;
	}

	@Override
	public void deleteJar(File jar) throws IOException {
		// Try to delete file directly
		if (jar.delete())
			return;

		try {
			// Probably locked; Overwrite it with an empty zip file until Minecraft is closed
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ZipOutputStream out = new ZipOutputStream(bout);
			out.setComment(DELETION_MARKER);
			out.close();
			Files.write(jar.toPath(), bout.toByteArray());
		} catch (Throwable t) {
			// Deleting failed, but it doesn't matter, so nothing is done
			t.printStackTrace();
		}

		// Add old file to LabyMod's .delete
		Path deleteFilePath = AddonLoader.getDeleteQueueFile().toPath();
		String deleteLine = jar.getName() + System.lineSeparator();
		Files.write(deleteFilePath, deleteLine.getBytes(), CREATE, APPEND);
	}

	@Override
	public void handleError(Throwable e) {
		if (e instanceof IOException) {
			// Allow start if updating failed due to network errors
			e.printStackTrace(System.err);
		} else
			throw new RuntimeException("Could not update GrieferUtils!", e);
	}

}