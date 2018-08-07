package me.virustotal.dynamicgui.server.bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.bukkit.BukkitPlayerWrapper;
import me.virustotal.dynamicgui.scheduler.bukkit.BukkitScheduler;
import me.virustotal.dynamicgui.server.FakeServer;
import me.virustotal.dynamicgui.server.ServerType;

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
	public int getGlobalPlayerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServerType getType() 
	{
		return ServerType.SPIGOT;
	}
}