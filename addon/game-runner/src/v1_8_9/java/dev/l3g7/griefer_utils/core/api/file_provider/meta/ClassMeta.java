/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.core.api.file_provider.meta;

import dev.l3g7.griefer_utils.core.api.file_provider.FileProvider;
import dev.l3g7.griefer_utils.core.api.reflection.Reflection;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static dev.l3g7.griefer_utils.core.api.reflection.Reflection.c;
import static dev.l3g7.griefer_utils.core.api.util.ArrayUtil.map;

/**
 * Meta information of a class.
 */
public class ClassMeta implements IMeta {

	public final String name;
	public final String superName;
	public final List<String> interfaces;
	public final int modifiers;
	public final String signature;
	public final List<MethodMeta> methods;
	public final List<AnnotationMeta> annotations;

	public final ClassNode asmNode;
	private Class<?> loadedClass = null;

	/**
	 * Load the information from ASM's {@link ClassNode}.
	 */
	public ClassMeta(ClassNode node) {
		this.name = node.name;
		this.superName = node.superName;
		this.interfaces = node.interfaces;
		this.modifiers = node.access;
		this.signature = node.signature;
		this.methods = map(node.methods, m -> new MethodMeta(this, m));
		this.annotations = node.visibleAnnotations == null ? new ArrayList<>() : map(node.visibleAnnotations, AnnotationMeta::new);
		if (node.invisibleAnnotations != null)
			this.annotations.addAll(map(node.invisibleAnnotations, AnnotationMeta::new));

		this.asmNode = node;
	}

	/**
	 * Load the information from Reflection's {@link Class}.
	 */
	public ClassMeta(Class<?> clazz) {
		Class<?> superClass = clazz.getSuperclass();

		this.name = Type.getInternalName(clazz);
		this.superName = superClass == null ? null : Type.getInternalName(superClass);
		this.interfaces = superClass == null ? Collections.emptyList() : Arrays.stream(superClass.getInterfaces()).map(Type::getInternalName).collect(Collectors.toList());
		this.modifiers = clazz.getModifiers();
		this.signature = null;
		this.methods = map(clazz.getDeclaredMethods(), m -> new MethodMeta(this, m));
		this.annotations = map(clazz.getAnnotations(), AnnotationMeta::new);

		this.asmNode = null;
		this.loadedClass = clazz;
	}

	/**
	 * @return true if the class has the specified super class.
	 */
	public boolean hasSuperClass(String superName) {
		ClassMeta meta = this;
		while (true) {
			if (superName.equals(meta.superName))
				return true;

			if (meta.superName == null)
				return false;

			meta = FileProvider.getClassMeta(meta.superName + ".class", false);
			if (meta == null)
				return false;
		}
	}

	/**
	 * @return true if the class has the specified interface.
	 */
	public boolean hasInterface(String checkedInterface) {
		ClassMeta meta = this;
		while (true) {
			if (meta.interfaces.contains(checkedInterface))
				return true;

			meta = FileProvider.getClassMeta(meta.superName + ".class", false);
			if (meta == null)
				return false;
		}
	}

	/**
	 * Loads the class.
	 */
	public <T> Class<T> load() {
		if (loadedClass == null)
			loadedClass = Reflection.load(name);

		return c(loadedClass);
	}

	@Override
	public List<AnnotationMeta> annotations() {
		return annotations;
	}

	@Override
	public int modifiers() {
		return modifiers;
	}

	@Override
	public String toString() {
		return name;
	}

}
