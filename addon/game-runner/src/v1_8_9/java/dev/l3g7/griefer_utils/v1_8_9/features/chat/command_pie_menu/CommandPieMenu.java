/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.v1_8_9.features.chat.command_pie_menu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.l3g7.griefer_utils.api.event.event_bus.EventListener;
import dev.l3g7.griefer_utils.api.file_provider.Singleton;
import dev.l3g7.griefer_utils.api.misc.Citybuild;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.laby4.settings.BaseSettingImpl;
import dev.l3g7.griefer_utils.laby4.settings.SettingsImpl;
import dev.l3g7.griefer_utils.laby4.settings.types.CitybuildSettingImpl;
import dev.l3g7.griefer_utils.laby4.settings.types.StringSettingImpl;
import dev.l3g7.griefer_utils.settings.BaseSetting;
import dev.l3g7.griefer_utils.settings.types.CitybuildSetting;
import dev.l3g7.griefer_utils.settings.types.KeySetting;
import dev.l3g7.griefer_utils.settings.types.StringSetting;
import dev.l3g7.griefer_utils.settings.types.SwitchSetting;
import dev.l3g7.griefer_utils.v1_8_9.events.GuiScreenEvent.GuiOpenEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.accessor.impl.ConfigPropertySettingAccessor;
import net.labymod.api.configuration.settings.type.list.ListSetting;
import net.labymod.api.configuration.settings.type.list.ListSettingConfig;
import net.labymod.api.configuration.settings.type.list.ListSettingEntry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static dev.l3g7.griefer_utils.api.reflection.Reflection.c;
import static dev.l3g7.griefer_utils.v1_8_9.util.MinecraftUtil.mc;

@Singleton
public class CommandPieMenu extends Feature {

	private final PieMenu pieMenu = new PieMenu();

	private final SwitchSetting animation = SwitchSetting.create()
		.name("Animation")
		.description("Ob die Öffnen-Animation abgespielt werden soll.")
		.icon("command_pie_menu")
		.defaultValue(true);

	private final KeySetting key = KeySetting.create()
		.name("Taste")
		.icon("key")
		.description("Die Taste, mit der das Befehlsradialmenü geöffnet werden soll.")
		.pressCallback(p -> {
			if (mc().currentScreen != null || !isEnabled())
				return;

			if (p) {
				pieMenu.open(animation.get(), getMainElement());
				return;
			}

			pieMenu.close();
		});

	private final PageListSetting pages = new PageListSetting()
		.name("Seiten")
		.icon(Items.map); // FIXME check whether pages are saved

	@MainElement
	private final SwitchSetting enabled = SwitchSetting.create()
		.name("Befehlsradialmenü")
		.description("Ein Radialmenü zum schnellen Ausführen von Citybuild-bezogenen Befehlen.")
		.icon("command_pie_menu")
		.subSettings(key, animation, pages);

	@EventListener
	private void onGuiOpen(GuiOpenEvent<?> event) {
		pieMenu.close();
	}

	public static class EntryConfig extends net.labymod.api.configuration.loader.Config implements ListSettingConfig {

		private final StringSettingImpl name = (StringSettingImpl) StringSetting.create()
			.name("Name")
			.description("Wie der Eintrag heißen soll.")
			.icon(Items.writable_book);

		private final StringSettingImpl command = (StringSettingImpl) StringSetting.create()
			.name("Befehl")
			.description("Welcher Befehl ausgeführt werden soll, wenn dieser Eintrag ausgewählt wird.")
			.icon(Blocks.command_block);

		private final CitybuildSettingImpl citybuild = (CitybuildSettingImpl) CitybuildSetting.create()
			.name("Citybuild")
			.description("Auf welchem Citybuild dieser Eintrag angezeigt werden soll");

		public void create(BaseSetting<?> parent) {
			name.create(parent);
			command.create(parent);
			citybuild.create(parent);
		}

		@Override
		public boolean isInvalid() {
			return name.get().isBlank() || command.get().isBlank();
		}

		@Override
		public @NotNull Component entryDisplayName() {
			return Component.text(name.get());
		}

		@Override
		public @NotNull Component newEntryTitle() {
			return Component.text("Neuer Eintrag");
		}

		@Override
		public @NotNull List<Setting> toSettings(@Nullable Setting parent, SpriteTexture texture) {
			return Arrays.asList(name, command, citybuild);
		}
	}

	public static class PageConfig extends net.labymod.api.configuration.loader.Config implements ListSettingConfig {

		private final StringSettingImpl name = (StringSettingImpl) StringSetting.create()
			.name("Name")
			.icon(Items.writable_book);

		private final EntryListSetting entries = new EntryListSetting()
			.name("Einträge")
			.icon("command_pie_menu");

		public PageConfig(String name) {
			this.name.set(name);
		}

		public void create(BaseSetting<?> parent) {
			name.create(parent);
			entries.create(parent);
		}

		@Override
		public boolean isInvalid() {
			return name.get().isBlank();
		}

		@Override
		public @NotNull Component entryDisplayName() {
			return Component.text(name.get());
		}

		@Override
		public @NotNull List<Setting> toSettings(@Nullable Setting parent, SpriteTexture texture) {
			return Arrays.asList(name, entries);
		}
	}

