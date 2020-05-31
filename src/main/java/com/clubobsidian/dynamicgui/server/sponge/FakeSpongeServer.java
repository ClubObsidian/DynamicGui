/*
   Copyright 2020 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.server.sponge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.scheduler.sponge.SpongeScheduler;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.dynamicgui.server.ServerType;
import com.clubobsidian.dynamicgui.world.WorldWrapper;
import com.clubobsidian.dynamicgui.world.sponge.SpongeWorldWrapper;

public class FakeSpongeServer extends FakeServer {

	public FakeSpongeServer() 
	{
		super(new SpongeScheduler());
	}

	@Override
	public void broadcastMessage(String message) 
	{
		Sponge.getServer().getBroadcastChannel().send(Text.of(message));
	}
	
	@Override
	public void dispatchServerCommand(String command)
	{
		Sponge.getCommandManager().process(Sponge.getServer().getConsole(), command);
	}

	@Override
	public PlayerWrapper<?> getPlayer(UUID uuid) 
	{
		return new SpongePlayerWrapper<Player>(Sponge.getServer().getPlayer(uuid).get());
	}

	@Override
	public PlayerWrapper<?> getPlayer(String name) 
	{
		return new SpongePlayerWrapper<Player>(Sponge.getServer().getPlayer(name).get());
	}

	@Override
	public Collection<PlayerWrapper<?>> getOnlinePlayers() 
	{
		List<PlayerWrapper<?>> onlinePlayers = new ArrayList<>();
		Sponge.getServer().getOnlinePlayers().forEach(player -> onlinePlayers.add(new SpongePlayerWrapper<Player>(player)));
		return onlinePlayers;
	}

	@Override
	public int getGlobalPlayerCount() 
	{
		if(DynamicGui.get().getRedisBungee() || DynamicGui.get().getBungeeCord())
		{
			return DynamicGui.get().getGlobalServerPlayerCount();
		}
		return Sponge.getServer().getOnlinePlayers().size();
	}

	@Override
	public ServerType getType() 
	{
		return ServerType.SPONGE;
	}

	@Override
	public void registerOutgoingPluginChannel(DynamicGuiPlugin plugin, String channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerIncomingPluginChannel(DynamicGuiPlugin plugin, String channel, MessagingRunnable runnable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WorldWrapper<?> getWorld(String worldName) 
	{
		Optional<World> world = Sponge.getServer().getWorld(worldName);
		if(world.isPresent())
		{
			return new SpongeWorldWrapper(worldName);
		}
		else
		{
			return null;
		}
	}
}