/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.labymod.laby4.settings.types;

import com.google.gson.JsonPrimitive;
import dev.l3g7.griefer_utils.core.api.misc.Named;
import dev.l3g7.griefer_utils.core.settings.types.DropDownSetting;
import dev.l3g7.griefer_utils.labymod.laby4.settings.AbstractSettingImpl;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;

public class DropDownSettingImpl<E extends Enum<E> & Named> extends AbstractSettingImpl<DropDownSetting<E>, E> implements DropDownSetting<E> {

	private final Class<E> enumClass;

	public DropDownSettingImpl(Class<E> enumClass) {
		super(e -> new JsonPrimitive(e.name()), s -> Enum.valueOf(enumClass, s.getAsString()), enumClass.getEnumConstants()[0]);
		this.enumClass = enumClass;
	}

	@Override
	public Icon getIcon() {
		return new IconProxy();
	}

	@Override
	protected Widget[] createWidgets() {
		DropdownWidget<E> widget = new DropdownWidget<>();
		widget.setSelected(get());
		widget.setChangeListener(this::set);

		widget.setEntryRenderer(new EntryRenderer<>() {

			@Override
			public float getWidth(E entry, float maxWidth) {
				return this.toRenderableComponent(entry, maxWidth).getWidth();
			}

			@Override
			public float getHeight(E entry, float maxWidth) {
				return this.toRenderableComponent(entry, maxWidth).getHeight();
			}

			@Override
			public @NotNull Widget createEntryWidget(E entry) {
				return ComponentWidget.component(this.toComponent(entry));
			}

			private Component toComponent(E entry) {
				return Component.text(entry.getName());
			}

			private RenderableComponent toRenderableComponent(E entry, float maxWidth) {
				return RenderableComponent.builder()
					.maxWidth(maxWidth)
					.disableCache()
					.format(this.toComponent(entry));
			}

		});

		widget.addAll(enumClass.getEnumConstants());

		callback(v -> widget.setSelected(v, false));

		return new Widget[]{widget};
	}

	private class IconProxy extends Icon { // NOTE: generalize

		protected IconProxy() {
			super(null);
		}

		@Override
		public void render(ResourceRenderContext context, float x, float y, float width, float height, boolean hover, int color) {
			getStorage().icon.render(null, x, y, width, height, hover, color, null);
		}

		@Override
		public void render(Stack stack, float x, float y, float width, float height, boolean hover, int color, Rectangle stencil) {
			getStorage().icon.render(stack, x, y, width, height, hover, color, stencil);
		}

	}

}
