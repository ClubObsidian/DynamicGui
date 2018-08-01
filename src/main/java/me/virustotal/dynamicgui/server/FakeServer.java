package me.virustotal.dynamicgui.server;

import java.util.UUID;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public abstract class FakeServer {

	public abstract void broadcastMessage(String message);
	public abstract PlayerWrapper<?> getPlayer(UUID uuid);
	public abstract PlayerWrapper<?> getPlayer(String name);
}
