/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 * Copyright (c) L3g7.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */

package dev.l3g7.griefer_utils.core.misc.mysterymod_connection.packets.auth;

import dev.l3g7.griefer_utils.core.misc.mysterymod_connection.packets.Packet;

import java.util.UUID;

public class LoginStartPacket extends Packet {

	public UUID uuid;

	public LoginStartPacket() {}

	public LoginStartPacket(UUID uuid) {
		this.uuid = uuid;
	}

}