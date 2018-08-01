package me.virustotal.dynamicgui.server.impl;

import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.impl.SpongePlayerWrapper;
import me.virustotal.dynamicgui.server.FakeServer;

public class FakeSpongeServer extends FakeServer {

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

}
