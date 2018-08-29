package com.clubobsidian.dynamicgui.server.bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.scheduler.bukkit.BukkitScheduler;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.dynamicgui.server.ServerType;

public class FakeBukkitServer extends FakeServer {

	public FakeBukkitServer() 
	{
		super(new BukkitScheduler());
	}

	@Override
	public void broadcastMessage(String message) 
	{
		Bukkit.getServer().broadcastMessage(message);
	}
	
	@Override
	public void dispatchServerCommand(String command)
	{
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
	}

	@Override
	public PlayerWrapper<?> getPlayer(UUID uuid) 
	{
		return new BukkitPlayerWrapper<Player>(Bukkit.getServer().getPlayer(uuid));
	}

	@SuppressWarnings("deprecation")
	@Override
	public PlayerWrapper<?> getPlayer(String name) 
	{
		return new BukkitPlayerWrapper<Player>(Bukkit.getServer().getPlayer(name));
	}

	@Override
	public Collection<PlayerWrapper<?>> getOnlinePlayers() 
	{
		List<PlayerWrapper<?>> onlinePlayers = new ArrayList<>();
		Bukkit.getServer().getOnlinePlayers().forEach(player -> onlinePlayers.add(new BukkitPlayerWrapper<Player>(player)));
		return onlinePlayers;
	}

	@Override
	public int getGlobalPlayerCount() 
	{
		if(DynamicGui.get().getRedisBungee() || DynamicGui.get().getBungeeCord())
		{
			return DynamicGui.get().getGlobalServerPlayerCount();
		}
		return Bukkit.getServer().getOnlinePlayers().size();
	}

	@Override
	public ServerType getType() 
	{
		return ServerType.SPIGOT;
	}

	@Override
	public void registerOutgoingPluginChannel(DynamicGuiPlugin plugin, String channel) 
	{
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) plugin, channel);
	}

	@Override
	public void registerIncomingPluginChannel(DynamicGuiPlugin plugin, String channel) 
	{
		Bukkit.getServer().getMessenger().registerIncomingPluginChannel((Plugin) plugin, channel, (PluginMessageListener) plugin);
		// TODO Auto-generated method stub
		
	}
}