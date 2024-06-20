/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.post_processor.processors;

import net.minecraft.launchwrapper.IClassTransformer;

/**
 * A processor applied at runtime.
 */
public abstract class RuntimePostProcessor implements IClassTransformer {

	public abstract byte[] transform(String fileName, byte[] basicClass);

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		return transform(transformedName.replace('.', '/').concat(".class"), basicClass);
	}

}