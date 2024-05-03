package dev.l3g7.griefer_utils.laby3.settings;

import com.google.gson.JsonElement;
import dev.l3g7.griefer_utils.api.misc.functions.Function;
import dev.l3g7.griefer_utils.api.reflection.Reflection;
import dev.l3g7.griefer_utils.laby3.settings.Icon.WrappedIcon;
import dev.l3g7.griefer_utils.settings.AbstractSetting;
import dev.l3g7.griefer_utils.settings.BaseSetting;
import dev.l3g7.griefer_utils.settings.types.HeaderSetting;
import net.labymod.settings.elements.SettingsElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static dev.l3g7.griefer_utils.api.reflection.Reflection.c;
import static dev.l3g7.griefer_utils.laby3.bridges.Laby3MinecraftBridge.laby3MinecraftBridge;

@SuppressWarnings("unchecked")
public interface Laby3Setting<S extends AbstractSetting<S, V>, V> extends AbstractSetting<S, V> {

	// Helper methods

	default void init() {
		((SettingsElement) this).setDisplayName(null);
	}

	// BaseSetting

	@Override
	default String name() {
		return getStorage().name;
	}

	@Override
	default S name(String name) {
		((SettingsElement) this).setDisplayName(name.trim());
		getStorage().name = name.trim(); // TODO why store in storage?
		return (S) this;
	}

	@Override
	default S description(String... description) {
		if (description.length == 0)
			getStorage().description = null;
		else
			getStorage().description = String.join("\n", description).trim();

		((SettingsElement) this).setDescriptionText(getStorage().description);
		return (S) this;
	}

	@Override
	default S icon(Object icon) {
		getStorage().icon = laby3MinecraftBridge.createIcon(icon);
		Reflection.set(this, "iconData", new WrappedIcon(getStorage().icon));
		return (S) this;
	}

	@Override
	default S subSettings(BaseSetting<?>... settings) {
		((SettingsElement) this).getSubSettings().getElements().clear();
		return subSettings(Arrays.asList(settings));
	}

	@Override
	default S subSettings(List<BaseSetting<?>> settings) {
		settings = new ArrayList<>(settings);
		settings.removeIf(Objects::isNull);
		settings.forEach(s -> s.setParent(this));
		((SettingsElement) this).getSubSettings().addAll(c(settings));
		return (S) this;
	}

	@Override
	default S addSetting(BaseSetting<?> setting) {
		setting.setParent(this);
		((SettingsElement) this).getSubSettings().add(c(setting));
		return (S) this;
	}

	@Override
	default S addSetting(int index, BaseSetting<?> setting) {
		setting.setParent(this);
		((SettingsElement) this).getSubSettings().getElements().add(index, c(setting));
		return (S) this;
	}

	@Override
	default List<BaseSetting<?>> getChildSettings() {
		return c(((SettingsElement) this).getSubSettings().getElements()
			.stream().filter(BaseSetting.class::isInstance)
			.collect(Collectors.toList()));
	}

	@Override
	default void create(Object parent) {
		for (BaseSetting<?> setting : getChildSettings())
			setting.create(this);
	}

	// AbstractSetting

	@Override
	ExtendedStorage<V> getStorage();

	@Override
	default S enabled(boolean enabled) {
		getStorage().enabled = enabled;
		return (S) this;
	}

	@Override
	default S extend() {
		return (S) this;
	}

	@Override
	default S set(V value) {
		if (Reflection.getField(getClass(), "currentValue") != null)
			Reflection.set(this, "currentValue", value);
		if (Reflection.getMethod(getClass(), "updateValue") != null)
			Reflection.invoke(this, "updateValue");

		return AbstractSetting.super.set(value);
	}

	class ExtendedStorage<V> extends Storage<V> {

		public String name = "§cNo name set";
		public String description = null;
		public Icon icon;
		public boolean enabled = true;

		public ExtendedStorage(Function<V, JsonElement> encodeFunc, Function<JsonElement, V> decodeFunc, V fallbackValue) {
			super(encodeFunc, decodeFunc, fallbackValue);
		}

	}

}