	// NOTE: cleanup? merge?
	public static class PageListSetting extends ListSetting implements BaseSettingImpl<PageListSetting, List<PageConfig>> {

		private final ExtendedStorage<List<PageConfig>> storage;

		public PageListSetting() {
			this(new ExtendedStorage<>(v -> {
				JsonArray pages = new JsonArray();
				for (PageConfig entry : v) {
					JsonObject obj = new JsonObject();
					obj.addProperty("name", entry.name.get());
					obj.add("entries", entry.entries.storage.encodeFunc.apply(entry.entries.get()));
					pages.add(obj);
				}
				return pages;
			}, entries -> {
				List<PageConfig> v = new ArrayList<>();
				for (JsonElement elem : entries.getAsJsonArray()) {
					JsonObject page = elem.getAsJsonObject();
					PageConfig cfg = new PageConfig(page.get("name").getAsString());
					cfg.entries.set(cfg.entries.storage.decodeFunc.apply(page.get("entries")));
					v.add(cfg);
				}
				return v;
			}, new ArrayList<>()));
		}

		public PageListSetting(ExtendedStorage<List<PageConfig>> storage) {
			super(UUID.randomUUID().toString(), null, null, new String[0], null, false, null, (byte) -127,
				new ConfigPropertySettingAccessor(null, null, null, null) {
					@Override
					public <T> T get() {
						return c(storage.value == null ? storage.fallbackValue : storage.value);
					}

					@Override
					public <T> void set(T value) {
						List<PageConfig> list = get();
						ArrayList<PageConfig> val = c(value);
						list.clear();
						list.addAll(val);
					}

					@Override
					public Type getGenericType() {
						return new ParameterizedType() {
							public Type[] getActualTypeArguments() {return new Type[]{PageConfig.class};}

							public Type getRawType() {return null;}

							public Type getOwnerType() {return null;}
						};
					}
				}
			);

			this.storage = storage;
			for (PageConfig page : get())
				page.create(this);
		}

		@Override
		public ListSettingEntry createNew() {
			PageConfig config = new PageConfig("Seite " + get().size());
			get().add(config);

			ListSettingEntry entry = new ListSettingEntry(this, config.entryDisplayName(), get().size()) {
				public Icon getIcon() {
					return SettingsImpl.buildIcon("command_pie_menu");
				}
			};

			entry.addSettings(config);
			return entry;
		}

		@Override
		public Component displayName() {
			return Component.text(name());
		}

		@Override
		public Icon getIcon() {
			return storage.icon;
		}

		@Override
		public ExtendedStorage<List<PageConfig>> getStorage() {
			return storage;
		}
	}

	public static class EntryListSetting extends ListSetting implements BaseSettingImpl<EntryListSetting, List<EntryConfig>> {

		private final ExtendedStorage<List<EntryConfig>> storage;

		public EntryListSetting() {
			this(new ExtendedStorage<>(v -> {
				JsonArray entries = new JsonArray();
				for (EntryConfig entry : v) {
					JsonObject obj = new JsonObject();
					obj.addProperty("name", entry.name.get());
					obj.addProperty("command", entry.command.get());
					obj.addProperty("cb", entry.citybuild.get().getDisplayName()); // NOTE GSON?
					entries.add(obj);
				}
				return entries;
			}, entries -> {
				List<EntryConfig> v = new ArrayList<>();
				for (JsonElement elem : entries.getAsJsonArray()) {
					JsonObject obj = elem.getAsJsonObject();
					EntryConfig entry = new EntryConfig();
					entry.name.set(obj.get("name").getAsString());
					entry.command.set(obj.get("command").getAsString());
					entry.citybuild.set(Citybuild.getCitybuild(obj.get("cb").getAsString()));
					v.add(entry);
				}
				return v;
			}, new ArrayList<>()));
		}

		private EntryListSetting(ExtendedStorage<List<EntryConfig>> storage) {
			super(UUID.randomUUID().toString(), null, null, new String[0], null, false, null, (byte) -127,
				new ConfigPropertySettingAccessor(null, null, null, null) {
					@Override
					public <T> T get() {
						return c(storage.value == null ? storage.fallbackValue : storage.value);
					}

					@Override
					public <T> void set(T value) {
						List<EntryConfig> list = get();
						ArrayList<EntryConfig> val = c(value);
						list.clear();
						list.addAll(val);
					}

					@Override
					public Type getGenericType() {
						return new ParameterizedType() {
							public Type[] getActualTypeArguments() {return new Type[]{EntryConfig.class};}

							public Type getRawType() {return null;}

							public Type getOwnerType() {return null;}
						};
					}
				}
			);
			this.storage = storage;
			for (EntryConfig entry : get())
				entry.create(this);
		}

		@Override
		public ListSettingEntry createNew() {
			EntryConfig config = new EntryConfig();
			get().add(config);

			ListSettingEntry entry = new ListSettingEntry(this, config.newEntryTitle(), get().size()) {
				public Icon getIcon() {
					return SettingsImpl.buildIcon(Items.map);
				}
			};

			entry.addSettings(config);
			return entry;
		}

		@Override
		public Component displayName() {
			return Component.text(name());
		}

		@Override
		public Icon getIcon() {
			return storage.icon;
		}

		@Override
		public ExtendedStorage<List<EntryConfig>> getStorage() {
			return storage;
		}
	}

}
