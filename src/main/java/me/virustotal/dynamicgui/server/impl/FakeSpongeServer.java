package me.virustotal.dynamicgui.server.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.impl.SpongePlayerWrapper;
import me.virustotal.dynamicgui.scheduler.impl.SpongeScheduler;
import me.virustotal.dynamicgui.server.FakeServer;

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
	public int getGlobalPlayerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
