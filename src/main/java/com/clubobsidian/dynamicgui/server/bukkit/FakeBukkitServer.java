/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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
import com.clubobsidian.dynamicgui.messaging.MessagingRunnable;
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
	public void registerOutgoingPluginChannel(final DynamicGuiPlugin plugin, final String outGoingChannel) 
	{
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) plugin, outGoingChannel);
	}

	@Override
	public void registerIncomingPluginChannel(final DynamicGuiPlugin plugin, final String incomingChannel, final MessagingRunnable runnable) 
	{
		PluginMessageListener listener = new PluginMessageListener()
		{
			@Override
			public void onPluginMessageReceived(String channel, Player player, byte[] message) 
			{
				if(channel.equals(incomingChannel))
				{
					PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<>(player);
					runnable.run(playerWrapper, message);
				}
			}
		};
		Bukkit.getServer().getMessenger().registerIncomingPluginChannel((Plugin) plugin, incomingChannel, listener);
	}
}