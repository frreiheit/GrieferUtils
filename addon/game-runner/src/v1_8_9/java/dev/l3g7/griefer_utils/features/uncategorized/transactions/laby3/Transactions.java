/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.features.uncategorized.transactions.laby3;

import dev.l3g7.griefer_utils.core.api.bridges.Bridge;
import dev.l3g7.griefer_utils.core.api.bridges.Bridge.ExclusiveTo;
import dev.l3g7.griefer_utils.core.api.event_bus.EventListener;
import dev.l3g7.griefer_utils.core.api.file_provider.FileProvider;
import dev.l3g7.griefer_utils.core.api.file_provider.Singleton;
import dev.l3g7.griefer_utils.core.api.misc.Constants;
import dev.l3g7.griefer_utils.core.api.misc.Named;
import dev.l3g7.griefer_utils.core.api.reflection.Reflection;
import dev.l3g7.griefer_utils.labymod.laby3.settings.types.HeaderSettingImpl;
import dev.l3g7.griefer_utils.core.events.network.MysteryModConnectionEvent.MMPacketReceiveEvent;
import dev.l3g7.griefer_utils.core.events.network.MysteryModConnectionEvent.MMStateChangeEvent;
import dev.l3g7.griefer_utils.core.injection.InheritedInvoke;
import dev.l3g7.griefer_utils.core.misc.TickScheduler;
import dev.l3g7.griefer_utils.core.misc.mysterymod_connection.MysteryModConnection;
import dev.l3g7.griefer_utils.core.misc.mysterymod_connection.MysteryModConnection.State;
import dev.l3g7.griefer_utils.core.misc.mysterymod_connection.packets.transactions.Transaction;
import dev.l3g7.griefer_utils.core.misc.mysterymod_connection.packets.transactions.TransactionsPacket;
import dev.l3g7.griefer_utils.core.settings.BaseSetting;
import dev.l3g7.griefer_utils.core.settings.types.CategorySetting;
import dev.l3g7.griefer_utils.core.settings.types.HeaderSetting;
import dev.l3g7.griefer_utils.core.settings.types.StringSetting;
import dev.l3g7.griefer_utils.core.util.MinecraftUtil;
import dev.l3g7.griefer_utils.features.Feature;
import dev.l3g7.griefer_utils.features.uncategorized.transactions.LocalTransactions;
import dev.l3g7.griefer_utils.features.uncategorized.transactions.TempTransactionsBridge;
import net.labymod.settings.LabyModAddonsGui;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.SimpleDateFormat;
import java.util.*;

import static dev.l3g7.griefer_utils.core.api.bridges.Bridge.Version.LABY_3;
import static dev.l3g7.griefer_utils.core.api.reflection.Reflection.c;
import static dev.l3g7.griefer_utils.core.util.MinecraftUtil.mc;

@Bridge
@Singleton
@ExclusiveTo(LABY_3)
public class Transactions extends Feature implements TempTransactionsBridge {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private static final List<BaseSetting<?>> HEADER = Arrays.asList(
		HeaderSetting.create("§r"),
		HeaderSetting.create("§r§e§l" + Constants.ADDON_NAME).scale(1.3),
		HeaderSetting.create("§f§lTransaktionen").scale(.7).entryHeight(7),
		HeaderSetting.create("§fder letzten 30 Tage").scale(.7).entryHeight(10),
		HeaderSetting.create("§c§nDie Beträge sind abgerundet§c!").scale(.7)
	);

	private Set<Transaction> transactions = Collections.synchronizedSet(new TreeSet<>());
	private boolean firstRequest = false;

	@MainElement
	private final CategorySetting setting = CategorySetting.create()
		.name("Transaktionen")
		.description("§eVerbindet...")
		.icon("scroll")
		.subSettings(HEADER);

	{ ((ControlElement) setting).setSettingEnabled(false); }

	@EventListener
	public void onMMPacket(MMPacketReceiveEvent<TransactionsPacket> event) {
		if (firstRequest && event.packet.transactions.isEmpty()) {
			transactions = LocalTransactions.transactions;
			firstRequest = false;
		} else {
			transactions.addAll(event.packet.transactions);
		}

		updateSettings();
	}

	@EventListener
	public void onMMStateChange(MMStateChangeEvent event) {
		State state = event.state;
		if (state == State.CONNECTED) {
			setting.name("Transaktionen")
				.description("Zeigt die Transaktionen der letzen 30 Tage an.");
			((ControlElement) setting).setSettingEnabled(true);

			firstRequest = true;
			MysteryModConnection.requestTransactions();
		} else {
			setting.name("§c§mTransaktionen")
				.description("§cMysteryMod ist nicht erreichbar:", state.errorMessage, "", "Joine auf einen Server, um die Verbindung erneut zu versuchen!");
		}
	}

