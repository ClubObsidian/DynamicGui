package me.virustotal.dynamicgui.server.impl;

import org.bukkit.Bukkit;

import me.virustotal.dynamicgui.server.FakeServer;

public class FakeBukkitServer extends FakeServer {

	@Override
	public void broadcastMessage(String message) 
	{
		Bukkit.getServer().broadcastMessage(message);
	}

}
