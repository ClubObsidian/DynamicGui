package com.clubobsidian.dynamicgui.server.sponge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.player.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.plugin.DynamicGUIPlugin;
import com.clubobsidian.dynamicgui.scheduler.sponge.SpongeScheduler;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.dynamicgui.server.ServerType;

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
		// TODO - Global
		return Sponge.getServer().getOnlinePlayers().size();
	}

	@Override
	public ServerType getType() 
	{
		return ServerType.SPONGE;
	}

	@Override
	public void registerOutgoingPluginChannel(DynamicGUIPlugin<?, ?> plugin, String channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerIncomingPluginChannel(DynamicGUIPlugin<?, ?> plugin, String channel) {
		// TODO Auto-generated method stub
		
	}
}