	public void updateSettings() {
		List<SettingsElement> list = ((ControlElement) setting).getSubSettings().getElements();

		list.clear();
		list.addAll(c(Arrays.asList(
			HeaderSetting.create("§r"),
			HeaderSetting.create("§r§e§l" + Constants.ADDON_NAME).scale(1.3),
			HeaderSetting.create("§f§lTransaktionen").scale(.7).entryHeight(7),
			HeaderSetting.create("§fder letzten 30 Tage").scale(.7).entryHeight(10)
		)));
		if (transactions != LocalTransactions.transactions)
			list.add(c(HeaderSetting.create("§c§nDie Beträge sind abgerundet§c!").scale(.7)));

		// Add transactions count
		String subHeader = "Insgesamt " + (transactions.size() == 1 ? "eine Transaktion" : transactions.size() + " Transaktionen");
		if (transactions == LocalTransactions.transactions)
			subHeader += " §e(nicht synchronisiert)";
		list.add(new HeaderSettingImpl(subHeader));
		list.add((HeaderSettingImpl) new HeaderSettingImpl("§r").scale(.4).entryHeight(10));

		// Add filter
		StringSetting filter = StringSetting.create()
			.name("Suche")
			.icon("magnifying_glass")
			.callback(this::updateFilter);

		list.add((SettingsElement) filter);
		list.add((SettingsElement) HeaderSetting.create("§r").entryHeight(10));

		// Add transactions
		List<Transaction> transactions = new ArrayList<>(this.transactions);
		for (Transaction t : transactions) {
			if (t.recipientname == null)
				continue;

			Direction direction = Direction.get(t);

			String amountStr = Constants.DECIMAL_FORMAT_98.format(t.amount);
			String title = "§l" + amountStr + "$§";

			List<BaseSetting<?>> subSettings = new ArrayList<>(Arrays.asList(
				HeaderSetting.create("§r"),
				HeaderSetting.create("§r§e§l" + Constants.ADDON_NAME).scale(1.3),
				HeaderSetting.create("§f§lTransaktion #" + t.id).scale(.7)
			));

			// Add sender/receiver by direction
			switch (direction) {
				case SENT:
					title = "§c" + title + "c an §l" + t.recipientname;
					subSettings.add(HeaderSetting.create("§lEmpfänger: §r" + t.recipientname).entryHeight(11));
					break;
				case RECEIVED:
					title = "§a" + title + "a von §l" + t.username;
					subSettings.add(HeaderSetting.create("§lSender: §r" + t.username).entryHeight(11));
					break;
				case SELF:
					title = "§e" + title + "e an dich";
					break;
			}

			// Add amount and timestamp
			subSettings.add(HeaderSetting.create("§lBetrag: §r" + amountStr + "$").entryHeight(11));
			subSettings.add(HeaderSetting.create("§lZeitpunkt: §r" + DATE_FORMAT.format(new Date(t.timestamp))).entryHeight(11));

			list.add((SettingsElement) CategorySetting.create().name(" " + title).subSettings(subSettings));
		}

		// Update
		TickScheduler.runAfterRenderTicks(() -> {
			if (!(mc().currentScreen instanceof LabyModAddonsGui))
				return;

			if (path().size() == 0 || path().get(path().size() - 1) != setting)
				return;

			updateFilter();
		}, 1);
	}

	public void onInit() {
		if (path().get(path().size() - 1) == setting) {
			if (transactions != LocalTransactions.transactions)
				MysteryModConnection.requestTransactions();
			else
				updateSettings();
		}
	}

	public static ArrayList<SettingsElement> path() { return Reflection.get(mc().currentScreen, "path"); }

	private void updateFilter() {
		TickScheduler.runAfterRenderTicks(() -> {
			if (!(mc().currentScreen instanceof LabyModAddonsGui))
				return;

			List<SettingsElement> listedElementsStored = new ArrayList<>(Reflection.get(mc().currentScreen, "listedElementsStored"));
			listedElementsStored.removeIf(setting -> setting instanceof CategorySetting);

			StringSetting filterSetting = listedElementsStored.stream()
				.filter(s -> s instanceof StringSetting)
				.map(s -> (StringSetting) s)
				.findFirst().orElse(null);

			String filter = filterSetting == null ? "" : filterSetting.get();
			boolean dotMode = filter.contains(".");

			((ControlElement) getMainElement()).getSubSettings().getElements().stream()
				.filter(setting -> {
					if (!(setting instanceof CategorySetting))
						return false;

					String text = setting.getDisplayName().toLowerCase()
						.replaceAll("§.", "");

					if (!dotMode)
						text = text.replace(".", "");

					return text.contains(filter.toLowerCase());
				})
				.forEach(listedElementsStored::add);
			Reflection.set(mc().currentScreen, "listedElementsStored", listedElementsStored);
		}, 1);
	}

	public enum Direction {

		SENT, RECEIVED, SELF;

		public static Direction get(Transaction t) {
			if (t.recipientname.equals(t.username))
				return SELF;
			if (MinecraftUtil.name().equals(t.username))
				return SENT;
			else
				return RECEIVED;
		}
	}

	enum ExportFormat implements Named {

		NO_SELECTION("§7-", null),
		TEXT("Text", "txt"),
		JSON("JSON", "json"),
		CSV("CSV", "csv"),
		PPTX("PPTX", "pptx");

		final String name;
		final String fileSuffix;

		ExportFormat(String name, String fileSuffix) {
			this.name = name;
			this.fileSuffix = fileSuffix;
		}

		@Override
		public String getName() {
			return name;
		}

	}

	@Mixin(LabyModAddonsGui.class)
	private static class MixinLabyModAddonsGui {

		@InheritedInvoke(GuiScreen.class)
		@Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
		public void onPathAdd(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
			FileProvider.getSingleton(Transactions.class).onInit();
		}

	}

}