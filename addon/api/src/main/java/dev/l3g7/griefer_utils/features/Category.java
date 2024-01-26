/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.features;

import dev.l3g7.griefer_utils.settings.types.SwitchSetting;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A feature category.
 */
public class Category {

	private static final Map<Package, Category> categoryCache = new HashMap<>();

	private final SwitchSetting setting;
	private final String configKey;

	/**
	 * Creates a new category from the given package, using data from its meta annotation.
	 */
	private Category(Package pkg) {
		if (pkg.isAnnotationPresent(Uncategorized.class)) {
			setting = SwitchSetting.create().set(true);
			configKey = null;
			return;
		}

		if (!pkg.isAnnotationPresent(Meta.class))
			throw new IllegalStateException("Could not find category of " + pkg);

		Meta meta = pkg.getDeclaredAnnotation(Meta.class);
		configKey = meta.configKey();

		setting = SwitchSetting.create()
			.name(meta.name())
			.icon(meta.icon())
			.config(configKey + ".active")
			.defaultValue(true)
			.subSettings();
	}

	public static Category getCategory(Package pkg) {
		return categoryCache.computeIfAbsent(pkg, Category::new);
	}

	public static Collection<Category> getCategories() {
		return categoryCache.values();
	}

	public SwitchSetting getSetting() {
		return setting;
	}

	public String getConfigKey() {
		return configKey;
	}

	public boolean isEnabled() {
		return setting.get();
	}

	public void add(Feature feature) {
		setting.subSettings(ImmutableList.of(feature.getMainElement()));
	}

	/**
	 * An annotation for defining the metadata of a category.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PACKAGE)
	public @interface Meta {

		String name();
		String icon();
		String configKey();

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PACKAGE)
	public @interface Uncategorized {}
}
